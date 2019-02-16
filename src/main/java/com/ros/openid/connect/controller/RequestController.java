package com.ros.openid.connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ros.openid.connect.domain.AuthRequest;
import com.ros.openid.connect.service.RequestService;

@Controller
public class RequestController {

	@Autowired
	public RequestService requestService;

	@RequestMapping("/auth")
	public String authRequest(Model model) {
		model.addAttribute("authRequest", new AuthRequest());
		return "authRequest";
	}

	@RequestMapping("/registerNewClient")
	public String registerNewClient(Model model) {
		model.addAttribute("registerNewClient", new AuthRequest());
		System.out.println("registerNewClient");
		return "registerNewClient";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String authenticate(@RequestBody AuthRequest request) {
		return requestService.authentication(request);
	}
	
	@RequestMapping(value = "/registerNewClient", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String registerNewClient(@RequestBody AuthRequest request) throws JsonProcessingException {
		System.out.println("Request received Client URI:" +request.getClientURI() + " RedirectURI "+request.getRedirectURI());
		return requestService.registerClient(request);
	}
	
}