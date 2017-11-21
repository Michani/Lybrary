package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(AuthorService)
@Mock([Author,Book, Bookshelf, BookService])
class AuthorServiceTests {

    void testUpdate() {
        assert Author.count == 0
        Author author = service.create(name: "Auth")
        assert Author.count == 1
        service.update(author.id, "anotherAuth")
        assert Author.findByName("Auth") == null
        assert Author.findByName("anotherAuth") != null
    }

    void testCreate() {
        assert Author.count == 0
        assert Author.findByName('Auth') == null
        service.create([name : 'Auth'])
        assert Author.count == 1
        assert Author.findByName('Auth') != null
    }

    void testDelete() {
        assert Author.count == 0
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Author.count == 1
        Book book = new Book(name: "Boo", author: author).save(failOnError: true)
        assert Author.findByName("Auth") != null
        service.delete(author.id)
        assert Author.count == 0
    }
}
