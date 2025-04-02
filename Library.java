// ArrayList allows you to have an array without having a set length
import java.util.ArrayList;

// HashMap allows you to store key-value pairs
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
        System.out.println("Added book: " + book.getTitle() + " by " + book.getAuthor());
    }


    //  Method to store students in the borrowedBooks list when they borrow for the first time
    public void addStudent(Student student) {
        // Ensure they are registered
        borrowedBooks.putIfAbsent(student, new ArrayList<>()); 
    }

    // Method to borrow a book
    public void borrowBook(Student student, Book book) {
        if (book.isAvailable()) { 
            // Mark book as borrowed
            book.borrowBook(); 
           
            // Ensure student has a list in borrowedBooks
            borrowedBooks.putIfAbsent(student, new ArrayList<>());
    
            // Add the new book to the borrowed Books list
            borrowedBooks.get(student).add(book);
    
            // Store the book's due date
            dueDates.put(book, LocalDate.now().plusDays(14));
    
            System.out.println(book.getTitle() + " has been borrowed.");
            System.out.println(student.getName() + " borrowed " + book.getTitle() + " (Due on: " + dueDates.get(book) + ")");
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
    public String getBorrowedBooks(Student student) {

        // Check if the student has any borrowed books
        if (!borrowedBooks.containsKey(student) || borrowedBooks.get(student).isEmpty()) {
            return student.getName() + " has not borrowed any books.";
        }
    
        // Start building out the string to display the borrowed list
        StringBuilder borrowedList = new StringBuilder();
        borrowedList.append(student.getName()).append(" has borrowed:\n");
    
        // For each borrowed book display the title, author, and due date
        for (Book book : borrowedBooks.get(student)) {
            LocalDate dueDate = dueDates.getOrDefault(book, LocalDate.now());
            borrowedList.append("- ").append(book.getTitle())
                        .append(" (Due on: ").append(dueDate).append(")\n");
        }
    
        return borrowedList.toString();
    }
    
    
    // Method to find a sudent
    public Student findStudent(String name) {
        for (Student student : borrowedBooks.keySet()) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
    
    // Gets a list of borrowed books for that student
    public boolean hasBorrowedBook(Student student, Book book) {
        Student existingStudent = findStudent(student.getName()); 
            return existingStudent != null && borrowedBooks.containsKey(existingStudent) && borrowedBooks.get(existingStudent).contains(book);
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

        // Check if the student has any outstanding fines
        if (currentFine <= 0) {
            System.out.println(student.getName() + " has no outstanding fines.");
            return; // Exit the method
        }

        // ✅ If the amount is greater than or equal to the fine, clear the fine
        if (amount >= currentFine) {
            fines.put(student, 0.0);
            System.out.println(student.getName() + " has paid off all fines.");
        } else {
            // ✅ Deduct the paid amount from the fine
            fines.put(student, currentFine - amount);
            System.out.println(student.getName() + " paid $" + amount + ". Remaining fine: $" + fines.get(student));
        }
    }

    
}
