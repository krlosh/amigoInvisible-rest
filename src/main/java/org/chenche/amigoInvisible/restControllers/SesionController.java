package org.chenche.amigoInvisible.restControllers;

import javax.servlet.http.HttpServletRequest;

import org.chenche.amigoInvisible.domain.Authorization;
import org.chenche.amigoInvisible.domain.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sesion")
public class SesionController {

	private static Logger logger = LoggerFactory.getLogger(SesionController.class);
			
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Authorization login(@RequestBody UserData user,HttpServletRequest request){
		String token=request.getSession(true).getId();
		//TODO evaluar userdata
		return new Authorization(token);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody void logout(@RequestParam(value="token") String token){ //ver tb RequestHeader
		//TODO Implementar
		logger.info("{} logged Out ", token);
	}
}
