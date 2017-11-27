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
        Bookshelf bookshelf = new Bookshelf(name: "name").save()
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        assert bookshelf.books == null
        controller.addBook(bookshelf.id, book.id)
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf.books.size() == 1
        assert bookshelf.books.get(0).name == "Boo"
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testRemoveBook() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save()
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        controller.addBook(bookshelf.id, book.id)
        assert bookshelf.books.size() == 1
        controller.removeBook(bookshelf.id, 0)
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf.books.size() == 0
        assert view == "/bookshelf/show/${bookshelf.id}"
    }

    void testShow() {
        Bookshelf bookshelf = new Bookshelf(name:  "name").save()
        if(!bookshelf)
            assert response.redirectUrl == "/bookshelf/list"
        else{
            controller.show(bookshelf.id)
            assert view == "/bookshelf/show"
            assert model.bookshelf == bookshelf
        }
    }

    void testEdit() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save()
        if(!bookshelf)
            assert response.redirectUrl == "/bookshelf/list"
        else{
            controller.edit(bookshelf.id)
            assert view == "/bookshelf/edit"
            assert model.bookshelf == bookshelf
        }
    }

    void testDelete() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "name").save(failOnError: true)
        assert Bookshelf.count == 1
        controller.delete(bookshelf.id)
        assert Bookshelf.count == 0
        assert view == "/bookshelf/list"
    }

    void testGenerate() {
        Author author = new Author(name: "Auth").save()
        new Book(name: "Boo", author: author).save()
        assert Bookshelf.count == 0
        controller.generate()
        assert Bookshelf.count == 1
        Bookshelf bookshelf = Bookshelf.find{}
        assert view == "/bookshelf/show"
        assert model.bookshelf == bookshelf
    }
}
