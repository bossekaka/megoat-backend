package com.megoat.es.repository

import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.node.Node
import static org.elasticsearch.node.NodeBuilder.nodeBuilder

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-28
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
class EsCaller {

    private static final String CLUSTER_NAME = "stellan"
    private static final String INDEX_NAME = "megoat"
    private static final String USER_TYPE_NAME = "user"

    String getUser(String uid) {

        TransportClient client = null
        try {
            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", CLUSTER_NAME).build()

            client = new TransportClient(settings)
            client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
//        Client client = new Client(client)
//
//        Node node = nodeBuilder().local(true).node()
//        Client client = node.client()

            GetResponse response = client.prepareGet(INDEX_NAME, USER_TYPE_NAME, uid).get()
            return response.sourceAsString

        } finally {
            client.close()
        }

    }
}
