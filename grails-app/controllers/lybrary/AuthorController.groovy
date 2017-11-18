package lybrary

class AuthorController {

    def authorService

    def index() {
        if (Author.count == 0) redirect(action: "create")
        else redirect(action: "list")
    }

    def list() {
        render(view: "list", model: [authors: Author.list(params).sort { it.id }])
    }

    def create() {
        render(view: "create")
    }

    def save() {
        Author author
        if (params.id) {
            author = authorService.update(params.long('id'), params.name)
        } else {
            author = authorService.create(params)
        }
        redirect(action: "show", id: author.id)
    }

    def show(Long id) {
        def author = Author.get(id)
        if (!author) {
            return redirect(action: "list")
        }
        render(view: "show", model: [author: author])
    }

    def edit(Long id) {
        def author = Author.get(id)
        if (!author) {
            redirect(action: "list")
            return
        }
        render(view: "edit", model: [author: author])
    }


    def delete(Long id) {
        authorService.delete(id)
        redirect(action: "list")
    }
}
