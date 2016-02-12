package org.chenche.amigoInvisible.persistence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.chenche.amigoInvisible.persistence.PMF;
import org.chenche.amigoInvisible.persistence.Amigo;
import org.chenche.amigoInvisible.persistence.Amistad;
import org.chenche.amigoInvisible.persistence.Evento;
import org.chenche.amigoInvisible.persistence.Grupo;
import org.chenche.amigoInvisible.persistence.Sorteo;

public class SorteoDAO {
	private static Logger log =Logger.getLogger(SorteoDAO.class.getName());
	
	/**
	 * Guardamos los datos de un sorteo
	 * @param sorteo
	 */
	public void guardarSorteo(Sorteo sorteo){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
        try {
        	tx.begin();
            pm.makePersistent(sorteo);
            tx.commit();
            log.fine("Sorteo guardado");
            //MArcar evento como sorteado
            tx.begin();
            Evento evento = pm.getObjectById(Evento.class, sorteo.getEventoKey());
            evento.setYaSorteado(true);
            pm.makePersistent(evento);
            tx.commit();
        } finally {
        	if(tx.isActive()){
        		tx.rollback();
        	}
            pm.close();
        }
	}
	
	/**
	 * Buscamos los eventos que se sortean en una fecha
	 * @param fecha
	 * @return
	 */
	public Collection<Evento> buscarEventos(Date fecha){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			Calendar fechaDesde = Calendar.getInstance();
			fechaDesde.setTime(fecha);
			fechaDesde.set(Calendar.HOUR_OF_DAY, 0);
			fechaDesde.set(Calendar.MINUTE, 0);
			fechaDesde.set(Calendar.SECOND, 0);
			fechaDesde.set(Calendar.MILLISECOND, 0);
			
			Calendar fechaHasta = Calendar.getInstance();
			fechaHasta.setTime(fechaDesde.getTime());
			fechaHasta.add(Calendar.DAY_OF_MONTH, +1);
			
			//TODO: Tener en cuenta las fechas
		    //Query query = pm.newQuery(Evento.class,"yaSorteado==false && fecha>=desde && fecha<=hasta");
			Query query = pm.newQuery(Evento.class,"yaSorteado==false && fecha>=:desde && fecha<=:hasta");
			//query.declareParameters("java.util.Date desde, java.util.Date hasta");
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("desde", fechaDesde.getTime());
			parametros.put("hasta", fechaHasta.getTime());
		    List<Evento> eventos = (List<Evento>) query.executeWithMap(parametros);
		    ArrayList<Evento> resultado = new ArrayList<Evento>();
		    resultado.addAll(eventos);
		    return resultado;
		}
		catch(RuntimeException e){
			log.fine(e.getMessage());
			return new ArrayList<Evento>();
		}
	    finally{
	    	tx.commit();
			pm.close();
		}
	}
	/**
	 * Buscamos las amistades de un grupo por el nombre del grupo
	 * @param grupo
	 * @return
	 */
	public List<Amigo> buscarAmistades(Grupo grupo){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//Transaction tx=pm.currentTransaction();
		try {
			//tx.begin();
			Query query = pm.newQuery(Amistad.class,"nombreGrupo==grupo");
			query.declareParameters("java.lang.String grupo");
			List<Amistad> amistades =(List<Amistad>)query.execute(grupo.getNombre());
			ArrayList<Amigo> resultado = new ArrayList<Amigo>();
			for (Iterator<Amistad> iterator = amistades.iterator(); iterator.hasNext();) {
				Amistad amistad =  iterator.next();
				amistad.getAmigo().getLogin();
				amistad.getAmigo().getEmail();
				resultado.add(amistad.getAmigo());
			}
			log.info("Encontradas amistades"+resultado.size()+" de "+grupo.getNombre());
			return resultado;
		}
		catch(RuntimeException e){
			log.fine(e.getMessage());
			return new ArrayList<Amigo>();
		}
	    finally{
	    	//tx.commit();
			pm.close();
		}
	}
}
