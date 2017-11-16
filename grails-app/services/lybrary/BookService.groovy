package lybrary

class BookService {

    def update(Long id, String name) {
        Book book = Book.get(id)
        if (book) {
            book.name = name
            return book.save()
        }
        throw new Exception("No such book")
    }

    def create(params) {
        return new Book(params).save()
    }

    def delete(Long id){
        def book = Book.get(id)
        if (book) {
            return book.delete()
        }
        throw new Exception("No such book")
    }
}
