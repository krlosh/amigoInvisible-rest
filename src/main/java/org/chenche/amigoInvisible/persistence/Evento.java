package org.chenche.amigoInvisible.persistence;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Evento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6994787440160157766L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Date fecha;
	
	@Persistent
	private String nombreEvento;
	
	@Persistent(defaultFetchGroup="true")
	private Grupo grupo;
	
	@Persistent
	private Boolean yaSorteado;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	
	public void setGrupo(Grupo g){
		this.grupo=g;
		g.anadeEvento(this);
	}
	
	public Grupo getGrupo(){
		return this.grupo;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getFechaAsString(){
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(this.fecha);
	}

	public Boolean getYaSorteado() {
		return yaSorteado;
	}

	public void setYaSorteado(Boolean yaSorteado) {
		this.yaSorteado = yaSorteado;
	}
}
