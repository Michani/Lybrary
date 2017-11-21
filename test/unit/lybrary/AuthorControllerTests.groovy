package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(AuthorController)
@Mock([Book, AuthorService, Author])
class AuthorControllerTests {

    void testIndex() {
        new Author(name: "Auth1").save(failOnError: true)
        new Author(name: "Auth2").save(failOnError: true)
        controller.index()
        assert response.redirectUrl == '/author/list'
        assert Author.count == 2
    }

    void testList() {
        controller.list()
        assert view == '/author/list'
        assert Author.count == 0
    }

    void testCreate() {
        controller.create()
        assert view == '/author/create'
        assert params.name == null
    }

    void testSave() {
        assert Author.count == 0
        params.name = 'Auth'
        controller.save()
        assert Author.count == 1
        assert Author.findByName('Auth') != null
        assert Author.findByName('anotherAuth') == null
        Long id = Author.findByName('Auth').id
        assert response.redirectUrl == "/author/show/${id}"
    }

    void testSaveUpdate() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        params.name = 'anotherAuth'
        params.id = author.id
        controller.save()
        assert Author.count == 1
        assert Author.findByName('Auth') == null
        assert Author.findByName('anotherAuth') != null
        assert response.redirectUrl == "/author/show/${author.id}"
    }

    void testShow() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        controller.show(author.id)
        assert view == "/author/show"
    }

    void testEdit() {
        Author author = new Author(name: "Auth").save(failOnError: true)
        controller.edit(author.id)
        assert view == "/author/edit"
    }

    void testDelete() {
        assert Author.count == 0
        Author author = new Author(name: "Auth").save(failOnError: true)
        assert Author.count == 1
        controller.delete(author.id)
        assert Author.count == 0
        assert response.redirectUrl == "/author/list"
    }
}
