package org.chenche.amigoInvisible.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
/**
 * 
 * @author Administrador
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Sorteo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2684252148885365920L;
	
	/**
	 * Clave de uso interno 
	 */
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/**
	 * Evento al que pertenec el sorteo
	 */  
	@Persistent(defaultFetchGroup="true")
	private Key eventoKey;
	
	/**
	 * Quien regala a quien (login)
	 */
	@Persistent
	private ArrayList<Asignacion> amigos;
	
	@NotPersistent
	private Map<Amigo,Amigo> mapRegalados;  

	
	/**
	 * Constructor
	 */
	public Sorteo(){
		this.amigos = new ArrayList<Asignacion>();
		this.mapRegalados = new Hashtable<Amigo,Amigo>();
	}

	/**
	 * Método de acceso al atributo evento
	 * @return
	 */
	public Key getEventoKey() {
		return eventoKey;
	}

	public void setEventoKey(Key eventoKey) {
		this.eventoKey = eventoKey;
	}

	/**
	 * Método de establecimiento del atributo evento
	 * @param evento
	 */
	public void setEvento(Evento evento) {
		this.eventoKey = evento.getKey();
	}

	/**
	 * Método de acceso al atributo amigos
	 * @return
	 */
	public ArrayList<Asignacion> getAmigos() {
		
		return this.amigos;
	}

	/**
	 * Método de establecimiento del atributo amigos
	 * @param evento
	 */
	public void setAmigos(ArrayList<Asignacion> amigos) {
		this.amigos = amigos;
	}

	/**
	 * Método de acceso del atributo key
	 * @param evento
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * Método de establecimiento del atributo key
	 * @param evento
	 */
	public void setKey(Key key) {
		this.key = key;
	}
	
	/**
	 * Obtenemos asignaciones
	 * @return
	 */
	public Map<Amigo,Amigo> getAsignaciones(){
		return this.mapRegalados;
	}
	
	/** 
	 * Establecemos amigoinvisible 
	 * @param regalador
	 * @param amigoInvisible
	 */
	public void setAmigoInvisible(Amigo regalador,Amigo amigoInvisible){
		this.mapRegalados.put(regalador, amigoInvisible);
		Asignacion asignacion = new Asignacion();
		asignacion.setSorteo(this);
		asignacion.setLoginAmigoInvisible(amigoInvisible.getLogin());
		asignacion.setLoginRegalador(regalador.getLogin());
		this.amigos.add(asignacion);
	}
	
}
