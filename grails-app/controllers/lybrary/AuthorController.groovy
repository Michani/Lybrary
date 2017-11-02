package lybrary

import org.codehaus.groovy.grails.plugins.InvalidVersionException
import org.springframework.dao.DataIntegrityViolationException

class AuthorController {

    def authorService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        if(Author.count==0) redirect(action: "create")
        else redirect(action: "list", params: params)
    }

    def list() {
        [authorInstanceList: Author.list(), authorInstanceTotal: Author.count()]
    }

    def create() {
        [authorInstance: new Author(params)]
    }

    def save() {
        if (!authorService.save(params)) {
            render(view: "create", model: [authorInstance: Author(params)])
            return
        }
        redirect action: "list"
    }

    def show(Long id) {
        def authorInstance = Author.get(id)
        if (!authorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), id])
            redirect(action: "list")
            return
        }

        [authorInstance: authorInstance]
    }

    def edit(Long id) {
        def authorInstance = Author.get(id)
        if (!authorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), id])
            redirect(action: "list")
            return
        }

        [authorInstance: authorInstance]
    }

    def update(Long id) {

        try{
            def authorInstance = Author.get(id)
            authorService.update(authorInstance, params)
        } catch (Exception) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), id])
            redirect(action: "list")
            return
        } catch (InvalidVersionException) {
                authorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'author.label', default: 'Author')] as Object[],
                          "Another user has updated this Author while you were editing")
                render(view: "edit", model: [authorInstance: authorInstance])
                return
        } catch (NotActiveException) {
            render(view: "edit", model: [authorInstance: authorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'author.label', default: 'Author'), authorInstance.id])
        redirect(action: "show", id: authorInstance.id)
    }

    def delete(Long id) {
        def authorInstance = Author.get(id)
        if (!authorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), id])
            redirect(action: "list")
            return
        }

        try {
            authorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'author.label', default: 'Author'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'author.label', default: 'Author'), id])
            redirect(action: "show", id: id)
        }
    }
}
