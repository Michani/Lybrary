package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(BookshelfService)
@Mock([Bookshelf,Book,Author])
class BookshelfServiceTests {

    void testUpdate() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        service.update(bookshelf.id,'anotherShelf')
        assert Bookshelf.count == 1
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf.name == 'anotherShelf'
        assert Bookshelf.findByName('Shelf') == null
    }

    void testWrongUpdate() {
        assert Bookshelf.count == 0
        new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        try {
            service.update(2,"anotherShelf")
        }catch (Exception e) {
            assert e.message == "No such bookshelf"
        }
        assert Bookshelf.count == 1
        assert Bookshelf.findByName("Shelf") != null
    }

    void testAddBook() {
        Author author = new Author([name: "Auth"]).save()
        Book book = new Book(name: "Boo", author: author).save()
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name : 'Shelf').save()
        assert Bookshelf.count == 1
        assert bookshelf.books == null
        service.addBook(bookshelf.id,book.id)
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf != null
        assert bookshelf.books.size() == 1
    }

    void testAddNoBook() {
        assert Bookshelf.count == 0
        Author author = new Author([name: "Auth"]).save()
        new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        try {
            service.addBook(bookshelf.id,20)
        }catch (Exception e){
            assert e.message == "No such book"
        }
    }

    void testAddNoBookshelf() {
        assert Bookshelf.count == 0
        Author author = new Author([name: "Auth"]).save()
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        try {
            service.addBook(20,book.id)
        }catch (Exception e){
            assert e.message == "No such bookshelf"
        }
    }

    void testWrongAddBook() {
        assert Bookshelf.count == 0
        Author author = new Author([name: "Auth"]).save()
        new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        try {
            service.addBook(5,6)
        }catch (Exception e){
            assert e.message == "Wrong parameters"
        }
    }

    void testRemoveBook() {
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        assert bookshelf.books == null
        bookshelf.addToBooks(book)
        assert bookshelf.books.size() == 1
        assert bookshelf.books.indexOf(book) == 0
        service.removeBook(bookshelf.id,0)
        bookshelf = Bookshelf.get(bookshelf.id)
        assert  bookshelf != null
        assert bookshelf.books.size() == 0
    }

    void testRemoveNoBookshelf() {
        assert Bookshelf.count == 0
        Author author = new Author([name: "Auth"]).save()
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        bookshelf.addToBooks(book)
        assert Bookshelf.count == 1
        try {
            service.removeBook(1,0)
        }catch (Exception e){
            assert e.message == "No such bookshelf"
        }
    }

    void testCreate() {
        assert Bookshelf.count == 0
        assert Bookshelf.findByName('Shelf') == null
        service.create([name : 'Shelf'])
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('Shelf') != null
    }

    void testRemove() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        service.remove(bookshelf.id)
        assert Bookshelf.count == 0
        bookshelf = Bookshelf.get(bookshelf.id)
        assert bookshelf == null
    }

    void testWrongRemove() {
        assert Bookshelf.count == 0
        new Bookshelf(name: "Shelf").save()
        assert Bookshelf.count == 1
        try {
            service.remove(2)
        }catch (Exception e){
            assert e.message == "No such bookshelf"
        }
        assert Bookshelf.count == 1
    }

    void testGenerate() {
        assert Bookshelf.count == 0
        Author author = new Author([name: "Auth"]).save()
        new Book([name: "Boo", author: author]).save()
        service.generate()
        assert Bookshelf.count == 1
        Bookshelf bookshelf = Bookshelf.get(1)
        assert bookshelf != null
        bookshelf.books.each {
            assert it.name == "Boo"
        }
    }
}
