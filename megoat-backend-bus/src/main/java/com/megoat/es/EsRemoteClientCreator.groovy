package com.megoat.es

import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-29
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
class EsRemoteClientCreator implements EsClientCreator {

    final String clusterName
    final String host

    EsRemoteClientCreator(String clusterName, String host) {
        this.clusterName = clusterName
        this.host = host
    }

    Client createClient() {

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build()

        Client client = new TransportClient(settings)
        client.addTransportAddress(new InetSocketTransportAddress(host, 9300))

        return client
    }
}
