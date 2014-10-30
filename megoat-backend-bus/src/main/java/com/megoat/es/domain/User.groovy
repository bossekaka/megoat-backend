package com.megoat.es.domain

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-28
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
class User {

    String name
    List<Cat> cats = []

    Cat getCatByPath(String path) {
        return cats.find({
            it.path == path
        })
    }
}
