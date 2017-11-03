package lybrary

class Bookshelf {

    String name
    List<Book> books

    static hasMany = [books: Book]

    static constraints = {
        name nullable: false, blank: false
    }
}
