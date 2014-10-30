package com.megoat.es.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-28
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
class JsonTest {

    @Test
    public void testGenJson() throws Exception {

        User user = new User(name: "arne")

        Cat home = new Cat(name: "home", path: "/home")
        home.links.add(new Link(name: "hl1", url: "www.hl1.se", description: "desc"))
        home.links.add(new Link(name: "hl2", url: "www.hl2.se", description: "desc"))

        Cat work = new Cat(name: "work", path: "/work")
        work.links.add(new Link(name: "wl1", url: "www.wl1.se", description: "desc"))
        work.links.add(new Link(name: "wl2", url: "www.wl2.se", description: "desc"))

        user.cats.add(home)
        user.cats.add(work)

        ObjectMapper objectMapper = new ObjectMapper()

        String json = objectMapper.writeValueAsString(user)
        println "$json"
    }

}
