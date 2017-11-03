package lybrary

class BookshelfController {

    BookshelfService bookshelfService

    def index() {
        return list()
    }

    def list() {
        render(view: "list", model: [bookshelves: Bookshelf.list(params).sort {it.id}])
    }

    def create() {
        render(view: "create")
    }

    def save() {
        Bookshelf bookshelf
        if (params.id) {
            bookshelf = bookshelfService.update(params.long('id'), params.name)
        } else {
            bookshelf = bookshelfService.create(params)
        }
        redirect(action: "show", id: bookshelf.id)
    }

    def addBook(Long id, Long bookId) {
        bookshelfService.addBook(id, bookId)
        redirect(action: "show", id: id)
    }

    def removeBook(Long id, Integer bookIndex) {
        bookshelfService.removeBook(id, bookIndex)
        redirect(action: "show", id: id)
    }

    def show(Long id) {
        def bookshelf = Bookshelf.get(id)
        if (!bookshelf) {
            return redirect(action: "list")
        }
        render(view: "show", model: [bookshelf: bookshelf])
    }

    def edit(Long id) {
        def bookshelf = Bookshelf.get(id)
        if (!bookshelf) {
            redirect(action: "list")
            return
        }
        render(view: "edit", model: [bookshelf: bookshelf])
    }

    def delete(Long id) {
        bookshelfService.remove(id)
        redirect(action: "list")
    }

    def generate() {
        bookshelfService.generate()
        redirect(action: "list")
    }
}
