package org.chenche.amigoInvisible.persistence.dao;

import static org.junit.Assert.fail;

import org.chenche.amigoInvisible.LocalServiceTestCase;
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
			assertNotNull(this.dao.obtenerAmigo("k"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
