package lybrary

class Book {

    String name

    Author author

    String toString() {
        return name
    }


    static constraints = {
        name blank: false, unique: true
    }
}
