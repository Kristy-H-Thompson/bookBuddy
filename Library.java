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

    // fines: Stores the amount of fines a student owes
    private HashMap<Student, Double> fines = new HashMap<>();

    // Fine rate per day (for late returns)
    private static final double FINE_PER_DAY = 1.0; 

    // Method to search for a book
    public void searchBook(String keyword) {
        System.out.println("\nSearch Results for: " + keyword);
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                book.displayBook();
            }
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

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

    // Method to return a borrowed book and check for overdue fines
    public void returnBook(Student student, Book book) {
        // Check if the student has borrowed this book
        if (borrowedBooks.containsKey(student) && borrowedBooks.get(student).contains(book)) {
            // Get today's date and the book's due date
            LocalDate today = LocalDate.now();
            LocalDate dueDate = dueDates.get(book);

            // If the book is overdue, calculate the fine
            if (dueDate.isBefore(today)) {
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
                double fineAmount = daysLate * FINE_PER_DAY;

                // Add the fine to the student's account
                fines.put(student, fines.getOrDefault(student, 0.0) + fineAmount);

                // Notify the student about the fine
                System.out.println("Late return! " + student.getName() + " owes $" + fineAmount + " for returning " + book.getTitle() + " " + daysLate + " days late.");
            }

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

    // Method to display overdue books
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

    // Method to get a student's fine balance (returns double)
    public double getFine(Student student) {  // ✅ Change from void → double
        return fines.getOrDefault(student, 0.0);
    }

    // Method to pay a fine
    public void payFine(Student student, double amount) {
        double currentFine = fines.getOrDefault(student, 0.0);

        // If the student has no fines, print a message
        if (currentFine == 0) {
            System.out.println(student.getName() + " has no outstanding fines.");
            return;
        }

        // If the amount is greater than the fine, set fine to 0
        if (amount >= currentFine) {
            fines.put(student, 0.0);
            System.out.println(student.getName() + " has paid off all fines.");
        } else {
            // Deduct the paid amount from the fine
            fines.put(student, currentFine - amount);
            System.out.println(student.getName() + " paid $" + amount + ". Remaining fine: $" + fines.get(student));
        }
    }
}
