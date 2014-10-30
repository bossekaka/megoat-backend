package com.megoat.domain

import javax.xml.bind.annotation.XmlRootElement

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 2014-10-27
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
class Cat {

    String name
    List<Cat> cats = []
    List<Link> links = []

    Cat() {
    }

    Cat(name) {
        this.name = name
    }

    def addCat(Cat cat) {
        cats.add(cat)
    }

    def addLink(Link link) {
        links.add(link)
    }
}
