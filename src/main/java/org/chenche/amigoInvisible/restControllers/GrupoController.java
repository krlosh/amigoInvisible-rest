package org.chenche.amigoInvisible.restControllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chenche.amigoInvisible.domain.Evento;
import org.chenche.amigoInvisible.domain.Grupo;
import org.chenche.amigoInvisible.domain.Integrante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
desenlazarPerfil-> DELETE /grupos/usuario/{usuarioId} (GrupoController ???)
enlazarPerfil	-> POST /grupos/usuario/{usuarioId} (GrupoController ???)
getGrupos		-> GET /grupos (GrupoController )
crearGrupo		-> POST /grupos (GrupoController )
buscarGrupo		-> GET /grupos/{grupoId} (GrupoController )
guardarGrupo	-> POST /grupos/{grupoId} (GrupoController ) 
 * */

@Controller
@RequestMapping("/grupos")
public class GrupoController {

	private static Logger logger = LoggerFactory.getLogger(GrupoController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Grupo> getGrupos(){//TODO: VEr si necesario algun parámetro
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		
		Grupo g=new Grupo();
		g.setId(0);
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
		logger.info("Obtenidos {} grupos ", grupos.size());
		return grupos;
	}
}
