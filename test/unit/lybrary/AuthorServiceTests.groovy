package lybrary

import grails.test.mixin.Mock
import grails.test.mixin.TestFor



@TestFor(AuthorService)
@Mock([Author,Book, Bookshelf, BookService])
class AuthorServiceTests {

    void testUpdate() {
        assert Author.count == 0
        Author author = service.create(name: "Auth")
        assert Author.count == 1
        author = Author.get(author.id)
        try{
            service.update(author.id, "anotherAuth")
            assert Author.findByName("Auth") == null
            assert Author.findByName("anotherAuth") != null
        } catch (Exception e){
            assert e.toString() == "No such book"
        }
    }

    void testCreate() {
        assert Author.count == 0
        assert Author.findByName('Auth') == null
        service.create([name : 'Auth'])
        assert Author.count == 1
        assert Author.findByName('Auth') != null
    }

    void testDelete() {
        assert Author.count == 0
        Author author = service.create(name: "Auth")
        assert Author.count == 1
        assert Author.findByName("Auth") != null
        author = Author.get(author.id)
        try{
            service.delete(author.id)
            assert Author.count == 0
        }catch (Exception e){
            assert e.toString() == "No such book"
        }
    }
}
