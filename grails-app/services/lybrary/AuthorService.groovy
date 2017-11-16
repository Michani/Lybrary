package lybrary

import org.codehaus.groovy.grails.plugins.InvalidVersionException
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class AuthorService {

    def update(Long id, String name) {
        Author author = Author.get(id)
        if (author) {
            author.name = name
            return author.save()
        }
        throw new Exception("No such author")
    }

    def create(params) {
        return new Author(params).save()
    }

    def delete(Long id){
        def author = Author.get(id)
        if (author) {
            return author.delete()
        }
        throw new Exception("No such author")
    }
}
