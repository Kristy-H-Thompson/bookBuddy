// ArrayList allows you to have an array without having a set length
// So that the array of books can be dynamic 
import java.util.ArrayList;

// HashMap allows you to store key value pairs
// This will  keep track of who borrowed the book
import java.util.HashMap;

// Define the library class
class Library {
    // Define the Library instance variables
    // In this case it is an array list of books
    private ArrayList<Book> books = new ArrayList<>();
    private HashMap<Student, ArrayList<Book>> borrowedBooks = new HashMap<>();


    // Method to add a book
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to borrow a book
    public void borrowBook(Student student, Book book) {
        // if the book is in the array list, and it is available
        if (books.contains(book) && book.isAvailable()) {
            // call the borrow book method from the Book class
            book.borrowBook();
            borrowedBooks.putIfAbsent(student, new ArrayList<>());
            borrowedBooks.get(student).add(book);
            System.out.println(student.getName() + " borrowed " + book.getTitle());
        } else {
            System.out.println("Sorry, this book is not available.");
        }
    }

    // Method to display books a student has borrowed
    public void displayBorrowedBooks(Student student) {
        System.out.println("\nBooks borrowed by " + student.getName() + ":");
        ArrayList<Book> studentBooks = borrowedBooks.getOrDefault(student, new ArrayList<>());
        for (Book book : studentBooks) {
            System.out.println("- " + book.getTitle());
        }
    }
}