package com.ros.openid.connect.domain;

import java.util.List;

public class RedirectURI {

	private List<String> redirectURI;
	
	public void setRedirectURI(List<String> redirectURI) {
		this.redirectURI = redirectURI;
	}
	public List<String> getRedirectURI() {
		return redirectURI;
	}
}
