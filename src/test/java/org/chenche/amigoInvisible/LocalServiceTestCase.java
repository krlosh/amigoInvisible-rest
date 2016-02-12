package org.chenche.amigoInvisible;

import org.junit.Ignore;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import junit.framework.TestCase;
@Ignore
public class LocalServiceTestCase extends TestCase {

	
	private final LocalServiceTestHelper helper =
		      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	
	 @Override
    public void setUp() throws Exception {
        helper.setUp();               
    }

    @Override
    public void tearDown() throws Exception {
        // not strictly necessary to null these out but there's no harm either
    	helper.tearDown();
    }

}
