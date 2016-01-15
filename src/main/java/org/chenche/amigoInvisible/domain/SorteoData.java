package org.chenche.amigoInvisible.domain;

import java.util.List;

public class SorteoData {

	private String nombre;
	
	private String introduccion;
	
	private List<Integrante> participantes;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIntroduccion() {
		return introduccion;
	}

	public void setIntroduccion(String introduccion) {
		this.introduccion = introduccion;
	}

	public List<Integrante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Integrante> participantes) {
		this.participantes = participantes;
	}
	
	
}
