package com.pruebas.test;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class Testing {

	@Test
	public void llamadoConParametro() throws Exception {

		URL url = new URL("http://localhost:8080/lugar/traceip/92.54.39.23");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();

		assertEquals(200, code);

	}

	@Test
	public void llamadoSinParametro() throws Exception {

		URL url = new URL("http://localhost:8080/lugar/traceip/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();

		assertEquals(404, code);

	}

}
