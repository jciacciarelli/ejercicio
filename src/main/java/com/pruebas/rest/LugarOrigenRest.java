package com.pruebas.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pruebas.dao.EstadisticaDAO;
import com.pruebas.exception.BadRequestException;
import com.pruebas.model.Estadistica;
import com.pruebas.model.LugarOrigen;

@RestController
@RequestMapping("lugar")
public class LugarOrigenRest {

	@Autowired
	private EstadisticaDAO estadisticaDAO;

	@GetMapping("/traceip/{ip}")
	public String getDatos(@PathVariable String ip) {
		
		LugarOrigen lugarOrigen = new LugarOrigen(ip);

		URL ip2country;
		try {
			ip2country = new URL("https://api.ip2country.info/ip?" + ip);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		JSONObject jObject;
		try {
			jObject = new JSONObject(llamadoHttps(ip2country));
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

		String countryName = jObject.getString("countryName");
		String countryCode3 = jObject.getString("countryCode3");

		lugarOrigen.setPais(countryName + " (" + countryCode3 + ")");

		URL restcountries;
		try {
			restcountries = new URL("https://restcountries.eu/rest/v2/alpha/" + countryCode3);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		JSONObject jObject2;
		try {
			jObject2 = new JSONObject(llamadoHttps(restcountries));
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		JSONArray languages = jObject2.getJSONArray("languages");

		for (int i = 0; i < languages.length(); i++) {
			String name = languages.getJSONObject(i).getString("name");
			String iso639_1 = languages.getJSONObject(i).getString("iso639_1");
			lugarOrigen.addIdioma(name + " (" + iso639_1 + ")");
		}

		lugarOrigen.setIsoCode(languages.getJSONObject(0).getString("iso639_1"));

		JSONArray timezones = jObject2.getJSONArray("timezones");

		for (int i = 0; i < timezones.length(); i++) {
			String utc = timezones.getString(i);
			String dateUTC = clientDateString(utc.replace("UTC", "GMT"));
			lugarOrigen.addHora(dateUTC + " (" + utc + ")");
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		lugarOrigen.setFechaActual(dateFormat.format(date));

		String currency = jObject2.getJSONArray("currencies").getJSONObject(0).getString("code");

		URL datafixer;
		try {
			datafixer = new URL("http://data.fixer.io/api/latest?access_key=6b5cb0ab4f3d8758fef9546d1eaf13d5");
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		JSONObject jObject3;
		try {
			jObject3 = new JSONObject(llamadoHttps(datafixer));
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		Double usd = jObject3.getJSONObject("rates").getDouble("USD");
		Double curr = jObject3.getJSONObject("rates").getDouble(currency);

		lugarOrigen.setMoneda(currency + " (1 " + currency + " : " + curr * usd + " U$S)");

		Double x1 = -34.0;
		Double x2 = -64.0;
		Double y1 = jObject2.getJSONArray("latlng").getDouble(0);
		Double y2 = jObject2.getJSONArray("latlng").getDouble(1);
		lugarOrigen.setDistanciaEstimada(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonInputString = gson.toJson(lugarOrigen);

		Estadistica estadistica = new Estadistica();

		estadistica.setPais(lugarOrigen.getPais().split(" ")[0]);
		estadistica.setDistancia(lugarOrigen.getDistanciaEstimada());
		estadistica.setInvocaciones(1);

		guardarEstadistica(estadistica);

		return jsonInputString;
	}

	private String llamadoHttps(URL url) {
		try {

			URLConnection connection;
			if ("http".equals(url.getProtocol())) {
				connection = (HttpURLConnection) url.openConnection();
				((HttpURLConnection) connection).setInstanceFollowRedirects(false);
				((HttpURLConnection) connection).setRequestMethod("GET");
			} else {
				connection = (HttpsURLConnection) url.openConnection();
				((HttpsURLConnection) connection).setInstanceFollowRedirects(false);
				((HttpsURLConnection) connection).setRequestMethod("GET");
			}

			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setRequestProperty("charset", "utf-8");

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	private String clientDateString(String id) {
		TimeZone tz = TimeZone.getTimeZone(id);
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		df.setTimeZone(tz); // strip timezone
		return df.format(new Date());
	}

	private void guardarEstadistica(Estadistica estadistica) {
		if (estadisticaDAO.existsById(estadistica.getPais()))
			estadistica.setInvocaciones(estadistica.getInvocaciones() + 1);
		estadisticaDAO.save(estadistica);
	}

}
