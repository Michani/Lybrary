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

    void testDelete() {
        assert Bookshelf.count == 0
        Bookshelf bookshelf = new Bookshelf(name: "name").save(failOnError: true)
        assert Bookshelf.count == 1
        bookshelf.delete()
        assert Bookshelf.count == 0
        assert Bookshelf.findByName("name") == null
    }
}
