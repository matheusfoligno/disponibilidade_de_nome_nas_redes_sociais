package com.nomesdeusuariosdisponiveis.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nomesdeusuariosdisponiveis.dto.responses.SiteResponse;
import com.nomesdeusuariosdisponiveis.services.SiteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/site")
@Api(value = "Site", tags = { "Site" })
public class SiteResource {

	@Autowired
	private SiteService siteService;

	@ApiOperation(value = "Get sites status by username", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Your request has invalid information or structure"),
			@ApiResponse(code = 404, message = "Request not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("{username}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<SiteResponse>> getSitesStatusByUserName(
			@ApiParam(name = "username", required = true, value = "username") @PathVariable(name = "username") final String userName) {
		return ResponseEntity.ok(siteService.getSiteStatusByUserName(userName));
	}

}
