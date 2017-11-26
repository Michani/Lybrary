package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(BookService)
@Mock([Bookshelf,Book,Author])
class BookServiceTests {

    void testUpdate() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Book.count == 0
        Book book = service.create([name: 'name', author: author])
        assert Book.count == 1
        service.update(book.id,"anotherName")
        assert Book.findByName('anotherName') != null
        assert Book.findByName('name') == null
        assert Book.findByAuthor(author) != null
    }

    void testCreate() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Book.count == 0
        assert Book.findByName('name') == null
        assert Book.findByAuthor(author) == null
        service.create([name : 'name', author: author])
        assert Book.count == 1
        assert Book.findByName('name') != null
        assert Book.findByAuthor(author) != null
    }

    void testDelete() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Book.count == 0
        Book book = service.create([name : 'name', author: author])
        assert Book.count == 1
        assert Book.findByName('name') != null
        service.delete(book.id)
        assert Book.count == 0
        assert Book.findByName('name') == null
    }
}
