package com.megoat.es.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.megoat.domain.Cat
import com.megoat.domain.Link
import com.megoat.domain.LinkRequest
import com.megoat.domain.User
import com.megoat.es.EsClientFactory
import com.megoat.es.domain.Cat as EsCat
import com.megoat.es.domain.Link as EsLink
import com.megoat.es.domain.User as EsUser
import org.elasticsearch.action.get.GetResponse

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-28
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
class EsRepository {

    User addLink(String userName, LinkRequest linkRequest) {

        EsUser esUser = getEsUser(userName)

        EsCat cat = esUser.getCatByPath(linkRequest.path)
        if (cat == null) {
            cat = createCat(esUser, linkRequest.path)
        }
        cat.links.add(new EsLink(name: linkRequest.name, url: linkRequest.url, description: linkRequest.description))

        return toDomainUser(esUser)
    }

    EsCat createCat(EsUser user, String path) {

        String currPath = ""
        path.replaceFirst("/", "").split("/").each {
            currPath += "/$it"
            EsCat cat = user.getCatByPath(currPath)
            if (cat == null) {
                user.cats.add(new EsCat(path: currPath, name: it))
            }
        }

        return user.getCatByPath(path)
    }

    User getUser(String userName) {
        return toDomainUser(getEsUser(userName))
    }

    List<EsLink> searchLinks(String userName, String query) {
        EsUser esUser = getEsUser(userName)
        return extractMatchingLinks(esUser.cats, query)
    }

    List<EsLink> extractMatchingLinks(List<EsCat> cats, String query) {

        List<EsLink> links = cats.links.flatten().findAll {
            link -> link.name.startsWith(query)
        }

        links += cats.findAll {
            cat -> cat.path.contains("/$query")
        }.links.flatten()

        return links.unique()
    }

    EsUser getEsUser(String userName) {

        GetResponse response = EsClientFactory.client.prepareGet("megoat", "user", userName).get()

        String userJson = response.sourceAsString

        ObjectMapper objectMapper = new ObjectMapper()

        EsUser esUser = objectMapper.readValue(userJson, EsUser.class)

        return esUser
    }

    User toDomainUser(EsUser source) {

        if (source == null) {
            return null
        }

        User target = new User(source.name)
        target.root = buildCatTree("/", source.cats)

        return target
    }

    Cat buildCatTree(String rootPath, ArrayList<EsCat> cats) {

        Map<String, Cat> catMap = [:]
        for (EsCat cat in cats) {
            catMap[cat.path] = toDomainCat(cat)
        }

        for (def catEntry in catMap.entrySet()) {

            if (!catEntry.key.startsWith(rootPath) || catEntry.key == rootPath) {
                continue
            }

            String parentPath = getParentPath(catEntry.key)
            Cat parent = catMap[(parentPath)]

            if (parent != null) {
                parent.addCat(catEntry.value)
            } else {
                println "no cat for root: $parentPath"
            }
        }

        return catMap[(rootPath)]
    }

    String getParentPath(String path) {

        if ("/" == path) {
            return null
        }

        List<String> catNames = path.replaceFirst("/", "").split("/").toList()
        if (catNames.isEmpty()) {
            return null
        }

        catNames.pop()
        return "/" + catNames.join("/")
    }

    Cat toDomainCat(EsCat source) {

        Cat target = new Cat(source.name)
        for (link in source.links) {
            target.addLink(toDomainLink(link))
        }

        return target
    }

    Link toDomainLink(EsLink source) {
        Link target = new Link(source.name, source.url)
        target.description = source.description
        return target
    }

    Cat getCat(String userName, String path) {

        EsUser user = getEsUser(userName)

        List<EsCat> cats = user.cats.findAll({
            it.path.startsWith(path)
        })

        return buildCatTree(path, cats)
    }
}
