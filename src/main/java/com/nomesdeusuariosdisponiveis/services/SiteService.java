package com.nomesdeusuariosdisponiveis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nomesdeusuariosdisponiveis.dto.responses.SiteResponse;
import com.nomesdeusuariosdisponiveis.repositories.impl.SiteRepositoryImpl;

@Lazy
@Service
public class SiteService {

	@Autowired
	private SiteRepositoryImpl siteRepositoryImpl;

	public List<SiteResponse> getSiteStatusByUserName(String userName) {
		return siteRepositoryImpl.verifyStatus(userName);
	}

}
