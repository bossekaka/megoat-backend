package com.megoat.rest

import com.megoat.domain.Cat
import com.megoat.es.EsClientFactory
import com.megoat.es.EsRemoteClientCreator
import com.megoat.es.domain.Link
import com.megoat.es.repository.EsRepository

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-27
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
@WebListener
@Path("/rest")
class MegoatRest implements ServletContextListener {

    @Path("{user}/{path:.*}")
    @GET
//    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    Cat getCat(@PathParam("user") String user, @PathParam("path") String path) {

        println "getting path: $path, user: $user"

        //TODO: root does not work (not in es)
        Cat cat = new EsRepository().getCat(user, "/" + path)

        return cat
    }

    @Path("{user}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Link> search(@PathParam("user") String user, @QueryParam("q") String q) {

        List<Link> links = new EsRepository().searchLinks(user, q)

        return links
    }

    void contextInitialized(ServletContextEvent servletContextEvent) {
        println "*** starting factory"
        EsClientFactory.startFactory(new EsRemoteClientCreator("stellan", "localhost"))
    }

    void contextDestroyed(ServletContextEvent servletContextEvent) {
        println "*** stopping factory"
        EsClientFactory.closeFactory()
    }
}
