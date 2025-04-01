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

        // ✅ Create a Student
        Student student = new Student("Alice");

        // ❌ Incorrect (missing student argument)
        // library.displayBorrowedBooks(); 

        // ✅ Correct (pass student as an argument)
        library.displayBorrowedBooks(student);

        // Student borrows a book
        student.borrowBook(book1);

        // ✅ Display books borrowed by Alice
        library.displayBorrowedBooks(student);

        // Student returns a book
        student.returnBook(book1);

        // ✅ Display books borrowed by Alice again
        library.displayBorrowedBooks(student);
    }
}
