package org.chenche.amigoInvisible.restControllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.chenche.amigoInvisible.domain.Amigo;
import org.chenche.amigoInvisible.domain.ErrorMessage;
import org.chenche.amigoInvisible.domain.Evento;
import org.chenche.amigoInvisible.domain.Grupo;
import org.chenche.amigoInvisible.domain.Integrante;
import org.chenche.amigoInvisible.domain.RegistroData;
import org.chenche.amigoInvisible.persistence.dao.AmigoDAO;
import org.chenche.amigoInvisible.persistence.dao.GrupoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * cargarPerfil 	-> GET /usuario/{perfilId} (UsuarioController)
 * guardarPerfil	-> POST /usuario/{perfilId} (UsuarioController)

 * @author SINE95.chenche
 *
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@RequestMapping(value = "/{perfilId}",method = RequestMethod.GET)
	public @ResponseBody Amigo cargarPerfil(@PathVariable(value = "perfilId") String perfilId){
		Amigo amigo = new Amigo();
		try {
			AmigoDAO amigoDAO = new AmigoDAO();
			org.chenche.amigoInvisible.persistence.Amigo amigoBD = amigoDAO.obtenerAmigo(perfilId);
			GrupoDAO grupoDAO = new GrupoDAO();
			amigo.setApodo(amigoBD.getAlias());
			amigo.setEmail(amigoBD.getEmail());
			amigo.setLogin(amigoBD.getLogin());
			List<Grupo> grupos = new ArrayList<>();
			for(org.chenche.amigoInvisible.persistence.Grupo gTx :amigoDAO.obtenerGruposAmigo(amigoBD)){
				Grupo g = new Grupo();
				g.setId(new Long(gTx.getKey()));//TODO:modificar
				g.setNombre(gTx.getNombre());
				Collection<org.chenche.amigoInvisible.persistence.IntegranteGrupo> integrantesTx = grupoDAO.getAmigosDe(gTx);
				List<Integrante> integrantes = new ArrayList<>();
				for(org.chenche.amigoInvisible.persistence.IntegranteGrupo igTx:integrantesTx){
					if(!igTx.getLoginAmigo().equals(amigo.getLogin())){
						org.chenche.amigoInvisible.persistence.Amigo amigo2  = amigoDAO.obtenerAmigo(igTx.getLoginAmigo());
						Integrante integrante = new Integrante();
						integrante.setEmail(amigo2.getEmail());
						integrante.setNombre(amigo2.getNombreCompleto());
						integrantes.add(integrante);
					}
				}
				g.setIntegrantes(integrantes);
				List<Evento> eventos = new ArrayList<>();
				for(org.chenche.amigoInvisible.persistence.Evento evt:gTx.getEventos()){
					Evento e = new Evento();
					e.setFecha(evt.getFecha());
					e.setId(evt.getKey().getId());
					e.setNombre(evt.getNombreEvento());
					eventos.add(e);
				}
				if(!eventos.isEmpty()){
					g.setProximoEvento(eventos.get(0));
				}
				g.setEventos(eventos);
				grupos.add(g);
			}
			
			amigo.setGrupos(grupos);
			
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return amigo;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void crearPerfil(@RequestBody RegistroData registro){
		//TODO Validaciones y creación en backend
		Amigo amigo = registro.getAmigo();
		org.chenche.amigoInvisible.persistence.Amigo amigoTx =  validate(amigo);
		amigoTx.setContrasenia(registro.getUserData().getPassword());
		new AmigoDAO().guardarAmigo(amigoTx );
		logger.info("Usuario {} creado ",amigo.getApodo());
	}
	
	private org.chenche.amigoInvisible.persistence.Amigo validate(Amigo amigo) {
		org.chenche.amigoInvisible.persistence.Amigo amigoTx = new org.chenche.amigoInvisible.persistence.Amigo();
		if(amigo.getLogin().isEmpty()){
			throw new IllegalArgumentException("login es obligatorio");
		}
		if(amigo.getApodo().isEmpty()){
			throw new IllegalArgumentException("apodo es obligatorio");
		}
		if(amigo.getEmail().isEmpty()){
			throw new IllegalArgumentException("email es obligatorio");
		}
		amigoTx.setLogin(amigo.getLogin());
		amigoTx.setAlias(amigo.getApodo());
		amigoTx.setEmail(amigo.getEmail());
		return amigoTx;
	}

	@RequestMapping(value = "/{perfilId}",method = RequestMethod.POST)
	public @ResponseBody Amigo guardarPerfil(@PathVariable(value = "perfilId") String perfilId,@RequestBody RegistroData registro){
		org.chenche.amigoInvisible.persistence.Amigo amigoTx =  validate(registro.getAmigo()); //TODO Qué campos se pueden cambiar?
		amigoTx.setContrasenia(registro.getUserData().getPassword());
		new AmigoDAO().guardarAmigo(amigoTx );
		return registro.getAmigo();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN/*,  reason="Illegal request, please verify your payload"*/)//Si pones reason no devuelve como ResponseBody ErrorMessage
	public @ResponseBody ErrorMessage handleClientErrors(IllegalArgumentException ex) { 
		return new ErrorMessage(ex.getLocalizedMessage());
	}
}
