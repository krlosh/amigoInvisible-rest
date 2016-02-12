package org.chenche.amigoInvisible.persistence;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * Representa la relación de amigos invisibles para un evento. (Quien regala a quien) 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Asignacion{	
	/**
	 * Clave de uso interno 
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private com.google.appengine.api.datastore.Key key;
	
	@Persistent
	private Key eventoKey;

	@Persistent
	private String loginRegalador;
	
	@Persistent
	private String loginAmigoInvisible;

	/**
	 * Acceso al login regalador
	 * @return
	 */
	public String getLoginRegalador() {
		return loginRegalador;
	}

	public void setLoginRegalador(String loginRegalador) {
		this.loginRegalador = loginRegalador;
	}

	public String getLoginAmigoInvisible() {
		return loginAmigoInvisible;
	}

	public void setLoginAmigoInvisible(String loginAmigoInvisible) {
		this.loginAmigoInvisible = loginAmigoInvisible;
	}

	public com.google.appengine.api.datastore.Key getKey() {
		return key;
	}

	public void setKey(com.google.appengine.api.datastore.Key key) {
		this.key = key;
	}

	public Key getEventoKey() {
		return this.eventoKey;
	}

	public void setSorteo(Sorteo sorteo) {
		this.eventoKey = sorteo.getEventoKey();
	}

	public void setEventoKey(Key sorteoKey) {
		this.eventoKey = sorteoKey;
	}
	
}
