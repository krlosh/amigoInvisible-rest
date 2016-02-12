package org.chenche.amigoInvisible.persistence.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.chenche.amigoInvisible.persistence.PMF;
import org.chenche.amigoInvisible.persistence.Amigo;
import org.chenche.amigoInvisible.persistence.Amistad;
import org.chenche.amigoInvisible.persistence.Grupo;

public class AmigoDAO {

	private static Logger log =Logger.getLogger(AmigoDAO.class.getName());

	/**
	 * Almacena un amigo
	 * @param amigo Amigo a guardar
	 */
	public void guardarAmigo(Amigo amigo){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
        try {
        	tx.begin();
            pm.makePersistent(amigo);
            tx.commit();
            log.fine("Ok.guardarAmigo " +amigo.getLogin());
        } finally {
        	if(tx.isActive()){
        		tx.rollback();
        	}
            pm.close();
        }
	}
	
	/**
	 * Enlaza un amigo a un grupo
	 * @param amigo Amigo
	 * @param nombreGrupo Nombre del grupo
	 * @throws CommandException
	 */
	public void anadeGrupoAmigo(Amigo amigo,String nombreGrupo) throws Exception{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			String query = "select from " + Grupo.class.getName() + " where nombre == '"+nombreGrupo+"'";
			Grupo grupo=null;
			pm.currentTransaction().begin();
			try{
				List<Grupo> grupos = (List<Grupo>) pm.newQuery(query).execute();
				if(grupos.size()>1){
					throw new Exception("Múltiples grupos con mismo nombre");
				}
				else if(grupos.isEmpty()){
					grupo = new Grupo();
					grupo.setNombre(nombreGrupo);
					grupo.setLoginOwner(amigo.getLogin());
					pm.makePersistent(grupo);
					pm.currentTransaction().commit();//chapuza, no puede gestionar dos root entities
					pm.currentTransaction().begin();
				}
				else{
					grupo=grupos.get(0);
				}
				amigo.anadeGrupo(grupo);
				pm.makePersistent(amigo);
				pm.currentTransaction().commit();
				//esto sigue siendo una ñapa ...
				pm.currentTransaction().begin();
				grupo.anadeIntegrante(amigo);
				pm.makePersistent(grupo);
				pm.currentTransaction().commit();
				log.fine("Ok. anadeGrupoAmigo"+nombreGrupo+" , " +amigo.getLogin());
			}finally {
				if (pm.currentTransaction().isActive()) {
			        pm.currentTransaction().rollback();
			     }
				pm.close();
			}
		}
		catch(Throwable e){
			throw new Exception(e);
		}
	}
	
	/**
	 * Busqueda de los grupos a los que pertenece un amigo
	 * @param amigo Amigo que se consulta
	 * @return 
	 */
	public Set<Grupo> obtenerGruposAmigo(Amigo amigo){
		Set<Grupo> resultado = new HashSet<Grupo>();
		
		Set<Amistad> amistades = amigo.getGruposAmigoSet();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		tx.begin();
		Query query = pm.newQuery(Grupo.class);		
    	//Esto es una prueba para que lo cargue.
		try {
	    	for (Iterator<Amistad> iterator = amistades.iterator(); iterator
					.hasNext();) {
				Amistad amistad = iterator.next();
				query.declareParameters("String myKey");
				query.setFilter("nombre == myKey");
			    List<Grupo> grupos = (List<Grupo>) query.execute(amistad.getNombreGrupo());
			    if (grupos.isEmpty()){//si hay amistad y no grupo, borro la amistad
			    	pm.deletePersistent(amistad);
			    }else{
			    	for (Iterator<Grupo> iterator2 = grupos.iterator(); iterator2
							.hasNext();) {
						Grupo grupo = iterator2.next();
						grupo.getEventos();//TODO Necesario por Lazy load
						grupo.getIntegranteGrupoSet(); //TODO Necesario por Lazy load
						resultado.add(grupo);
					}
			    }
	    	}	    		    	
	    	tx.commit();
	    	log.fine("Ok.obtenerGruposAmigo " +amigo.getLogin());	
		} finally {
			if(tx.isActive()){
				tx.rollback();
			}
            pm.close();
        }
    	return resultado;
	}
	
	/**
	 * Buscamos un Amigo por su nombre de usuario
	 * @param nombreUsuario Nombre de usuario
	 * @return Instancia de amigo
	 * @throws CommandException
	 */
	public Amigo obtenerAmigo(String nombreUsuario) throws Exception{
		String query = "select from " + Amigo.class.getName() + " where login == '"+nombreUsuario+"'";
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try{
			tx.begin();
			List<Amigo> amigos = (List<Amigo>) pm.newQuery(query).execute();
	        log.fine("Encontrados usuarios num="+amigos.size()+" con login " + nombreUsuario);
	        if(amigos.size()!=1){
				throw new Exception("Error login");
	        }
	        else{
	        	Amigo amigo = amigos.get(0);
	        	int sizeAmigos = amigo.getGruposAmigoSet().size();
	        	log.fine(amigo.getLogin() +" pertenece a " +amigo.getGruposAmigoSet().size()); //traza de debug necesaria por lazy loading
	        	return amigo;
			}	        
		}
        finally {
        	tx.commit();
            pm.close();
        }
	}
}
