import dao.BookDAO;
import dao.UserDAO;
import dao.TransactionDAO;
import model.Book;
import model.User;
import model.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        while (true) {
            System.out.println("\n📚 Library Management System");
            System.out.println("1. Add a Book");
            System.out.println("2. View All Books");
            System.out.println("3. Add a User");
            System.out.println("4. View All Users");
            System.out.println("5. Issue a Book");
            System.out.println("6. Return a Book");
            System.out.println("7. View All Transactions");
            System.out.println("8. 🔍 Search Books");

            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author name: ");
                    String author = sc.nextLine();
                    Book book = new Book(0, title, author, true);
                    bookDAO.addBook(book);
                    break;

                case 2:
                    List<Book> books = bookDAO.getAllBooks();
                    for (Book b : books) {
                        System.out.println(b.getId() + " | " + b.getTitle() + " | " + b.getAuthor() + " | Available: " + b.isAvailable());
                    }
                    break;

                case 3:
                    System.out.print("Enter user name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    User user = new User(0, name, email);
                    userDAO.addUser(user);
                    break;

                case 4:
                    List<User> users = userDAO.getAllUsers();
                    for (User u : users) {
                        System.out.println(u.getId() + " | " + u.getName() + " | " + u.getEmail());
                    }
                    break;

                case 5:
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    transactionDAO.issueBook(userId, bookId);
                    break;

                case 6:
                    System.out.print("Enter Transaction ID: ");
                    int transactionId = sc.nextInt();
                    transactionDAO.returnBook(transactionId);
                    break;

                case 7:
                    List<Transaction> txList = transactionDAO.getAllTransactions();
                    for (Transaction t : txList) {
                        System.out.println(t.getId() + " | User: " + t.getUserId() +
                                " | Book: " + t.getBookId() +
                                " | Issued: " + t.getIssueDate() +
                                " | Returned: " + t.getReturnDate());
                    }
                    break;


                case 8:
                    System.out.println("1. Search by Title");
                    System.out.println("2. Search by Author");
                    System.out.print("Choose: ");
                    int searchChoice = sc.nextInt();
                    sc.nextLine(); // consume leftover newline

                    System.out.print("Enter search keyword: ");
                    String keyword = sc.nextLine();

                    List<Book> searchResults;
                    if (searchChoice == 1) {
                        searchResults = bookDAO.searchBooksByTitle(keyword);
                    } else {
                        searchResults = bookDAO.searchBooksByAuthor(keyword);
                    }

                    if (searchResults.isEmpty()) {
                        System.out.println("❌ No books found.");
                    } else {
                        for (Book b : searchResults) {
                            System.out.println(b.getId() + " | " + b.getTitle() + " | " + b.getAuthor() + " | Available: " + b.isAvailable());
                        }
                    }
                    break;


                case 0:
                    System.out.println("👋 Exiting the system. Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
}
