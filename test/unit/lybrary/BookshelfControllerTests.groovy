package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(BookshelfController)
@Mock([Bookshelf, BookshelfService])
class BookshelfControllerTests {

    void testIndex() {
        new Bookshelf(name: "name 1").save()
        new Bookshelf(name: "name 2").save()
        controller.index()
        assert view == '/bookshelf/list'
        assert model.bookshelves.size() == 2
    }

    void testSave() {
        assert Bookshelf.count == 0
        params.name = 'name'
        controller.save()
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') != null
        assert Bookshelf.findByName('anotherName') == null
        Long id = Bookshelf.findByName('name').id
        assert response.redirectUrl == "/bookshelf/show/${id}"
    }

    void testSaveUpdate() {
        Bookshelf bookshelf = new Bookshelf(name: "name").save()
        params.name = 'anotherName'
        params.id = bookshelf.id
        controller.save()
        assert Bookshelf.count == 1
        assert Bookshelf.findByName('name') == null
        assert Bookshelf.findByName('anotherName') != null
        assert response.redirectUrl == "/bookshelf/show/${bookshelf.id}"
    }

}
