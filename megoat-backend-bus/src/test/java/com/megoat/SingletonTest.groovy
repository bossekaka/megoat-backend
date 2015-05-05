package com.megoat

import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-11-19
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
class SingletonTest {

    @Test
    public void testName() throws Exception {

        Map m = ["arne":["bo", "kaka"]]
        def x = null
        x.collect {
            println "adding: $it"
        }

        List l = ["1", "2"]
    }

    @Test
    public void testSingleton() throws Exception {

        10.times {
            Thread.start {
                Singleton.instance.hello()
            }
        }

        sleep 2000
    }
}
