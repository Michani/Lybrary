package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor


@TestFor(BookshelfService)
@Mock(Bookshelf)
class BookshelfServiceTests {

    void testCreate() {
        assert Bookshelf.count == 0
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
