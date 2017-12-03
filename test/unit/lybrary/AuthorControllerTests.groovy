package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(AuthorController)
@Mock([Book, AuthorService, Author])
class AuthorControllerTests {

    void testIndex() {
        new Author(name: "Auth").save()
        assert Author.count == 1
        controller.index()
        assert response.redirectUrl == '/author/list'
    }

    void testIndexNoAuth() {
        controller.index()
        assert response.redirectUrl == '/author/create'
    }

    void testList() {
        new Author(name: "Auth1").save()
        new Author(name: "Auth2").save()
        controller.list()
        assert view == '/author/list'
        assert model.authors.size() == 2
    }

    void testCreate() {
        controller.create()
        assert view == '/author/create'
    }

    void testSave() {
        assert Author.count == 0
        params.name = 'Auth'
        controller.save()
        assert Author.count == 1
        assert Author.findByName('Auth') != null
        Long id = Author.findByName('Auth').id
        assert response.redirectUrl == "/author/show/${id}"
    }

    void testUpdate() {
        Author author = new Author(name: "Auth").save()
        assert Author.count == 1
        params.name = 'anotherAuth'
        params.id = author.id
        controller.save()
        author = Author.get(author.id)
        assert author != null
        assert author.name == 'anotherAuth'
        assert response.redirectUrl == "/author/show/${author.id}"
    }

    void testShow() {
        Author author = new Author(name: "Auth").save()
        controller.show(author.id)
        author = Author.get(author.id)
        assert view == "/author/show"
        assert model.author.id == author.id
    }

    void testWrongShow() {
        assert Author.count == 0
        new Author(name: "Auth").save()
        assert Author.count == 1
        controller.show(2)
        assert response.redirectUrl == '/author/list'
    }

    void testEdit() {
        Author author = new Author(name: "Auth").save()
        controller.edit(author.id)
        assert view == "/author/edit"
        assert model.author.id == author.id
    }

    void testWrongEdit() {
        assert Author.count == 0
        new Author(name: "Auth").save()
        assert Author.count == 1
        controller.edit(2)
        assert response.redirectUrl == '/author/list'
    }

    void testDelete() {
        assert Author.count == 0
        Author author = new Author(name: "Auth").save()
        assert Author.count == 1
        controller.delete(author.id)
        assert Author.count == 0
        assert response.redirectUrl == "/author/index"
    }

    void testWrongDelete() {
        assert Author.count == 0
        new Author(name: "Auth").save()
        assert Author.count == 1
        controller.delete(2)
        assert response.redirectUrl == '/author/list'
    }
}
