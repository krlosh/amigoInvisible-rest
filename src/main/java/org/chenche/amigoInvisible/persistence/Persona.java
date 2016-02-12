package org.chenche.amigoInvisible.persistence;

public class Persona {

	private String nombre;
	
	private String email;

	public Persona(String nombre, String e) {
		this.nombre=nombre;
		this.email=e;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return this.nombre+"["+this.email+"]";
	}
	
}
