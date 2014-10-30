package com.megoat.es

import org.elasticsearch.client.Client

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-29
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
class EsClientFactory {

    static Client client

    def static startFactory(EsClientCreator esClientCreator) {
        client = esClientCreator.createClient()
    }

    def static closeFactory() {
        client.close()
    }

    static Client getClient() {
        return client
    }
}
