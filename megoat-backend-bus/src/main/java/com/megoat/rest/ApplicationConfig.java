package com.megoat.rest;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


import org.glassfish.jersey.server.ResourceConfig;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-09-12
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages("com.megoat.rest");
    }
}

