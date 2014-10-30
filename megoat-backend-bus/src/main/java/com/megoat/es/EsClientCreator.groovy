package com.megoat.es

import org.elasticsearch.client.Client

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-29
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public interface EsClientCreator {
    Client createClient()
}
