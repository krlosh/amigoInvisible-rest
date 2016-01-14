package org.chenche.amigoInvisible.restControllers;

import java.util.Random;

import org.chenche.amigoInvisible.domain.Evento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * eliminarEvento	-> DELETE /grupos/eventos/{eventoId} (EventoController)
 * añadirEvento		-> POST /grupos/eventos/{eventoId} (EventoController)
 * 
 * @author SINE95.chenche
 *
 */
@Controller
@RequestMapping("/grupos/{grupoId}/eventos")
public class EventoController {

	private static Logger logger = LoggerFactory.getLogger(EventoController.class);
	
	@RequestMapping(value = "/{eventoId}",method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void eliminarEvento(@PathVariable(value = "eventoId") String eventoId,@PathVariable(value="grupoId") String grupoId){
		logger.info("Eliminando evento {} de grupo {}",eventoId,grupoId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Evento anadirEvento(@RequestBody Evento evento,@PathVariable(value="grupoId") String grupoId){
		long eventoId=new Random().nextLong();
		logger.info("Creando evento {} en grupo {} con valores {}",eventoId,grupoId,evento.getNombre());
		evento.setId(eventoId);
		return evento;
	}
}
