package com.nomesdeusuariosdisponiveis.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SiteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String service;
	private String url;
	private String urlRegister;
	private int errorType;
	private String errorMsg;

}
