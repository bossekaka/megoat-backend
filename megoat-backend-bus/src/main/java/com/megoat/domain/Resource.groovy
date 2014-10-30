package com.megoat.domain

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-27
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
class Resource {

    String path
    String name
    String description
    String url

    Resource(String url, String path, String name) {
        this.url = url
        this.path = path
        this.name = name
    }
}
