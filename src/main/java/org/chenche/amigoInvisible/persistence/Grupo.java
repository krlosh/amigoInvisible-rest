package org.chenche.amigoInvisible.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Grupo implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3774491910603715742L;

	 
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String key;
	 
	@Persistent
	private String nombre;
	
	@Persistent(mappedBy="grupo"/*,defaultFetchGroup="true"*/) //No se mete en fetch group pq no soporta join
	@Element(dependent="true")
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="fecha asc"))
	private List<Evento> eventos;
	
	@Persistent(mappedBy="grupo"/*,defaultFetchGroup="true"*/) //No se mete en fetch group pq no soporta join
	@Element(dependent="true")
	private Set<IntegranteGrupo> integranteGrupoSet;
	
	@Persistent
	private String loginOwner;

	
	public Grupo(){
		this.eventos=new ArrayList<Evento>();
		this.integranteGrupoSet= new HashSet<IntegranteGrupo>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Evento> getEventos(){
		return this.eventos;
	}
	
	public void setEventos(List<Evento> eventos){
		this.eventos=eventos;
	}
	
	public void anadeEvento(Evento e){
		this.eventos.add(e);
	}
	
	public Evento getPrimerEvento(){
		//Proximo evento desde hoy
		if(this.eventos.isEmpty()){
			return null;
		}
		else{
			Calendar hoy = Calendar.getInstance();
			Iterator<Evento> eventoIt = this.eventos.iterator();
			while(eventoIt.hasNext()){
				Evento evento = eventoIt.next();
				Calendar tmp =Calendar.getInstance();
				tmp.setTime(evento.getFecha());
				if(hoy.before(tmp)){
					return evento;
				}
			}
			return this.eventos.get(0);
		}
		
	}
	
	public String getKey(){
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public List<String> obtenerAmigos(){
		List<String> grupos = new ArrayList<String>();
		for(IntegranteGrupo ga:this.integranteGrupoSet){
			grupos.add(ga.getLoginAmigo());
		}
		return grupos;
	}
	
	public IntegranteGrupo existeIntegrante(Amigo a){
		for(IntegranteGrupo g2:this.integranteGrupoSet){
			if(g2.getLoginAmigo().equals(a.getLogin())){
				return g2;
			}
		}
		return null;
	}
	
	public void anadeIntegrante(Amigo a){
		if(this.existeIntegrante(a)==null){
			IntegranteGrupo ig = new IntegranteGrupo();
			ig.setGrupo(this);
			ig.setLoginAmigo(a.getLogin());
			this.integranteGrupoSet.add(ig);
		}
	}
	
	public void quitarIntegrante(Amigo a){
		IntegranteGrupo ig =this.existeIntegrante(a);
		if(ig!=null){
			this.integranteGrupoSet.remove(ig);
		}
	}

	public Set<IntegranteGrupo> getIntegranteGrupoSet() {
		return integranteGrupoSet;
	}

	public void setIntegranteGrupoSet(Set<IntegranteGrupo> integranteGrupoSet) {
		this.integranteGrupoSet = integranteGrupoSet;
	}

	public String getLoginOwner() {
		return this.loginOwner;
	}

	public void setLoginOwner(String owner) {
		this.loginOwner = owner;
	}
	
}
