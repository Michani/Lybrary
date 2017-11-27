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
        bookshelf = Bookshelf.get(bookshelf.id)
        service.update(bookshelf.id,'anotherName')
        assert Bookshelf.findByName('anotherName') != null
        assert Bookshelf.findByName('name') == null
    }

    void testAddBook() {
        assert Bookshelf.count == 0
        assert Bookshelf.findByName('name') == null
        Bookshelf bookshelf = service.create(name : 'name')
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
        assert Author.count == 0
        Author author = new Author([name: "Auth"]).save()
        assert Author.count == 1
        assert Book.count == 0
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf.books == null
        service.addBook(bookshelf.id,book.id)
        assert bookshelf.books.size() == 1
        assert bookshelf.books.find().name == "Boo"
        assert bookshelf.books.find().author == author
        assert bookshelf.books.find().author.name == "Auth"
    }

    void testRemoveBook() {
        Bookshelf bookshelf = service.create(name: 'name')
        Author author = new Author([name: "Auth"]).save()
        Book book = new Book([name: "Boo", author: author]).save()
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf.books == null
        service.addBook(bookshelf.id, book.id)
        assert bookshelf.books.size() == 1
        assert bookshelf.books.indexOf(book) == 0
        service.removeBook(bookshelf.id,bookshelf.books.indexOf(book))
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
        bookshelf = Bookshelf.get(bookshelf.id)
        service.remove(bookshelf.id)
        assert Bookshelf.count == 0
    }

    void testGenerate() {
        assert Bookshelf.count == 0
        Author author = new Author([name: "Auth"]).save()
        new Book([name: "Boo", author: author]).save()
        service.generate()
        assert Bookshelf.count == 1
        assert Bookshelf.findAll().each {
            if(it.books.size()!= 0) it.books.name == "Boo"
        }
    }
}
