package com.megoat.es.repository

import com.megoat.es.domain.Cat
import com.megoat.es.domain.Link
import com.megoat.es.domain.User
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-29
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
class EsRepositoryTest {

    EsRepository repo = new EsRepository()

    @Test
    public void testGetParentPath() throws Exception {

        assertEquals("/", repo.getParentPath("/arne"))
        assertEquals(null, repo.getParentPath("/"))
        assertEquals("/arne", repo.getParentPath("/arne/anka"))
        assertEquals("/x/y", repo.getParentPath("/x/y/z"))
        assertEquals("/", repo.getParentPath("/arne/"))
    }

    @Test
    public void testBuilder() throws Exception {

        ObjectGraphBuilder builder = new ObjectGraphBuilder()
        builder.classNameResolver = "com.megoat.es.domain"

        Cat cat = builder.cat(name: "arne") {
            3.times {
                link(name: "link#$it", url: "www.l${it}.se")
            }
        }

        assertEquals("arne", cat.name)
        assertEquals(3, cat.links.size())

    }

    @Test
    public void testExtractMatchingLinks() throws Exception {

        List<Link> links = repo.extractMatchingLinks(getCats(), "x").unique()

        println "found links: $links.name"

    }

    @Test
    public void testCreateCat() throws Exception {

        User user = new User(name: "arne")
        user.cats = getCats()

        Cat cat = repo.createCat(user, "/x/stefan/kaka")
        assertEquals("kaka", cat.name)
        assertEquals("/x/stefan/kaka", cat.path)
    }

    @Test
    public void testAddChildren() throws Exception {

        List<Cat> cats = getCats()

        println "$cats.links.name"

        com.megoat.domain.Cat domainRoot = repo.buildCatTree("/x/y/z", cats)
        assertEquals("z", domainRoot.name)
        assertEquals(3, domainRoot.links.size())
        assertEquals(2, domainRoot.cats.size())

        domainRoot = repo.buildCatTree("/", cats)
        assertEquals("root", domainRoot.name)
        assertEquals(0, domainRoot.links.size())
        assertEquals(1, domainRoot.cats.size())
    }

    private List<Cat> getCats() {

        //TODO: builder?
        Cat root = new Cat(
                path: "/x/y/z",
                links: [
                        new Link(name: "l1", url: "www.l1.se"),
                        new Link(name: "l2", url: "www.l2.se"),
                        new Link(name: "l3", url: "www.l3.se")
                ],
                name: "z"
        )

        List<Cat> cats = []

        cats.add(root)

        cats.add(new Cat(
                path: "/x/y/z/xxx",
                links: [
                        new Link(name: "xxx1", url: "www.xxx1.se"),
                        new Link(name: "xxx2", url: "www.xxx2.se"),
                        new Link(name: "xxx3", url: "www.xxx3.se")
                ],
                name: "xxx"
        ))

        cats.add(new Cat(
                path: "/x/y/z/yyy",
                links: [
                        new Link(name: "yyy1", url: "www.yyy1.se"),
                        new Link(name: "yyy2", url: "www.yyy2.se"),
                        new Link(name: "yyy3", url: "www.yyy3.se")
                ],
                name: "z"
        ))

        cats.add(new Cat(
                path: "/x",
                name: "x"
        ))

        cats.add(new Cat(
                path: "/",
                name: "root"
        ))

        cats.add(new Cat(
                path: "/x/y",
                name: "y"
        ))

        return cats
    }
}
