package org.chenche.amigoInvisible.restControllers;

import org.chenche.amigoInvisible.domain.Evento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * eliminarEvento	-> DELETE /grupos/eventos/{eventoId} (EventoController)
 * añadirEvento		-> POST /grupos/eventos/{eventoId} (EventoController)
 * 
 * @author SINE95.chenche
 *
 */
@Controller
@RequestMapping("/grupos/eventos")
public class EventoController {

	private static Logger logger = LoggerFactory.getLogger(EventoController.class);
	
	@RequestMapping(value = "/{eventoId}",method = RequestMethod.DELETE)
	public void eliminarEvento(@PathVariable(value = "eventoId") String eventoId,@RequestParam(value="grupoId") String grupoId){
		logger.info("Eliminando evento {} de grupo {}",eventoId,grupoId);
	}
	
	@RequestMapping(value = "/{eventoId}",method = RequestMethod.POST)
	public void anadirEvento(@PathVariable(value = "eventoId") String eventoId,@RequestBody Evento evento,@RequestParam(value="grupoId") String grupoId){
		logger.info("Creando evento {} en grupo {} con valores {}",eventoId,grupoId,evento.getNombre());
	}
}
