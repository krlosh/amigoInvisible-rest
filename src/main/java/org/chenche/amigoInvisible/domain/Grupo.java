package org.chenche.amigoInvisible.domain;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
	/*
	 id:0,
				nombre:'G1',
				proximoEvento:{
					nombre:'ev1',
					fecha:'01/01/2014'
					},
				integrantes:[{
		 				nombre:'yo',
		 				email:'yo@yo.es'
		 			},
		 			{
		 				nombre:'tu',
		 				email:'tu@tu.es'
		 			}
		 		],
		 		eventos:[{
		 				idEvento:0,
						nombre:'b',
						fecha:'01/01/2014'
					},
					{
		 				idEvento:1,
						nombre:'a',
						fecha:'01/01/2013'
					}
		 		]
				}
	 */
	
	public Grupo(){
		this.eventos=new ArrayList<Evento>();
		this.integrantes= new ArrayList<Integrante>();
	}
	
	private long id;
	
	private String nombre;
	
	private List<Integrante> integrantes;
	
	private List<Evento> eventos;
	
	private Evento proximoEvento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Integrante> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<Integrante> integrantes) {
		this.integrantes = integrantes;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento getProximoEvento() {
		return proximoEvento;
	}

	public void setProximoEvento(Evento proximoEvento) {
		this.proximoEvento = proximoEvento;
	}
}
