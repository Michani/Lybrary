package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(AuthorController)
@Mock([Book, AuthorService, Author])
class AuthorControllerTests {

    void testIndex() {
        if(Author.count == 0) response.redirectUrl == '/author/create'
        else response.redirectUrl == '/author/list'
    }

    void testList() {
        controller.list()
        assert view == '/author/list'
        assert model.authors.size() == 0
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
        assert Author.findByName('anotherAuth') == null
        Long id = Author.findByName('Auth').id
        assert response.redirectUrl == "/author/show/${id}"
    }

    void testUpdate() {
        Author author = new Author(name: "Auth").save()
        assert Author.count == 1
        author = Author.get(author.id)
        params.name = 'anotherAuth'
        params.id = author.id
        controller.save()
        assert Author.findByName('Auth') == null
        assert Author.findByName('anotherAuth') != null
        assert response.redirectUrl == "/author/show/${author.id}"
    }

    void testShow() {
        Author author = new Author(name: "Auth").save()
        controller.show(author.id)
        if(!author) assert response.redirectUrl == '/author/list'
        else{
            author = Author.get(author.id)
            assert view == "/author/show"
            assert model.author == author
        }
    }

    void testEdit() {
        Author author = new Author(name: "Auth").save()
        controller.edit(author.id)
        if(!author) assert response.redirectUrl == '/author/list'
        else{
            author = Author.get(author.id)
            assert view == "/author/edit"
            assert model.author == author
        }
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
