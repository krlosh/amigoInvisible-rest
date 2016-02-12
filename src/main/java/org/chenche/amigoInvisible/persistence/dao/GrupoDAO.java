package org.chenche.amigoInvisible.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.chenche.amigoInvisible.persistence.PMF;
import org.chenche.amigoInvisible.persistence.Amigo;
import org.chenche.amigoInvisible.persistence.Evento;
import org.chenche.amigoInvisible.persistence.Grupo;
import org.chenche.amigoInvisible.persistence.IntegranteGrupo;

public class GrupoDAO {

	private static Logger log =Logger.getLogger(GrupoDAO.class.getName());
	
	public GrupoDAO(){
		
	}
	
	/**
	 * Obtenemos los grupos definidos
	 * @return Lista de grupos
	 */
	public List<Grupo> buscarGrupos(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try{
			tx.begin();
			Query query = pm.newQuery(Grupo.class);
	    	//Como obtener los no asociados???? el datastore no soporta !=
	        List<Grupo> grupos = (List<Grupo>)query.execute(); //TODO Ojo con lazy load
	        tx.commit();
	        return grupos;
		} finally {
			if(tx.isActive()){
				tx.rollback();
			}
            pm.close();
        }
	}
	
	/**
	 * Buscamos un grupo por su identificador
	 * @param idGrupoSeleccionado Identificador del grupo a buscar
	 * @return Instancia de grupo encontrado
	 */
	public Grupo buscarGrupo(String idGrupoSeleccionado){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try{
			tx.begin();
			Grupo grupoAEnlazar = pm.getObjectById(Grupo.class, idGrupoSeleccionado);
			grupoAEnlazar.getEventos();
			grupoAEnlazar.getIntegranteGrupoSet();
			tx.commit();
			return grupoAEnlazar;
		}
		finally{
			if(tx.isActive()){
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
	 * Buscamos los amigos asociados a un grupo
	 * @param grupo Grupo para el que se obtienen los amigos
	 * @return Lista de integrantes del grupo
	 */
	public Collection<IntegranteGrupo> getAmigosDe(Grupo grupo){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		tx.begin();
		try {
		    Query query = pm.newQuery(IntegranteGrupo.class);
			query.declareParameters("org.chenche.amigoInvisible.modelo.Grupo myKey");
			query.setFilter("grupo == myKey");
		    List<IntegranteGrupo> amigos = (List<IntegranteGrupo>) query.execute(grupo);
		    tx.commit();
		    return amigos;
		}
		catch(RuntimeException e){
			log.fine(e.getMessage());
			return new ArrayList<IntegranteGrupo>();
		}
	    finally{
	    	if(tx.isActive()){
	    		tx.rollback();
	    	}
			pm.close();
		}
	}
	
	/**
	 * Guarda un grupo
	 * @param g
	 * @param nuevoEvento
	 * @return
	 * @throws CommandException
	 */
	public Grupo actualizarGrupo(Grupo g,Evento nuevoEvento) throws Exception{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			//if(nuevoEvento!=null){
				pm.makePersistent(g);
			//}
			pm.currentTransaction().commit();
		}
		catch(Throwable t){
			throw new Exception("Error guardando grupo",t);
		}
		finally {
			if (pm.currentTransaction().isActive()) {
		        pm.currentTransaction().rollback();
		     }
		}
		return g;
	}
	
	/**
	 * Borra un grupo, todos sus integrantes y amistades 
	 * @param g
	 * @throws CommandException
	 */
	public void borrarGrupo(Grupo g)throws Exception{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			
			Collection<IntegranteGrupo> igCollection = this.getAmigosDe(g);
			//borrar las amistades cuyo nombre es el grupo
			//ojo que no quiero borrar las instancias de amigo.
			AmigoDAO dao = new AmigoDAO();
			for (Iterator<IntegranteGrupo> iterator = igCollection.iterator(); iterator
					.hasNext();) {
				IntegranteGrupo integranteGrupo = iterator
						.next();
				Amigo amigo = dao.obtenerAmigo(integranteGrupo.getLoginAmigo());
				amigo.getGruposAmigoSet().clear();
				dao.guardarAmigo(amigo);
			}
			pm.currentTransaction().begin();
			g.getIntegranteGrupoSet().clear();
			pm.deletePersistent(g);
			pm.currentTransaction().commit();
		}
		catch(Throwable t){
			throw new Exception("Error guardando grupo",t);
		}
		finally {
			if (pm.currentTransaction().isActive()) {
		        pm.currentTransaction().rollback();
		     }
		}
			
	}
}
