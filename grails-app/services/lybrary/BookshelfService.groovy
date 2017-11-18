package lybrary

import org.apache.commons.lang.math.RandomUtils

class BookshelfService {

    def addBook(Long id, Long bookId) {
        Bookshelf bookshelf = Bookshelf.get(id)
        if (bookshelf) {
            bookshelf.addToBooks(Book.get(bookId))
            return bookshelf.save()
        }
        throw new Exception("No such bookshelf")
    }

    def removeBook(Long id, Integer bookIndex) {
        Bookshelf bookshelf = Bookshelf.get(id)
        if (bookshelf) {
            bookshelf.books.remove(bookIndex)
            return bookshelf.save()
        }
        throw new Exception("No such bookshelf")
    }

    def remove(Long id) {
        def bookshelf = Bookshelf.get(id)
        if (bookshelf) {
            return bookshelf.delete()
        }
        throw new Exception("No such bookshelf")
    }

    def create(params) {
        return new Bookshelf(params).save()
    }

    def update(Long id, String name) {
        Bookshelf bookshelf = Bookshelf.get(id)
        if (bookshelf) {
            bookshelf.name = name
            return bookshelf.save()
        }
        throw new Exception("No such bookshelf")
    }

    def generate() {
        Bookshelf bookshelf = new Bookshelf(name: "Bookshelf" + Bookshelf.count(), books: [])
        int count = System.currentTimeMillis() % 10
        List<Book> books = Book.list()
        for (int i = 0; i < count; i++) {
            bookshelf.books.add(books.get(RandomUtils.nextInt(books.size())))
        }
        bookshelf.save()
    }
}
