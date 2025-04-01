// imports the ArrayList class from the utils package
// ArrayList allows you to have an array without having a set length
// So that the array of books can be dynamic 
import java.util.ArrayList;

// Define the library class
class Library {
    // Define the Library instance variables
    // In this case it is an array list of books
    private ArrayList<Book> books = new ArrayList<>();

    // Method to add a book
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to display a list of books
    public void displayBooks() {
        System.out.println("\nLibrary Books:");
        // for every Book in books, call the displayBook method
        for (Book book : books) {
            book.displayBook();
        }
    }
}
