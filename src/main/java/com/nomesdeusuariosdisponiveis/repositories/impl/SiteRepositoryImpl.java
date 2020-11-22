package com.nomesdeusuariosdisponiveis.repositories.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.nomesdeusuariosdisponiveis.dto.responses.SiteResponse;
import com.nomesdeusuariosdisponiveis.entities.Site;
import com.nomesdeusuariosdisponiveis.repositories.SiteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SiteRepositoryImpl {

	private static final String FAILED_TO_GET_ALL_BY_SERVICE_NAME = "Failed to get all by service name";

	@Autowired
	private SiteRepository siteRepository;

	public List<SiteResponse> verifyStatus(String userName) {
		try {
			List<SiteResponse> sitesResponses = new ArrayList<>();
			List<Site> sites = siteRepository.findAll();

			for (Site site : sites) {
				String url = site.getUrl().replace("{}", userName);
				HttpResponse<String> response = Unirest.get(url).header("Connection", "keep-alive")
						.header("Upgrade-Insecure-Requests", "1")
						.header("User-Agent", site.getUserAgent() != null ? site.getUserAgent()
								: "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36")
						.header("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
						.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "en-US;q=1").asString();

				boolean available = false;

				if (site.getErrorType() == 0) {
					if (response.getStatus() != 200) {
						available = true;
					}
				} else if (site.getErrorType() == 1) {
					if (response.getBody().contains(site.getErrorMsg())) {
						available = true;
					}
				} else if (site.getErrorType() == 2) {
					if (response.getStatus() != 200 && response.getBody().contains(site.getErrorMsg())) {
						available = true;
					}
				}
				sitesResponses.add(SiteResponse.toResponse(site.getService(), url, available, null));
			}

			return sitesResponses;
		} catch (Exception e) {
			log.error(FAILED_TO_GET_ALL_BY_SERVICE_NAME, e);
			return Arrays.asList(SiteResponse.toResponse(null, null, false, e.getMessage()));
		}
	}

}
