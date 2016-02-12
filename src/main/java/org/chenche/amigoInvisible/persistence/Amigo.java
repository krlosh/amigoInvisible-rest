package org.chenche.amigoInvisible.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION,detachable="true")
public class Amigo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5860980347275113031L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long key;
	
	@Persistent
	private String login;
	
	@Persistent
	private String email;
	
	@Persistent
	private String alias;
	
	@Persistent
	private String contrasenia;
	
	@Persistent(mappedBy="amigo"/*,defaultFetchGroup="true"*/)//No se mete en fetch group pq no soporta join
	@Element(dependent="true")
	private Set<Amistad> gruposAmigoSet;
	
	public Amigo(){
		this.gruposAmigoSet = new HashSet<Amistad>();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	public Long getKey(){
		return this.key;
	}
	

	public void setKey(Long key) { 
		this.key = key;
	}

	/*public List<Grupo> obtenerGrupos(){
		List<Grupo> grupos = new ArrayList<Grupo>();
		for(Amistad ga:this.gruposAmigoSet){
			grupos.add(ga.getGrupo());
		}
		return grupos;
	}*/
	
	public List<String> obtenerGrupos(){
		List<String> grupos = new ArrayList<String>();
		for(Amistad ga:this.gruposAmigoSet){
			grupos.add(ga.getNombreGrupo());
		}
		return grupos;
	}
	
	private Amistad existeGrupo(Grupo g){
		for(Amistad g2:this.gruposAmigoSet){
			if(g2.getNombreGrupo().equals(g.getNombre())){
				return g2;
			}
		}
		return null;
	}
	
	public void anadeGrupo(Grupo g){
		if(this.existeGrupo(g)==null){
			Amistad ga = new Amistad();
			ga.setAmigo(this);
			//ga.setGrupo(g);
			ga.setNombreGrupo(g.getNombre());
			this.gruposAmigoSet.add(ga);
		}
	}
	
	public void quitaGrupo(Grupo g){
		Amistad ga = this.existeGrupo(g);
		if(ga!=null){
			this.gruposAmigoSet.remove(ga);
		}
	}

	public Set<Amistad> getGruposAmigoSet() {
		return gruposAmigoSet;
	}

	public void setGruposAmigoSet(Set<Amistad> gruposAmigoSet) {
		this.gruposAmigoSet = gruposAmigoSet;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getNombreCompleto(){
		StringBuilder strBuilder = new StringBuilder(this.login);
		if(this.alias!=null&&this.alias.length()>0&&!this.alias.equalsIgnoreCase(this.login)){
			strBuilder.append("( ");
			strBuilder.append(this.alias);
			strBuilder.append(" )");
		}
		return strBuilder.toString();
	}
}
