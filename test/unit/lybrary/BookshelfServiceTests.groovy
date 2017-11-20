package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(BookshelfService)
@Mock([Bookshelf,Book])
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
        assert Book.count == 0
        new Book([name: "Boo", author: "Auth"]).save()
        assert Book.count == 1
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
}
