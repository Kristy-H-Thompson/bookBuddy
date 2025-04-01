// Define a User Class
class User {
    // Define the User instance variables
    protected String name;

    // Constructor for a User object 
    public User(String name) {
        this.name = name;
    }

    // Method to display a User's name
    public void displayUser() {
        System.out.println("User: " + name);
    }
}

// Define a student subclass
class Student extends User {
    // Constructor for student object
    public Student(String name) {
        super(name);
    }

    // Method to return a students name
    public String getName() {
        return name;
    }

    // Calls the borrowBook method from the book class
    public void borrowBook(Book book) {
        book.borrowBook();
    }

    // Calls the returnBook method from the book class
    public void returnBook(Book book) {
        book.returnBook();
    }
}

// Define a librarian subclass
class Librarian extends User {
    // constructor for librarian object
    public Librarian(String name) {
        super(name);
    }

    // calls the addBook method from the library object
    public void addBook(Library library, Book book) {
        library.addBook(book);
        System.out.println(name + " added a new book: " + book.getTitle());
    }
}
