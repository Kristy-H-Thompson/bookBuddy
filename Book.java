// Define a Book Class
class Book {
    // Define the Book instance variables
    // Make them private so that they can only be accessed inside the Book Class
    private String title;
    private String author;
    private boolean isAvailable;

    // Constructor for a Book object 
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    // Method to return a title of a Book
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // Method to return if a Book is available or not
    public boolean isAvailable() {
        return isAvailable;
    }

    // Method to borrow a Book
    // If the the is available, 
       // set the isAvailable to false and let the user know they have borrowed the book
    // If it is not available, let the used know it is not available
    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println(title + " has been borrowed.");
        } else {
            System.out.println(title + " is not available.");
        }
    }

    // Method to return a borrowed book
    public void returnBook() {
        isAvailable = true;
        System.out.println(title + " has been returned.");
    }

    // Method to display a book
    // Conditional - change the output slightly based on if the book is available or not
    public void displayBook() {
        System.out.println(title + " by " + author + " | " + (isAvailable ? "Available" : "Not Available"));
    }
}