package lybrary

import org.codehaus.groovy.grails.plugins.InvalidVersionException

class AuthorService {

    def save(params) {
        def a = new Author(params)
        return a.save(flush: false)
    }

    def update(a, params) {
        if (!a) throw new Exception()
        if (a.version != null) {

            if (a.version > version) {
                throw new InvalidVersionException()
            }
        }

        a.properties = params

        if (!a.save(flush: true)) {
            throw new NotActiveException()
        }
    }
}
