package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(AuthorService)
@Mock([Author,Book, Bookshelf, BookService])
class AuthorServiceTests {

    void testUpdate() {
        assert Author.count == 0
        Author author = new Author(name: "Auth").save()
        assert Author.count == 1
        service.update(author.id, "anotherAuth")
        assert Author.count == 1
        author = Author.get(author.id)
        assert author != null
        assert author.name == 'anotherAuth'
    }

    void testWrongUpdate() {
        assert Author.count == 0
        new Author(name: "Auth").save()
        assert Author.count == 1
        try{
            service.update(2, 'anotherAuth')
        }catch (Exception e){
            assert e.message == "No such author"
        }
    }

    void testCreate() {
        assert Author.count == 0
        service.create([name : 'Auth'])
        assert Author.count == 1
        assert Author.findByName('Auth') != null
    }

    void testDelete() {
        assert Author.count == 0
        Author author = new Author(name: "Auth").save()
        assert Author.count == 1
        author = Author.get(author.id)
        service.delete(author.id)
        assert Author.count == 0
        author = Author.get(author.id)
        assert author == null
    }

    void testWrongDelete() {
        assert Author.count == 0
        new Author(name: "Auth").save()
        assert Author.count == 1
        try {
            service.delete(2)
        }catch (Exception e){
            assert e.message == "No such author"
        }
    }
}
