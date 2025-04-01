import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI {
    private Library library;
    private JFrame frame;
    private JTextField bookTitleField, studentNameField;
    private JTextField authorField;
    private JTextArea displayArea;
    private JButton addBookButton, borrowBookButton, returnBookButton, checkFinesButton, payFineButton, showBooksButton, viewBorrowedBooksButton;

    public LibraryGUI() {
        library = new Library(); // Create an instance of the Library

        // Create the main window
        frame = new JFrame("Library System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Create input fields
        studentNameField = new JTextField(15);
        bookTitleField = new JTextField(15);
        authorField = new JTextField(15);
        frame.add(new JLabel("Author:"));
        frame.add(authorField);

        // Create display area
        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);

        // Create buttons
        addBookButton = new JButton("Add Book");
        borrowBookButton = new JButton("Borrow Book");
        returnBookButton = new JButton("Return Book");
        checkFinesButton = new JButton("Check Fines");
        payFineButton = new JButton("Pay Fine");
        showBooksButton = new JButton("Show Books");
        viewBorrowedBooksButton = new JButton("View Borrowed Books");

        // Add action listeners
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        borrowBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        checkFinesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkFines();
            }
        });

        payFineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                payFine();
            }
        });

        showBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showBooks();
            }
        });

        viewBorrowedBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewBorrowedBooks();
            }
        });

        // Add components to the frame
        frame.add(new JLabel("Student Name:"));
        frame.add(studentNameField);
        frame.add(new JLabel("Book Title:"));
        frame.add(bookTitleField);
        frame.add(addBookButton);
        frame.add(borrowBookButton);
        frame.add(returnBookButton);
        frame.add(checkFinesButton);
        frame.add(payFineButton);
        frame.add(showBooksButton);
        frame.add(viewBorrowedBooksButton);
        frame.add(new JScrollPane(displayArea));

        // Show the frame
        frame.setVisible(true);
    }

    // Method to add a book
    private void addBook() {
        String title = bookTitleField.getText();
        String author = authorField.getText();
    
        if (!title.isEmpty() && !author.isEmpty()) {
            Book book = new Book(title, author); // Create a new book
            library.addBook(book); // Add it to the library
            displayArea.setText("Added book: " + title + " by " + author);
        } else {
            displayArea.setText("Please enter both book title and author.");
        }
    }

    // Method to borrow a book
    private void borrowBook() {
        String studentName = studentNameField.getText();
        String bookTitle = bookTitleField.getText();

        if (!studentName.isEmpty() && !bookTitle.isEmpty()) {
            Student student = new Student(studentName);
            Book book = findBook(bookTitle);

            if (book != null) {
                library.borrowBook(student, book);
                displayArea.setText(studentName + " borrowed " + bookTitle);
            } else {
                displayArea.setText("Book not found.");
            }
        } else {
            displayArea.setText("Enter both student name and book title.");
        }
    }


    // Method to return a book
    private void returnBook() {
        String studentName = studentNameField.getText();
        String bookTitle = bookTitleField.getText();
    
        if (!studentName.isEmpty() && !bookTitle.isEmpty()) {
            Student student = library.findStudent(studentName); // Find the existing student
            Book book = findBook(bookTitle); // Find the book in the library
    
            if (student == null) {
                displayArea.setText(studentName + " has not borrowed any books.");
                return;
            }
    
            if (book == null) {
                displayArea.setText("Book not found.");
                return;
            }
    
            // Check if the student actually borrowed the book before allowing return
            if (!library.hasBorrowedBook(student, book)) {
                displayArea.setText(studentName + " did not borrow " + bookTitle);
                return;
            }
    
            // Proceed with returning the book
            library.returnBook(student, book);
            displayArea.setText(studentName + " returned " + bookTitle);
        } else {
            displayArea.setText("Enter both student name and book title.");
        }
    }
    
    // Method to check fines
    private void checkFines() {
        String studentName = studentNameField.getText();
        if (!studentName.isEmpty()) {
            Student student = new Student(studentName);
            library.getFine(student);
            displayArea.setText(studentName + "'s fine: $" + library.getFine(student));
        } else {
            displayArea.setText("Enter student name.");
        }
    }

    // Method to pay a fine
    private void payFine() {
        String studentName = studentNameField.getText();
        if (!studentName.isEmpty()) {
            Student student = new Student(studentName);
            library.payFine(student, 5.0); // Assume $5 payment
            displayArea.setText(studentName + " paid $5. Remaining fine: $" + library.getFine(student));
        } else {
            displayArea.setText("Enter student name.");
        }
    }

    // Method to display all books
    private void showBooks() {
        displayArea.setText("Library Books:\n");
    
        for (Book book : library.getBooks()) {
            displayArea.append(book.getTitle() + " by " + book.getAuthor() + 
                               " | " + (book.isAvailable() ? "Available" : "Not Available") + "\n");
        }
    }
    
    // Helper method to find a book by title
    private Book findBook(String title) {
        for (Book book : library.getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private void viewBorrowedBooks() {
        String studentName = studentNameField.getText();
    
        if (!studentName.isEmpty()) {
            Student student = library.findStudent(studentName); // Find the existing student
    
            if (student != null) {
                String borrowedBooks = library.getBorrowedBooks(student);
                displayArea.setText(borrowedBooks);
            } else {
                displayArea.setText(studentName + " has not borrowed any books.");
            }
        } else {
            displayArea.setText("Enter a student name to view borrowed books.");
        }
    }
    

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
