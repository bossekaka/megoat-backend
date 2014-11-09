package com.megoat.it;

import com.megoat.it.arne.JavaIntegrationTest;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-11-09
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GroovyIntegrationTest.class,
        JavaIntegrationTest.class
})
public class ItSetUp {

    @BeforeClass
    public static void setUp() throws Exception {

        if (System.getProperty("env") == null) {
            System.setProperty("env", "it-from-setup");
        }

    }
}
