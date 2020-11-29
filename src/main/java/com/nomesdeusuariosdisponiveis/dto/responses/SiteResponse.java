package com.nomesdeusuariosdisponiveis.dto.responses;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel(value = "Site Response")
public class SiteResponse {

	private String name;
	private String url;
	private boolean available;

	public static SiteResponse toResponse(String service, String url, boolean available) {
		return SiteResponse.builder().name(service).url(url).available(available).build();
	}

}
