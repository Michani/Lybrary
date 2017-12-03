package lybrary

import grails.test.mixin.TestFor
import grails.test.mixin.Mock

@TestFor(Author)
class AuthorTests {

    void testCreate() {
        assert Author.count == 0
        assert Author.findByName('Auth') == null
        new Author(name: "Auth").save()
        assert Author.count == 1
        assert Author.findByName('Auth') != null
    }

    void testUpdate() {
        assert Author.count == 0
        assert Author.findByName('Auth') == null
        Author author = new Author(name: "Auth").save()
        assert Author.count == 1
        assert Author.findByName('Auth') != null
        author.name = 'anotherAuth'
        author.save()
        assert Author.findByName('Auth') == null
        assert Author.findByName('anotherAuth') != null
    }

    void testDelete() {
        assert Author.count == 0
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Author.count == 1
        author.delete()
        assert Author.count == 0
        assert Author.findByName("Auth") == null
    }
}
