public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Creating books
        Book book1 = new Book("The Alchemist", "Paulo Coelho");
        Book book2 = new Book("Harry Potter", "J.K. Rowling");

        // Librarian adds books
        Librarian librarian = new Librarian("Mr. Smith");
        librarian.addBook(library, book1);
        librarian.addBook(library, book2);

        // Display books
        library.displayBooks();

        // Student borrows a book
        Student student = new Student("Alice");
        student.borrowBook(book1);

        // Display books again
        library.displayBooks();

        // Student returns a book
        student.returnBook(book1);
        library.displayBooks();
    }
}
