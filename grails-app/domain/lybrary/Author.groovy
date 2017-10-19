package lybrary

class Author {

    String name

    static  hasMany = [book:Book]

    String toString() {
        return name
    }

    static constraints = {
        name blank: false, unique: true
    }
}
