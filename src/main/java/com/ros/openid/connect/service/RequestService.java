package com.ros.openid.connect.service;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ros.openid.connect.domain.AuthRequest;
import com.ros.openid.connect.domain.Response;
import com.ros.openid.connect.utils.JSONUtil;

@Service
public class RequestService {

	@Autowired
	public RestTemplate restTemplate;

	public String authentication(AuthRequest request) {

		HttpHeaders headers = createHeaders(request.getClientName(), request.getClientSecret());
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		
		if (!StringUtils.isBlank(request.getGrantType())) {
			map.put("grant_type", singletonList(request.getGrantType()));
		}
		if (!StringUtils.isBlank(request.getUserName())) {
			map.put("username", singletonList(request.getUserName()));
		}
		if (!StringUtils.isBlank(request.getGrantType())) {
			map.put("password", singletonList(request.getPassword()));
		}
		if (!StringUtils.isBlank(request.getScope())) {
			map.put("scope", singletonList(request.getScope()));
		}
		
		HttpEntity entity = new HttpEntity(map, headers);
		URI uri = null;
		try {
			uri = new URI(request.getTokenEndPoint());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ResponseEntity<String> reponseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		Response response = new Response();
		System.out.println(reponseEntity.getBody());
		try {
			response = (Response) JSONUtil.fromJSON(reponseEntity.getBody(), Response.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!StringUtils.isBlank(response.getId_token())){
			return testDecodeJWT(response.getId_token());
		}else{			
			return getUserInfo(response.getAccess_token(), request.getUserEndPoint());
		}
	}

	public String registerClient(AuthRequest request) throws JsonProcessingException {

		HttpHeaders headers = new HttpHeaders() ;
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		String jsonRequest = JSONUtil.createJSON(request);
		
		
		//map.put("redirect_uris", "[https://client.example.com:8443/callback]");
		
////		map.put("redirect_uris", singletonList("[https://client.example.com:8443/callback]"));
////		
////		
////		if (!StringUtils.isBlank(request.getClientName())) {
////			map.put("client_name", singletonList(request.getClientName()));
////		}
////		if (!StringUtils.isBlank(request.getClientURI())) {
////			map.put("client_uri", singletonList(request.getClientURI()));
////		}
//		System.out.println(map.get("redirect_uris"));
//		System.out.println(map.get("client_name"));
//		System.out.println(map.get("client_uri"));
		HttpEntity entity = new HttpEntity(jsonRequest, headers);
		URI uri = null;
		try {
			uri = new URI(request.getUserEndPoint());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("URI "+uri.getPath()+ " Entity :"+ entity.getHeaders());
		//ResponseEntity<String> resutl = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		ResponseEntity<String> resutl = restTemplate.postForEntity(uri, request, String.class);
		//restTemplate().postForObject(getRestServiceURL() + serviceUrl.trim(), serviceRequest,
		//uk.gov.hscic.careid.commons.domain.ServiceResponse.class);
		
		Response response = new Response();
		System.out.println(resutl.getBody());
		try {
			response = (Response) JSONUtil.fromJSON(resutl.getBody(), Response.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resutl.getBody();
	}

	@SuppressWarnings("serial")
	private HttpHeaders createHeaders(final String username, final String password) {
		return new HttpHeaders() {
			{
				if (!StringUtils.isBlank(username)) {
					String auth = username + ":" + password;
					byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
					String authHeader = "Basic " + new String(encodedAuth);
					set("Authorization", authHeader);
				}
			}
		};
	}

	public String testDecodeJWT(String jwtToken) {
		System.out.println("------------ Decode JWT ------------");
		Map<String, String> map = new HashMap<>();
		String[] split_string = jwtToken.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		String base64EncodedSignature = split_string[2];
		System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));
		map.put("JWT Header", header);
		System.out.println("JWT Header : " + header);

		System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
		String body = new String(base64Url.decode(base64EncodedBody));
		System.out.println("JWT Body : " + body);
		map.put("JWT Body", body);
		return body;
	}
	
	public String getUserInfo(String accesstoken, String url ) {
		
		HttpHeaders headers = new HttpHeaders();
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		headers.set("Authorization", "Bearer "+accesstoken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<String> resutl = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		System.out.println(resutl.getBody());
		return resutl.getBody();
	}

}