package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(BookService)
@Mock([Bookshelf,Book,Author])
class BookServiceTests {

    void testUpdate() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        service.update(book.id, "anotherBoo")
        assert Book.count == 1
        book = Book.get(book.id)
        assert book != null
        assert book.name == "anotherBoo"
        assert book.author == author
    }

    void testWrongUpdate() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        new Book(name: 'Boo', author: author).save()
        assert Book.count == 1
        assert Book.findByName("Boo") != null
        try {
            service.update(2, "anotherBoo")
        }catch (Exception e){
            assert e.message == "No such book"
        }
        assert Book.findByName("Boo") != null
    }

    void testCreate() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        service.create([name: 'Boo', author: author])
        assert Book.count == 1
        assert Book.findByName('Boo') != null
    }

    void testDelete() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        Book book = service.create([name : 'Boo', author: author])
        assert Book.count == 1
        assert Book.findByName('Boo') != null
        service.delete(book.id)
        assert Book.count == 0
        assert Book.get(book.id) == null
    }

    void testWrongDelete() {
        assert Book.count == 0
        Author author = new Author(name: "Auth").save()
        new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        try {
            service.delete(2)
        }catch (Exception e){
            assert e.message == "No such book"
        }
    }
}
