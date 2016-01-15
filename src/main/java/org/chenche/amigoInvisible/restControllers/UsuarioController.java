package org.chenche.amigoInvisible.restControllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chenche.amigoInvisible.domain.Amigo;
import org.chenche.amigoInvisible.domain.Evento;
import org.chenche.amigoInvisible.domain.Grupo;
import org.chenche.amigoInvisible.domain.Integrante;
import org.chenche.amigoInvisible.domain.RegistroData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
		amigo.setLogin(perfilId);
		amigo.setApodo("yo");
		amigo.setEmail("yo@b.es");
		List<Grupo> grupos = new ArrayList<Grupo>();
		Grupo g=new Grupo();
		g.setId(1);
		g.setNombre("G1");
		Evento proximoEvento = new Evento();
		Date f = new Date();
		proximoEvento.setFecha(f );
		proximoEvento.setNombre("ev1");
		g.setProximoEvento(proximoEvento );
		List<Evento> eventos=new ArrayList<Evento>();
		eventos.add(proximoEvento);
		g.setEventos(eventos);
		List<Integrante> integrantes=new ArrayList<Integrante>();
		Integrante i = new Integrante();
		i.setEmail("yo@b.es");
		i.setNombre("yo");
		integrantes.add(i );
		g.setIntegrantes(integrantes);
		grupos.add(g);
		amigo.setGrupos(grupos);
		return amigo;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void crearPerfil(@RequestBody RegistroData registro){
		//TODO Validaciones y creación en backend
		Amigo amigo = registro.getAmigo();
		
		logger.info("Usuario {} creado ",amigo.getApodo());
	}
}
