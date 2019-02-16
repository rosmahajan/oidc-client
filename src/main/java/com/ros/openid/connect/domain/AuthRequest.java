package com.ros.openid.connect.domain;

import java.util.List;

public class AuthRequest {
	
	private String tokenEndPoint;
	private String userEndPoint;
	private String userName;
	private String password;
	private String clientName;
	private String clientSecret;
	private String grantType;
	private String scope;
	private List<String> redirectURI;
	private String clientURI;
	
	
	public String getTokenEndPoint() {
		return tokenEndPoint;
	}
	public void setTokenEndPoint(String tokenEndPoint) {
		this.tokenEndPoint = tokenEndPoint;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getUserEndPoint() {
		return userEndPoint;
	}
	public void setUserEndPoint(String userEndPoint) {
		this.userEndPoint = userEndPoint;
	}
	@Override
	public String toString() {
		return "Request [tokenEndPoint=" + tokenEndPoint + ", userEndPoint=" + userEndPoint + ", userName=" + userName
				+ ", password=" + password + ", clientName=" + clientName + ", clientSecret=" + clientSecret
				+ ", grantType=" + grantType + ", scope=" + scope + "]";
	}
	public String getClientURI() {
		return clientURI;
	}
	public void setClientURI(String clientURI) {
		this.clientURI = clientURI;
	}
	
	public void setRedirectURI(List<String> redirectURI) {
		this.redirectURI = redirectURI;
	}
	public List<String> getRedirectURI() {
		return redirectURI;
	}

}
