package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(BookshelfController)
@Mock([Bookshelf, BookshelfService, Book, Author])
class BookshelfControllerTests {

    void testIndex() {
        new Bookshelf(name: "name 1").save()
        new Bookshelf(name: "name 2").save()
        controller.index()
        assert view == '/bookshelf/list'
        assert model.bookshelves.size() == 2
    }

    void testList() {
        controller.list()
        assert view == '/bookshelf/list'
        assert model.bookshelves.size() == 0
    }

    void testCreate() {
        controller.create()
        assert view == '/bookshelf/create'
        assert params.name == null
        assert params.books == null
    }

    void testSave() {
        assert Bookshelf.count == 0
        params.name = 'name'
        controller.save()
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
        assert Bookshelf.findByName('anotherName') == null
        Long id = Bookshelf.findByName('name').id
        assert response.redirectUrl == "/bookshelf/show/${id}"
    }

    void testSaveUpdate() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save()
        params.name = 'anotherName'
        params.id = bookshelf.id
        controller.save()
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') == null
        assert Bookshelf.findByName('anotherName') != null
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testAddBook() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save(failOnError: true)
        Author author = new Author(name: "Auth").save(failOnError: true)
        Book book = new Book(name: "Boo", author: author).save(failOnError: true)
        controller.addBook(bookshelf.id, book.id)
        assert bookshelf.books.find().name == "Boo"
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testRemoveBook() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save(failOnError: true)
        Author author = new Author(name: "Auth").save(failOnError: true)
        Book book = new Book(name: "Boo", author: author).save(failOnError: true)
        controller.addBook(bookshelf.id, book.id)
        assert bookshelf.books.size() == 1
        bookshelf.removeFromBooks(book)
        assert bookshelf.books.size() == 0
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testShow() {
        Bookshelf bookshelf = new Bookshelf(name:  "name").save(failOnError: true)
        controller.show(bookshelf.id)
        assert view == "/bookshelf/show"
    }

    void testEdit() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save(failOnError: true)
        controller.edit(bookshelf.id)
        assert view == "/bookshelf/edit"
    }

    void testDelete() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "name").save(failOnError: true)
        assert Bookshelf.count == 1
        controller.delete(bookshelf.id)
        assert Bookshelf.count == 0
        assert response.redirectUrl == "/bookshelf/list"
    }

    void testGenerate() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        Book book = new Book(name: "Boo", author: author).save(failOnError: true)
        assert Bookshelf.count == 0
        controller.generate()
        assert Bookshelf.count == 1
        assert response.redirectUrl == "/bookshelf/list"
    }
}
