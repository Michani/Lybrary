package lybrary

class BookshelfController {

    BookshelfService bookshelfService

    def index() {
        if (Bookshelf.count == 0) return redirect(action: "create")
        else return redirect(action: "list")
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
        return redirect(action: "show", id: bookshelf.id)

    }

    def addBook(Long id, Long bookId) {
        bookshelfService.addBook(id, bookId)
        return redirect(action: "show", id: id)

    }

    def removeBook(Long id, Integer bookIndex) {
        bookshelfService.removeBook(id, bookIndex)
        return redirect(action: "show",id: id)

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
            return redirect(action: "list")

        }
        render(view: "edit", model: [bookshelf: bookshelf])
    }

    def delete(Long id) {
        Bookshelf bookshelf = Bookshelf.get(id)
        if(!bookshelf){
            return redirect(action: "list")
        } else{
            bookshelfService.remove(bookshelf.id)
            return redirect(action: "index")
        }

    }

    def generate() {
        Bookshelf bookshelf = bookshelfService.generate()
        render(view: "show", model: [bookshelf: bookshelf])

    }
}
