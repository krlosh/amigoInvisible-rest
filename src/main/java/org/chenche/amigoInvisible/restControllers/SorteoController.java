package org.chenche.amigoInvisible.restControllers;

import org.chenche.amigoInvisible.domain.SorteoData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * sortear 		-> POST /sorteo (SorteoController)
 * 
 * @author SINE95.chenche
 *
 */
@Controller
@RequestMapping("/sorteo")
public class SorteoController {
	
	private static Logger logger=LoggerFactory.getLogger(SorteoController.class);
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void sortear(@RequestBody SorteoData sorteo){
		logger.debug("Realizando sorteo ...."+sorteo.getNombre());
	}
}
