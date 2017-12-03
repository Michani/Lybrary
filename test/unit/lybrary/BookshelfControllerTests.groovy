package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(BookshelfController)
@Mock([Bookshelf, BookshelfService, Book, Author])
class BookshelfControllerTests {

    void testIndex() {
        assert Bookshelf.count == 0
        new Bookshelf(name: "Shelf1").save()
        new Bookshelf(name: "Shelf2").save()
        assert Bookshelf.count == 2
        controller.index()
        assert response.redirectUrl == "/bookshelf/list"
    }

    void testIndexNoShelf() {
        assert Bookshelf.count == 0
        controller.index()
        assert response.redirectUrl == "/bookshelf/create"
    }

    void testList() {
        assert Bookshelf.count == 0
        new Bookshelf(name: "Shelf1").save()
        new Bookshelf(name: "Shelf2").save()
        assert Bookshelf.count == 2
        controller.list()
        assert view == '/bookshelf/list'
        assert model.bookshelves.size() == 2
    }

    void testCreate() {
        controller.create()
        assert view == '/bookshelf/create'
    }

    void testSave() {
        assert Bookshelf.count == 0
        params.name = 'Shelf'
        controller.save()
        assert Bookshelf.count == 1
        Bookshelf bookshelf = Bookshelf.findByName("Shelf")
        assert bookshelf != null
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testUpdate() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        params.name = 'anotherShelf'
        params.id = bookshelf.id
        controller.save()
        assert Bookshelf.count == 1
        bookshelf = Bookshelf.findByName('anotherShelf')
        assert bookshelf != null
        assert Bookshelf.findByName("Shelf") == null
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testAddBook() {
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        assert bookshelf.books == null
        controller.addBook(bookshelf.id, book.id)
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf != null
        assert bookshelf.books.size() == 1
        assert bookshelf.books.get(0).name == "Boo"
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testRemoveBook() {
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        assert bookshelf.books == null
        bookshelf.addToBooks(book)
        assert bookshelf.books.size() == 1
        controller.removeBook(bookshelf.id, 0)
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf != null
        assert bookshelf.books.size() == 0
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

    void testShow() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name:  "Shelf").save()
        assert Bookshelf.count == 1
        controller.show(bookshelf.id)
        assert view == "/bookshelf/show"
        assert model.bookshelf.id == bookshelf.id
    }

    void  testWrongShow() {
        assert Bookshelf.count == 0
        new Bookshelf(name:  "Shelf").save()
        assert Bookshelf.count == 1
        controller.show(2)
        assert response.redirectUrl == "/bookshelf/list"
    }

    void testEdit() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        controller.edit(bookshelf.id)
        assert view == "/bookshelf/edit"
        assert model.bookshelf.id == bookshelf.id
    }

    void testWrongEdit() {
        assert Bookshelf.count == 0
        new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        controller.edit(2)
        assert response.redirectUrl == "/bookshelf/list"
    }

    void testDelete() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        controller.delete(bookshelf.id)
        assert Bookshelf.count == 0
        assert Bookshelf.get(bookshelf.id) == null
        assert response.redirectUrl == "/bookshelf/index"
    }

    void testWrongDelete() {
        assert Bookshelf.count == 0
        new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        controller.delete(2)
        assert Bookshelf.count == 1
        assert response.redirectUrl == "/bookshelf/list"
    }

    void testGenerate() {
        Author author = new Author(name: "Auth").save()
        new Book(name: "Boo", author: author).save()
        assert Bookshelf.count == 0
        controller.generate()
        assert Bookshelf.count == 1
        Bookshelf bookshelf = Bookshelf.find{}
        assert bookshelf != null
        assert view == "/bookshelf/show"
        assert model.bookshelf.id == bookshelf.id
    }
}
