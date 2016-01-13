package org.chenche.amigoInvisible.domain;

import java.util.ArrayList;
import java.util.List;

public class Amigo {

	private String login;
	
	private String apodo;
	
	private String email;
	
	private List<Grupo> grupos;
	
	public Amigo(){
		this.grupos=new ArrayList<Grupo>();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	
}
