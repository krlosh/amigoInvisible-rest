package org.chenche.amigoInvisible.restControllers;

import javax.servlet.http.HttpServletRequest;

import org.chenche.amigoInvisible.domain.Authorization;
import org.chenche.amigoInvisible.domain.ErrorMessage;
import org.chenche.amigoInvisible.domain.UserData;
import org.chenche.amigoInvisible.persistence.Amigo;
import org.chenche.amigoInvisible.persistence.dao.AmigoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/sesion")
public class SesionController {

	private static Logger logger = LoggerFactory.getLogger(SesionController.class);
			
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Authorization login(@RequestBody UserData user,HttpServletRequest request){
		try {
			Amigo amigo  = new AmigoDAO().obtenerAmigo(user.getName());
			if(amigo.getContrasenia().equals(user.getPassword())){
				String token=request.getSession(true).getId();
			
				return new Authorization(token);
			}
			else{
				throw new IllegalArgumentException("wrong user/pass");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("wrong user/pass");
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody void logout(@RequestBody String token,HttpServletRequest request){ //ver tb RequestHeader
		//TODO Implementar algo más?
		request.getSession().invalidate();
		logger.info("{} logged Out ", token);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN/*,  reason="Illegal request, please verify your payload"*/)//Si pones reason no devuelve como ResponseBody ErrorMessage
	public @ResponseBody ErrorMessage handleClientErrors(IllegalArgumentException ex) { 
		return new ErrorMessage(ex.getLocalizedMessage());
	}
}
