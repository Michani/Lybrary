package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(BookshelfService)
@Mock([Bookshelf,Book,Author])
class BookshelfServiceTests {

    void testUpdate() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = service.create([name: 'name'])
        assert Bookshelf.count == 1
        bookshelf.name='anotherName'
        assert Bookshelf.findByName('anotherName') == null
        assert Bookshelf.findByName('name') != null
    }

    void testAddBook() {
        assert Bookshelf.count == 0
        assert Bookshelf.findByName('name') == null
        Bookshelf bookshelf = service.create(name : 'name')
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
        assert Author.count == 0
        new Author([name: "Auth"]).save(failOnError: true)
        assert Author.count == 1
        assert Book.count == 0
        new Book([name: "Boo", author: Author.findByName("Auth")]).save(failOnError: true)
        assert Book.count == 1
        assert bookshelf.books == null
        service.addBook(bookshelf.id,Book.findByName("Boo").id)
        assert bookshelf.books != null
    }

    void testRemoveBook() {
        Bookshelf bookshelf = service.create(name: 'name')
        new Author([name: "Auth"]).save(failOnError: true)
        new Book([name: "Boo", author: Author.findByName("Auth")]).save(failOnError: true)
        assert bookshelf.books == null
        service.addBook(bookshelf.id, Book.findByName("Boo").id)
        assert bookshelf.books != null
        bookshelf.removeFromBooks(Book.findByName("Boo"))
        assert bookshelf.books.size() == 0
    }

    void testCreate() {
        assert Bookshelf.count == 0
        assert Bookshelf.findByName('name') == null
        service.create([name : 'name'])
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
    }

    void testRemove() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = service.create([name : 'name'])
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
        service.remove(bookshelf.id)
        assert Bookshelf.count == 0
    }

    void testGenerate() {
        assert Bookshelf.count == 0
        new Author([name: "Auth"]).save(failOnError: true)
        new Book([name: "Boo", author: Author.findByName("Auth")]).save(failOnError: true)
        service.generate()
        assert Bookshelf.count == 1
        assert Bookshelf.findAll().books.size() != 0
    }
}
