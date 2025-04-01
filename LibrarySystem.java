public class LibrarySystem {
    public static void main(String[] args) {
        // Create a Library
        Library library = new Library();

        // Creating books
        Book book1 = new Book("The Alchemist", "Paulo Coelho");
        Book book2 = new Book("Harry Potter", "J.K. Rowling");

        // Librarian adds books to the library
        Librarian librarian = new Librarian("Mr. Smith");
        librarian.addBook(library, book1);
        librarian.addBook(library, book2);

        // Create a Student
        Student student = new Student("Alice");

        // Student borrows books
        library.borrowBook(student, book1);

        // Simulate overdue by setting book due date to the past
        library.returnBook(student, book1); // This will now charge a fine

        // Display fines
        library.getFine(student);

        // Student pays part of the fine
        library.payFine(student, 2);

        // Student pays off the rest of the fine
        library.payFine(student, 10);

        // Display fines again
        library.getFine(student);
    }
}
