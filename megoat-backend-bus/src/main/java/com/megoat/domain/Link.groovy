package com.megoat.domain;

/**
 * Created with IntelliJ IDEA.
 * User: bo kakax
 * Date: 2014-10-27
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
class Link {

    String name;
    String description
    String url;

    Link(name, url) {
        this.name = name
        this.url = url
    }

    Link(String name, String description, String url) {
        this.name = name
        this.description = description
        this.url = url
    }
}
