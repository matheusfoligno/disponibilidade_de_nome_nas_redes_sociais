package com.nomesdeusuariosdisponiveis.repositories.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomesdeusuariosdisponiveis.dto.responses.SiteResponse;
import com.nomesdeusuariosdisponiveis.dtos.SiteDTO;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@Service
public class SiteRepositoryImpl {

	private static final String FAILED_TO_GET_ALL_BY_SERVICE_NAME = "Failed to get all by service name";

	public List<SiteResponse> verifyStatus(String userName) {
		List<SiteResponse> sitesResponses = new ArrayList<>();

		try {
			List<SiteDTO> sites = readJson();

			for (SiteDTO site : sites) {
				String url = site.getUrl().replace("{}", userName);

				HttpResponse<String> response = getResponse(site, url);

				boolean available = checkAvailable(site, response);

				sitesResponses.add(SiteResponse.toResponse(site.getService(),
						site.getUrlRegister().replace("{}", userName), available));
			}

			return sitesResponses;
		} catch (Exception e) {
			log.error(FAILED_TO_GET_ALL_BY_SERVICE_NAME, e);
			return Arrays.asList(SiteResponse.toResponse(null, null, false));
		}
	}

	private boolean checkAvailable(SiteDTO site, HttpResponse<String> response) {
		boolean available = false;

		switch (site.getErrorType()) {
		case 0:
			if (response == null || response.getStatus() != 200) {
				available = true;
			}
			break;
		case 1:
			if (response.getBody().contains(site.getErrorMsg())) {
				available = true;
			}
			break;
		}
		return available;
	}

	private HttpResponse<String> getResponse(SiteDTO site, String url) throws UnirestException {
		try {
			HttpClient httpClient = HttpClients.custom().disableCookieManagement().build();
			Unirest.config().httpClient(httpClient);
			HttpResponse<String> response = Unirest.get(url).header("Connection", "keep-alive")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36")
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "en-US;q=1").asString();
			return response;
		} catch (Exception e) {
			if (site.getService().contains("Domain")) {
				return null;
			}
		}
		return null;
	}

	private List<SiteDTO> readJson() throws JsonParseException, JsonMappingException, IOException {
		InputStream in = SiteRepositoryImpl.class.getResourceAsStream("sitesInfo.json");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(in, new TypeReference<List<SiteDTO>>() {
		});
	}

}
