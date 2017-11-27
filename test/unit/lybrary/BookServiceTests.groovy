package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(BookService)
@Mock([Bookshelf,Book,Author])
class BookServiceTests {

    void testUpdate() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        Book book = service.create([name: 'Boo', author: author])
        assert Book.count == 1
        book = Book.get(book.id)
        try {
            service.update(book.id, "anotherBoo")
            assert Book.findByName('anotherBoo') != null
            assert Book.findByName('Boo') == null
            assert Book.findByAuthor(author) != null
        }catch(Exception e) {
            assert e.toString() == "No such book"
        }
    }

    void testCreate() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        assert Book.findByName('Boo') == null
        assert Book.findByAuthor(author) == null
        service.create([name: 'Boo', author: author])
        assert Book.count == 1
        assert Book.findByName('Boo') != null
        assert Book.findByAuthor(author) != null
    }

    void testDelete() {
        Author author = new Author(name: "Auth").save()
        assert Book.count == 0
        Book book = service.create([name : 'Boo', author: author])
        assert Book.count == 1
        assert Book.findByName('Boo') != null
        try{
            service.delete(book.id)
            assert Book.count == 0
            assert Book.findByName('Boo') == null
        }catch (Exception e){
            assert e.toString() == "No such book"
        }
    }
}
