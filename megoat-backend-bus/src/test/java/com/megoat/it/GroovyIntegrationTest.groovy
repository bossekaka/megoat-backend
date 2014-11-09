package com.megoat.it

import org.junit.Test
import static com.jayway.restassured.RestAssured.*
import static com.jayway.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-11-04
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
class GroovyIntegrationTest {

    @Test
    public void testIt() throws Exception {

        def env = System.getProperty("env")

        println "*** groovy IT-test [env=$env]"

        get("http://localhost:8090/rest/arne/").then().assertThat().statusCode(200)
    }
}
