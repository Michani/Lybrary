package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(BookController)
@Mock([Book, BookService, Author, Bookshelf])
class BookControllerTests {

    void testIndex() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        controller.index()
        assert response.redirectUrl == '/book/list'
    }

    void testIndexNoBook() {
        assert Book.count == 0
        controller.index()
        assert response.redirectUrl == '/book/create'
    }

    void testList() {
        Author author = new Author(name: "Auth").save()
        new Book(name: "Boo1", author: author).save()
        new Book(name: "Boo2", author: author).save()
        assert Book.count == 2
        controller.list()
        assert view == '/book/list'
        assert model.books.size() == 2
    }

    void testCreate() {
        controller.create()
        assert view == '/book/create'
    }

    void testSave() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        params.name = 'Boo'
        params.author = author
        controller.save()
        assert Book.count == 1
        Book book = Book.findByName("Boo")
        assert book != null
        assert book.author == author
        assert response.redirectUrl == "/book/show/${book.id}"
    }

    void testUpdate() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        params.name = 'anotherBoo'
        params.id = book.id
        controller.save()
        assert Book.count == 1
        book = Book.get(book.id)
        assert book.name != 'Boo'
        assert book.name == 'anotherBoo'
        assert book.author == author
        assert response.redirectUrl == "/book/show/${book.id}"
    }

    void testShow() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name:  "Boo", author: author).save()
        assert Book.count == 1
        controller.show(book.id)
        assert view == "/book/show"
        assert model.book.id == book.id
    }

    void testWrongShow(){
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        new Book(name:  "Boo", author: author).save()
        assert Book.count == 1
        controller.show(2)
        assert response.redirectUrl == '/book/list'
    }

    void testEdit() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name:  "Boo", author: author).save()
        assert Book.count == 1
        controller.edit(book.id)
        assert view == "/book/edit"
        assert model.book.id == book.id
    }

    void testWrongEdit() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        new Book(name:  "Boo", author: author).save()
        assert Book.count == 1
        controller.edit(2)
        assert response.redirectUrl == '/book/list'
    }

    void testDelete() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        controller.delete(book.id)
        assert Book.count == 0
        assert response.redirectUrl == "/book/index"
    }

    void testWrongDelete() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        controller.delete(2)
        assert response.redirectUrl == "/book/list"
    }
}
