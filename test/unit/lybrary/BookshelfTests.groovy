package lybrary

import grails.test.mixin.TestFor

@TestFor(Bookshelf)
class BookshelfTests {

    void testCreate() {
        assert Bookshelf.count == 0
        assert Bookshelf.findByName('name') == null
        new Bookshelf(name: "name").save()
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
    }

    void testUpdate() {
        assert Bookshelf.count == 0
        assert Bookshelf.findByName('name') == null
        Bookshelf bookshelf = new Bookshelf(name: "name").save()
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
        bookshelf.name = 'anotherName'
        bookshelf.save()
        assert Bookshelf.findByName('name') == null
        assert Bookshelf.findByName('anotherName') != null
    }
}
