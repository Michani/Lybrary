package lybrary

import grails.test.mixin.TestFor
import grails.test.mixin.Mock

@TestFor(Book)
@Mock(Author)
class BookTests {

    void testCreate() {
        assert Book.count == 0
        assert Book.findByName('Boo') == null
        Author author = new Author(name: "Auth")
        new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        assert Book.findByName('Boo') != null
    }

    void testWrongCreate() {
        assert Book.count == 0
        new Book(name: "Boo").save()
        assert Book.count == 0
    }

    void testUpdate() {
        assert Book.count == 0
        assert Book.findByName('Boo') == null
        Author author = new Author(name: "Auth")
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        assert Book.findByName('Boo') != null
        book.name = 'anotherBoo'
        book.save()
        assert Book.count == 1
        assert Book.findByName('Boo') == null
        assert Book.findByName('anotherBoo') != null
    }

    void testDelete() {
        assert Book.count == 0
        Author author = new Author(name: "Auth")
        Book book = new Book(name: "Boo", author: author).save()
        assert Book.count == 1
        book.delete()
        assert Book.count == 0
        assert Book.findByName("Boo") == null
    }
}
