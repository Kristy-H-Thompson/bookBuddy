// ArrayList allows you to have an array without having a set length
// So that the array of books can be dynamic 
import java.util.ArrayList;

// HashMap allows you to store key-value pairs
// This will keep track of who borrowed the book
import java.util.HashMap;

// LocalDate allows us to store time and track due dates
import java.time.LocalDate;

// Define the Library class
class Library {
    // Define the Library instance variables
    // books: Stores the list of available books
    private ArrayList<Book> books = new ArrayList<>();
    
    // borrowedBooks: Keeps track of which student borrowed which books
    private HashMap<Student, ArrayList<Book>> borrowedBooks = new HashMap<>();
    
    // dueDates: Stores the due date for each borrowed book
    private HashMap<Book, LocalDate> dueDates = new HashMap<>();

    // Method to add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to borrow a book
    public void borrowBook(Student student, Book book) {
        // Check if the book is in the library and is available
        if (books.contains(book) && book.isAvailable()) {
            // Call the borrowBook method from the Book class
            book.borrowBook();

            // Ensure the student has an entry in the borrowedBooks HashMap
            borrowedBooks.putIfAbsent(student, new ArrayList<>());

            // Add the book to the student's borrowed list
            borrowedBooks.get(student).add(book);

            // Set the due date for this book (e.g., 14 days from today)
            LocalDate dueDate = LocalDate.now().plusDays(14);
            dueDates.put(book, dueDate); 

            // Print confirmation message
            System.out.println(student.getName() + " borrowed " + book.getTitle() + " (Due on: " + dueDate + ")");
        } else {
            System.out.println("Sorry, this book is not available.");
        }
    }

    // Method to return a borrowed book
    public void returnBook(Student student, Book book) {
        // Check if the student has borrowed this book
        if (borrowedBooks.containsKey(student) && borrowedBooks.get(student).contains(book)) {
            // Call the returnBook method from the Book class
            book.returnBook();

            // Remove the book from the student's borrowed list
            borrowedBooks.get(student).remove(book);

            // Remove the due date from the system
            dueDates.remove(book);

            // Print confirmation message
            System.out.println(student.getName() + " returned " + book.getTitle());
        } else {
            System.out.println("Error: This book was not borrowed by " + student.getName());
        }
    }

    // Method to display books a student has borrowed along with due dates
    public void displayBorrowedBooks(Student student) {
        System.out.println("\nBooks borrowed by " + student.getName() + ":");

        // Retrieve the list of books borrowed by the student (or an empty list if none)
        ArrayList<Book> studentBooks = borrowedBooks.getOrDefault(student, new ArrayList<>());

        // If the student has no borrowed books, print a message
        if (studentBooks.isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            // Loop through the books and display their titles along with due dates
            for (Book book : studentBooks) {
                LocalDate dueDate = dueDates.get(book);
                System.out.println("- " + book.getTitle() + " (Due on: " + dueDate + ")");
            }
        }
    }

    // Method to check and display overdue books
    public void displayOverdueBooks() {
        System.out.println("\nOverdue Books:");

        // Get today's date
        LocalDate today = LocalDate.now();
        boolean hasOverdue = false;

        // Loop through all borrowed books to check for overdue books
        for (Book book : dueDates.keySet()) {
            LocalDate dueDate = dueDates.get(book);

            // If the book is overdue, display it
            if (dueDate.isBefore(today)) {
                System.out.println("- " + book.getTitle() + " (Due on: " + dueDate + ")");
                hasOverdue = true;
            }
        }

        // If no overdue books, print a message
        if (!hasOverdue) {
            System.out.println("No overdue books.");
        }
    }
}
