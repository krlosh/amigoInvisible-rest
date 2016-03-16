package org.chenche.amigoInvisible.persistence.dao;

import static org.junit.Assert.fail;

import org.chenche.amigoInvisible.LocalServiceTestCase;
import org.chenche.amigoInvisible.persistence.Amigo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AmigoDAOTest extends LocalServiceTestCase{

	private AmigoDAO dao;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.dao=new AmigoDAO();
	}
	
	 @After
    public void tearDown()throws Exception  {
		 super.tearDown();
    }
	 
	@Test
	public void testObtenerAmigo() {
		try {
			Amigo amigo = new Amigo();
			amigo.setLogin("k");
			amigo.setAlias("k");
			amigo.setContrasenia("k");
			amigo.setKey(1l);
			amigo.setEmail("a@b.es");
			this.dao.guardarAmigo(amigo);
			assertNotNull(this.dao.obtenerAmigo("k"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testGuardarAmigo(){
		Amigo amigo = new Amigo();
		amigo.setLogin("k");
		amigo.setAlias("k");
		amigo.setContrasenia("k");
		amigo.setKey(1l);
		amigo.setEmail("a@b.es");
		try{
			this.dao.obtenerAmigo("k");
		}
		catch(Exception e){
			if(e.getMessage().equals("Error login")){
				this.dao.guardarAmigo(amigo );
			}
		}
	}

}