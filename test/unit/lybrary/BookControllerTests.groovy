package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(BookController)
@Mock([Book, BookService, Author, Bookshelf])
class BookControllerTests {

    void testIndex() {
        if(Book.count == 0) response.redirectUrl == '/book/create'
        else response.redirectUrl == '/book/list'
    }

    void testList() {
        controller.list()
        assert view == '/book/list'
        assert model.authors.size() == 0
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
        assert Book.findByName('Boo') != null
        assert Book.findByName('anotherBoo') == null
        assert Book.findByAuthor(author) != null
        Long id = Book.findByName('Boo').id
        assert response.redirectUrl == "/book/show/${id}"
    }

    void testUpdate() {
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        book = Book.get(book.id)
        params.name = 'anotherBoo'
        params.id = book.id
        controller.save()
        assert Book.findByName('Boo') == null
        assert Book.findByName('anotherBoo') != null
        assert Book.findByAuthor(author) != null
        assert response.redirectUrl == "/book/show/${book.id}"
    }

    void testShow() {
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name:  "Boo", author: author).save()
        controller.show(book.id)
        if(!book) assert response.redirectUrl == '/book/list'
        else{
            book = Book.get(book.id)
            assert view == "/book/show"
            assert model.book == book
        }

    }

    void testEdit() {
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name:  "Boo", author: author).save()
        controller.edit(book.id)
        if(!book) assert response.redirectUrl == '/book/list'
        else{
            book = Book.get(book.id)
            assert view == "/book/edit"
            assert model.book == book
        }
    }

    void testDelete() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        controller.delete(book.id)
        assert Book.count == 0
        assert response.redirectUrl == "/book/list"
    }
}
