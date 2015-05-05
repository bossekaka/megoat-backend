package com.megoat.es

import com.fasterxml.jackson.databind.ObjectMapper
import com.megoat.domain.Cat
import com.megoat.domain.User
import com.megoat.es.domain.Link
import com.megoat.es.repository.EsRepository
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mockito
import com.megoat.es.domain.Cat as EsCat

import static org.elasticsearch.node.NodeBuilder.nodeBuilder
import static org.mockito.Mockito.when

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-28
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
class EsTest {

    @BeforeClass
    public static void initTests() throws Exception {

        EsClientCreator esClientCreator = Mockito.mock(EsClientCreator.class)

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "test").put("index.store.type", "memory").build()

        Client client = nodeBuilder().settings(settings).clusterName("test").local(true).node().client()
        when(esClientCreator.createClient()).thenReturn(client)

        // create index
//            client.admin().indices().prepareDelete("megoat").execute().actionGet()
        client.admin().indices().prepareCreate("megoat").execute().actionGet()

        // create mapping
        PutMappingResponse response = client.admin().
                indices().
                preparePutMapping("megoat").
                setType("user").
                setSource(getUserMapping()).
                execute().
                actionGet();

        com.megoat.es.domain.User user = new com.megoat.es.domain.User(name: "arne")

        EsCat home = new EsCat(name: "home", path: "/home")
        home.links.add(new Link(name: "hl1", url: "www.hl1.se", description: "desc"))
        home.links.add(new Link(name: "hl2", url: "www.hl2.se", description: "desc"))

        EsCat work = new EsCat(name: "work", path: "/work")
        work.links.add(new Link(name: "wl1", url: "www.wl1.se", description: "desc"))
        work.links.add(new Link(name: "wl2", url: "www.wl2.se", description: "desc"))

        user.cats.add(home)
        user.cats.add(work)

        ObjectMapper objectMapper = new ObjectMapper()

        String json = objectMapper.writeValueAsString(user)
        client.prepareIndex("megoat", "user").setSource(json).execute().actionGet()

        client.admin().indices().prepareFlush("megoat").execute().actionGet()

        EsClientFactory.startFactory(esClientCreator)
    }

    private static String getUserMapping() throws IOException {
        return new File(EsTest.class.getClassLoader().getResource("types/user.json").toURI()).text
    }

    @AfterClass
    public static void tearDownTests() throws Exception {

        // delete index
        EsClientFactory.client.admin().indices().prepareDelete("megoat").execute().actionGet()

        EsClientFactory.closeFactory()
    }

    @Test
    public void testGetUser() throws Exception {

        EsRepository esRepository = new EsRepository()

        User user = esRepository.getUser("arne")

        println "user: $user.name"
    }

    @Test
    public void testGetCats() throws Exception {

        EsRepository esRepository = new EsRepository()

        Cat cat = esRepository.getCat("arne", "/work")

        println "links in $cat.name: $cat.links.name"
    }
}
