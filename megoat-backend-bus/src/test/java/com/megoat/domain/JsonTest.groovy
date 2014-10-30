package com.megoat.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-27
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
class JsonTest {

    @Test
    public void testParse() throws Exception {

        Resources resources = new Resources()
        resources.user = "pater"

        resources.resources.add(new Resource("www.l1.se", "/work/uc", "l1"))
        resources.resources.add(new Resource("www.l2.se", "/work/uc", "l2"))
        resources.resources.add(new Resource("www.l3.se", "/work/dn", "l3"))
        resources.resources.add(new Resource("www.l4.se", "/work/dn/es", "l4"))
        resources.resources.add(new Resource("www.l5.se", "/home/tech", "l5"))
        resources.resources.add(new Resource("www.l6.se", "/home/tech", "l6"))
        resources.resources.add(new Resource("www.l7.se", "/home/tech/cars", "l7"))
        resources.resources.add(new Resource("www.l8.se", "/home/java", "l8"))
        resources.resources.add(new Resource("www.l9.se", "/home/music", "l9"))

        assertEquals(3, getByCat(resources, "/home/tech").size())

    }

    List<Resource> getByCat(Resources resources, String path) {
        return resources.resources.findAll {
            it.path.startsWith(path)
        }
    }

    @Test
    public void testGenJson() throws Exception {

        User user = new User("arne")

        Cat root = new Cat("root")
        root.addLink(new Link("l1", "www.l1.se", "desc"))
        root.addLink(new Link("l2", "www.l2.se", "desc"))

        Cat subCat = new Cat("work")
        root.addCat(subCat)

        user.setRoot(root)

        ObjectMapper objectMapper = new ObjectMapper()

        String json = objectMapper.writeValueAsString(user)

        "xxx".each {
            println(it)
        }

        println "$json"
    }
}
