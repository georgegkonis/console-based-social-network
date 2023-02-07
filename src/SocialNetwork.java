import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SocialNetwork {

    private static String username;
    private static String password;
    private static String passwordVer;
    private static String email;
    public static int count;

    public static List<User> users = new ArrayList<>();
    public static List<Integer> nonFriendList = new ArrayList<>();

    //Objects
    protected static User Ul; //Stores the user that logs in

    //Scanner
    public static Scanner sc = new Scanner(System.in);

    //Methods

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Writes all the users on the list to a file
    public static void writeUsersToFile() {
        String fileName = "user_data";
        try {
            try {
                PrintWriter writer = new PrintWriter(fileName);
                writer.close();
            } catch (FileNotFoundException fe) {
                System.out.println("\n@@@ File not found @@@\n");
            }
            FileOutputStream f = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(f);
            for (User u : users) {
                o.writeObject(u);
            }
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("\n@@@ File not found @@@\n");
        } catch (IOException e) {
            System.out.println("\n@@@ Error initializing stream @@@\n");
        }
    }

    // Reads the users from the file and adds them to the lis
    public static void readUsersFromFile() {
        int i;
        try {
            FileInputStream fis = new FileInputStream("user_data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            if (users.size() > 0)
                for (i = 0; i < users.size() + 1; i++)
                    users.remove(i);
            for (i = 0; i < users.size() + 1; i++) {
                User us;
                us = (User) ois.readObject();
                if (us != null) users.add(us);
                else break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Returns the current timestamp
    public static String getTimestamp() {
        DateTimeFormatter timestamp = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return timestamp.format(now);
    }

    //Is used to check the input during the navigation of the menus
    public static int inputCheck(int highestInput, int lowestInput) {
        Boolean invalidInput = false;
        int input = 0;
        do {
            try {
                System.out.print("Choose: ");
                input = SocialNetwork.sc.nextInt();
                if (input > highestInput || input < lowestInput) {
                    System.out.println("\n### Invalid input! ###\n");
                    invalidInput = true;
                } else {
                    invalidInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n### Invalid input! ###\n");
                invalidInput = true;
            }
            sc.nextLine();
        } while (invalidInput);
        return input;
    }

    //Writes text and returns it
    public static String writeText() {
        System.out.print("Type: ");
        return sc.nextLine();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Login to an existing account
    protected static void login() {
        do {
            System.out.print("Username: ");
            username = sc.next();
            System.out.print("Password: ");
            password = sc.next();
            if (!checkUsername(username) || !checkPassword(password)) {
                System.out.println("\n### Login failed! ###\n");
                Menu.goBackPage("### Do you want to retry or go back? ###\n", "Retry", "Back", "Start Page");
                System.out.println("\n" + "-~~~~~-\n" + " Retry\n" + "-~~~~~-\n");
            }
        } while (!checkUsername(username) || !checkPassword(password));
        Ul = findUserFromUsername(username);
        System.out.println("\n### Login successful! ###");
    }

    //Register a new account
    protected static void register() {
        users.add(new User(chooseUsername(), choosePassword(), chooseEmail()));
        System.out.println("\n### Registration successful! ###\n");
        Ul = findUserFromUsername(username);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //User chooses a username
    protected static String chooseUsername() {
        do {
            System.out.print("Username: ");
            username = sc.nextLine();
            if (checkUsername(username)) {
                System.out.println("\n### Username not available! ###\n");
            }

        } while (checkUsername(username));
        return username;
    }

    //User chooses a password
    protected static String choosePassword() {
        do {
            System.out.print("Password: ");
            password = sc.nextLine();
            System.out.print("Verify Password: ");
            passwordVer = sc.nextLine();
            if (!password.equals(passwordVer)) {
                System.out.println("\n### Passwords don't match! ###\n");
            }
        } while (!password.equals(passwordVer));
        return password;
    }

    //User chooses an email
    protected static String chooseEmail() {
        do {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (checkEmail(email)) {
                System.out.println("\n### Email not available! ###\n");
            }

        } while (checkEmail(email));
        return email;
    }

    //User chooses first name
    protected static String chooseFirstName() {
        System.out.print("First name: ");
        return sc.nextLine();
    }

    //User chooses last name
    protected static String chooseLastName() {
        System.out.print("Last name: ");
        return sc.nextLine();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Checks if the username entered is available
    private static Boolean checkUsername(String username) {
		return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    //Checks if the password entered matches the username entered
    private static Boolean checkPassword(String password) {
		User user = findUserFromUsername(username);
		if (user ==  null) return false;
		return user.getPassword().equals(password);
	}

    //Checks if the email entered is available
    private static Boolean checkEmail(String email) {
        for (User user : users)
            if (user.getEmail().equals(email)) return true;
        return false;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static User findUserFromUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username)) return user;
        return null;
    }

    public static User findUserFromID(int id) {
        for (User user : users)
            if (user.getId() == id) return user;
        return null;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Deletes the specified user
    public static void deleteUser(User user) {
        List<FriendRequest> friendRequestsDel = new ArrayList<>();
        List<Post> postsDel = new ArrayList<>();
        List<Comment> commentsDel = new ArrayList<>();

        for (User U : users) {
            U.getFriendList().remove(user.getId());


            for (FriendRequest FR : U.getFriendRequestList()) {
                if (FR.getSenderId() == user.getId()) {
                    friendRequestsDel.add(FR);
                }
            }
            U.getFriendRequestList().removeAll(friendRequestsDel);
            for (Post P : U.getWall().getPosts()) {
                if (P.getSenderID() == user.getId()) {
                    postsDel.add(P);
                }
                P.likes.remove(user.getId());

                for (Comment C : P.comments) {
                    if (C.getSenderID() == user.getId()) {
                        commentsDel.add(C);
                    }
                    C.likes.remove(user.getId());
                }
                P.comments.removeAll(commentsDel);
            }
            U.getWall().getPosts().removeAll(postsDel);
        }
        users.remove(user);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}