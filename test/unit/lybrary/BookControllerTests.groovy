package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(BookController)
@Mock([Book, BookService, Author, Bookshelf])
class BookControllerTests {

    void testIndex() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        new Book(name: "Boo1", author: author).save(failOnError: true)
        new Book(name: "Boo2", author: author).save(failOnError: true)
        controller.index()
        assert response.redirectUrl == '/book/list'
        assert Book.count == 2
    }

    void testList() {
        controller.list()
        assert view == '/book/list'
        assert Book.count == 0
    }

    void testCreate() {
        controller.create()
        assert view == '/book/create'
        assert params.name == null
    }

    void testSave() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Book.count == 0
        params.name = 'name'
        params.author = author
        controller.save()
        assert Book.count == 1
        assert Book.findByName('name') != null
        assert Book.findByName('anotherName') == null
        assert Book.findByAuthor(author) != null
        Long id = Book.findByName('name').id
        assert response.redirectUrl == "/book/show/${id}"
    }

    void testSaveUpdate() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        Book book = new Book(name: "name", author: author).save(failOnError: true)
        params.name = 'anotherName'
        params.id = book.id
        controller.save()
        assert Book.count == 1
        assert Book.findByName('name') == null
        assert Book.findByName('anotherName') != null
        assert Book.findByAuthor(author) != null
        assert response.redirectUrl == "/book/show/${book.id}"
    }

    void testShow() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        Book book = new Book(name:  "name", author: author).save(failOnError: true)
        controller.show(book.id)
        assert view == "/book/show"
    }

    void testEdit() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        Book book = new Book(name:  "name", author: author).save(failOnError: true)
        controller.show(book.id)
        assert view == "/book/show"
    }

    void testDelete() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Book.count == 0
        Book book = new Book(name: "name", author: author).save(failOnError: true)
        assert Book.count == 1
        controller.delete(book.id)
        assert Book.count == 0
        assert response.redirectUrl == "/book/list"
    }
}
