package org.chenche.amigoInvisible.domain;

import java.io.Serializable;

public class Authorization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -576994496994560296L;
	
	private String token;
	
	public Authorization(String token){
		this.token=token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
}
