package lybrary

import org.springframework.dao.DataIntegrityViolationException

class BookController {

    def bookService

    def index() {
        if(Book.count==0) redirect(action: "create")
        else redirect(action: "list")
    }

    def list() {
        render(view: "list", model: [authors: Book.list(params).sort {it.id}])
    }

    def create() {
        render(view: "create")
    }

    def save() {
        Book book
        if (params.id) {
            book = bookService.update(params.long('id'), params.name)
        } else {
            book = bookService.create(params)
        }
        redirect(action: "show", id: book.id)
    }

    def show(Long id) {
        def book = Book.get(id)
        if (!book) {
            return redirect(action: "list")
        }
        render(view: "show", model: [book: book])
    }

    def edit(Long id) {
        def book = Book.get(id)
        if (!book) {
            redirect(action: "list")
            return
        }
        render(view: "edit", model: [book: book])
    }


    def delete(Long id) {
        Book book = Book.get(id)
        Bookshelf.list().each{
            it.removeFromBooks(book)
        }
        bookService.delete(id)
        redirect(action: "list")
    }
}
