package com.megoat.es.domain

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-28
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
class Link {

    String name
    String description
    String url

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Link link = (Link) o

        if (name != link.name) return false
        if (url != link.url) return false

        return true
    }

    int hashCode() {
        int result
        result = name.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}
