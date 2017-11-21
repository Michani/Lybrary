package lybrary

class AuthorService {

    BookService bookService

    def update(Long id, String name) {
        Author author = Author.get(id)
        if (author) {
            author.name = name
            return author.save()
        }
        throw new Exception("No such author")
    }

    def create(params) {
        return new Author(params).save()
    }

    def delete(Long id) {
        Author author = Author.get(id)
        if (author) {
            Book.list().each {
                if(it.author == author) bookService.delete(it.id)
            }
            return author.delete()
        }
        throw new Exception("No such author")
    }
}
