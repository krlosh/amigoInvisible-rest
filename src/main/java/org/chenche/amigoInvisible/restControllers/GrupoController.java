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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	private Grupo mockGrupo(long id){
		Grupo g=new Grupo();
		g.setId(id);
		g.setNombre("G"+id);
		Evento proximoEvento = new Evento();
		Date f = new Date();
		proximoEvento.setFecha(f );
		proximoEvento.setNombre("ev"+id);
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
		return g;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Grupo> getGrupos(){//TODO: VEr si necesario algun parámetro
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		
		Grupo g= this.mockGrupo(0);
		grupos.add(g);
		grupos.add(this.mockGrupo(1));
		logger.info("Obtenidos {} grupos ", grupos.size());
		return grupos;
	}
	
	@RequestMapping(value = "/{grupoId}",method = RequestMethod.GET)
	public @ResponseBody Grupo getGrupo(@PathVariable(value = "grupoId")  String grupoId){
		logger.debug("Buscando grupo {}",grupoId);
		Grupo g = mockGrupo(Long.valueOf(grupoId));
		return g;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void crearGrupo( @RequestBody Grupo grupoACrear){
		logger.debug("Creando grupo {}",grupoACrear.getId());
	}
	
	@RequestMapping(value = "/{grupoId}",method = RequestMethod.POST)
	public void guardarGrupo(@PathVariable(value = "grupoId")  String grupoId, @RequestBody Grupo grupoAGuardar){
		logger.debug("Guardando grupo {}",grupoAGuardar.getId());
	}
	
	@RequestMapping(value = "/{grupoId}/usuario",method = RequestMethod.POST)
	public void enlazarPerfil(@PathVariable(value = "grupoId")  String grupoId,@RequestBody Integrante integrante){//	-> POST /grupos/{grupoId}/usuario/?{integrante}
		logger.debug("Anadiendo {} a grupo {}",integrante.getNombre(),grupoId);
	}
	
	@RequestMapping(value = "/{grupoId}/usuario",method = RequestMethod.DELETE)
	public void desenlazarPerfil(@PathVariable(value = "grupoId")  String grupoId,@RequestBody Integrante integrante){//	-> POST /grupos/{grupoId}/usuario/?{integrante}
		logger.debug("Eliminando {} de grupo {}",integrante.getNombre(),grupoId);
	}
}
