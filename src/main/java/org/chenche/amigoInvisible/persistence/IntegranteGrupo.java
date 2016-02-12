package org.chenche.amigoInvisible.persistence;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
/**
 * Clase necesaria para navegar por la relación desde el grupo a los amigos enlazados. 
 * Si empleamos la clase {@link Amistad} , al obtener las amistades de un grupo nos da error pq no puede manejar varias instancias Amigo (rootElement) 
 * @author Administrador
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION,detachable="true")
public class IntegranteGrupo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1418918653079196363L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent(defaultFetchGroup="true")
	private Grupo grupo;
	
	@Persistent
	private String loginAmigo;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getLoginAmigo() {
		return loginAmigo;
	}

	public void setLoginAmigo(String loginAmigo) {
		this.loginAmigo = loginAmigo;
	}
	
	
}
