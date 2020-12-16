package com.nomesdeusuariosdisponiveis.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@ExtendWith(MockitoExtension.class)
public class SiteRepositoryImplTest {

	@InjectMocks
	private SiteRepositoryImpl siteRepositoryImpl;

	@Test
	public void shouldGetReturnStatusNotFound() {
		HttpResponse<String> jsonResponse = Unirest.get("https://www.youtube.com/5a9ce37b3100004f00ab5154")
				.header("Connection", "keep-alive").header("Upgrade-Insecure-Requests", "1")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "en-US;q=1").asString();

		assertNotNull(jsonResponse.getBody());
		assertEquals(404, jsonResponse.getStatus());
	}

	@Test
	public void shouldGetReturnStatusOkay() {
		HttpResponse<String> jsonResponse = Unirest.get("https://www.youtube.com/umgrandeprogramador")
				.header("Connection", "keep-alive").header("Upgrade-Insecure-Requests", "1")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "en-US;q=1").asString();

		assertNotNull(jsonResponse.getBody());
		assertEquals(200, jsonResponse.getStatus());
	}

}
