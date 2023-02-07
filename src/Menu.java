public class Menu extends SocialNetwork {

    //Methods

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //This page offers the user the option to go back to another page (it's customizable)
    public static void goBackPage(String a, String b, String c, String back) {
        System.out.println(a);
        System.out.println("[1] " + b + "\n" + "[0] " + c + "\n");

        switch (inputCheck(1, 0)) {
            case 1:
                break;
            case 0:
                switch (back) {
                    case "Start Page" -> startPage();
                    case "User Menu" -> userMenuPage();
                    case "User Settings" -> userSettingsPage();
                    case "User Friends" -> userFriendsPage();
                }
                break;
        }
    }

    //Pauses the program
    public static void ok() {
        System.out.println("\n[1] Ok\n");
        inputCheck(1, 1);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Hello Page
    public static void helloPage() {
        System.out.println("###  Hello user! ###\n");
        System.out.println("### In order to select the option you want, you have to type the code that is displayed next to the option ###");
        startPage();
    }

    //This is the first page of the program, where the user chooses whether to login, register, get help or exit
    public static void startPage() {
        System.out.println("\n" + "-~~~~~~~~~-\n" + " Main Menu\n" + "-~~~~~~~~~-\n");
        System.out.println("[1] Login\n" + "[2] Register\n" + "[0] Exit\n");
        switch (inputCheck(2, 0)) {
            case 1 -> loginPage();
            case 2 -> registerPage();
            case 0 -> exitPage();
        }
    }

    //This is the login page
    private static void loginPage() {
        System.out.println("\n" + "-~~~~~-\n" + " Login\n" + "-~~~~~-\n");
        login();
        ok();
        userWelcomePage();
    }

    //This is the register page
    private static void registerPage() {
        System.out.println("\n" + "-~~~~~~~~-\n" + " Register\n" + "-~~~~~~~~-\n");
        register();
        goBackPage("### Do you want to login or go back? ###\n", "Login", "Back", "Start Page");
        userWelcomePage();
    }

    //This is the exit page
    private static void exitPage() {
        goBackPage("\n### Are you sure you want to exit? ###\n", "Yes", "No", "Start Page");
        writeUsersToFile();
        System.exit(0);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //This is the user welcome page
    public static void userWelcomePage() {
        if (Ul.isFirstLogin()) {
            System.out.println("\n### This is your first login. Please enter your first name and last name, so that your friends know who you are. ###\n");
            Ul.setFirstName(chooseFirstName());
            Ul.setLastName(chooseLastName());
            Ul.istFirstLogin();
            System.out.println("\n### Welcome " + Ul.getFirstName() + "! ###");
        } else {
            System.out.println("\n" + "### Welcome Back " + Ul.getFirstName() + "! ###");
        }
        userMenuPage();
    }

    //This is the user user menu page
    public static void userMenuPage() {
        System.out.println("\n" + "-~~~~~~~~~-\n" + " User Menu\n" + "-~~~~~~~~~-\n");
        System.out.println("[1] Account Settings\n" + "[2] Your Wall\n" + "[3] Friends\n" + "[0] Log out\n");
        switch (inputCheck(3, 0)) {
            case 0 -> Menu.startPage();
            case 1 -> userSettingsPage();
            case 2 -> userWallPage(Ul);
            case 3 -> userFriendsPage();
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //This is the user settings page
    public static void userSettingsPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~~~~~-\n" + " Account Settings\n" + "-~~~~~~~~~~~~~~~~-\n");
        System.out.println("[1] Change your username\n" + "[2] Change your password\n" + "[3] Change your email\n" + "[4] Change your first name and last name\n" + "[5] Delete your account\n" + "[0] Back\n");
        switch (inputCheck(5, 0)) {
            case 0 -> userMenuPage();
            case 1 -> changeUsernamePage();
            case 2 -> changePasswordPage();
            case 3 -> changeEmailPage();
            case 4 -> changeNamePage();
            case 5 -> deleteAccountPage();
        }
    }

    private static void changeUsernamePage() {
        System.out.println("\n" + "-~~~~~~~~~~~~~~~-\n" + " Change Username\n" + "-~~~~~~~~~~~~~~~-\n");
        Ul.setUsername(chooseUsername());
        System.out.println("\n### Your username has been successfully changed! ###");
        ok();
        userSettingsPage();
    }

    private static void changePasswordPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~~~~-\n" + " Change Password\n" + "-~~~~~~~~~~~~~~~-\n");
        Ul.setPassword(choosePassword());
        System.out.println("\n### Your password has been successfully changed! ###");
        ok();
        userSettingsPage();
    }

    private static void changeEmailPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~-\n" + " Change Email\n" + "-~~~~~~~~~~~~-\n");
        Ul.setEmail(chooseEmail());
        System.out.println("\n### Your email has been successfully changed! ###");
        ok();
        userSettingsPage();
    }

    private static void changeNamePage() {
        System.out.println("\n" + "-~~~~~~~~~~~-\n" + " Change Name\n" + "-~~~~~~~~~~~-\n");
        Ul.setFirstName(chooseFirstName());
        Ul.setLastName(chooseLastName());
        System.out.println("\n### Your first name and last name have been successfully changed! ###");
        ok();
        userSettingsPage();
    }

    private static void deleteAccountPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~~~-\n" + " Delete Account\n" + "-~~~~~~~~~~~~~~-\n");
        goBackPage("### Are you sure you want to delete your account? This action cannot be undone. ###\n", "Yes", "No", "User Settings");
        deleteUser(Ul);
        System.out.println("\n### Your account has been successfully deleted. ###");
        ok();
        startPage();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //This is the friends page
    public static void userFriendsPage() {
        System.out.println("\n" + "-~~~~~~~-\n" + " Friends\n" + "-~~~~~~~-\n");
        System.out.println("[1] View friends list\n" + "[2] Send friend request\n" + "[3] Pending friend requests\n" + "[0] Back\n");
        switch (inputCheck(3, 0)) {
            case 0 -> userMenuPage();
            case 1 -> friendsListPage();
            case 2 -> sendFriendRequestPage();
            case 3 -> pendingFriendRequestPage();
        }
    }

    public static void friendsListPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~-\n" + " Friends List\n" + "-~~~~~~~~~~~~-\n");
        Ul.showFriends();
        System.out.println("\n[0] Back\n");
        int input = inputCheck(Ul.getFriendList().size(), 0);
        if (input == 0) {
            userFriendsPage();
        } else {
            friendActionsPage(findUserFromID(Ul.getFriendList().get(input - 1)));
            ok();
            friendsListPage();
        }
    }

    public static void sendFriendRequestPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~~~~~~~~-\n" + " Send Friend Request\n" + "-~~~~~~~~~~~~~~~~~~~-\n");
        Ul.showNonFriends();
        System.out.println("\n[0] Back\n");
        int input = inputCheck(nonFriendList.size(), 0);
        switch (input) {
            case 0:
                userFriendsPage();
                break;
            default:
                Ul.sendFriendrequest(findUserFromID(nonFriendList.get(input - 1)));
                ok();
                sendFriendRequestPage();
                break;
        }
    }

    public static void pendingFriendRequestPage() {
        System.out.println("\n" + "-~~~~~~~~~~~~~~~~~~~~~~~-\n" + " Pending Friend Requests\n" + "-~~~~~~~~~~~~~~~~~~~~~~~-\n");
        Ul.showFriendRequests();
        System.out.println("\n[0] Back\n");
        int input = inputCheck(Ul.getFriendRequestList().size(), 0);
        if (input == 0) {
            userFriendsPage();
        } else {
            Ul.receiveFriendRequest(Ul.getFriendRequestList().get(input - 1));
            ok();
            pendingFriendRequestPage();
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //This is the user wall page
    public static void userWallPage(User U) {
        System.out.println("\n" + "-~~~~-\n" + " Wall\n" + "-~~~~-\n");
        System.out.println("### This is the wall of " + U.getFirstName() + ". ###\n");
        System.out.println("[1] New Post\n" + "[2] View Posts\n" + "[0] Back\n");
        switch (inputCheck(3, 0)) {
            case 0 -> userMenuPage();
            case 1 -> newPostPage(U);
            case 2 -> viewPostsPage(U);
        }
    }

    //This is the new post page
    public static void newPostPage(User U) {
        System.out.println("\n" + "-~~~~-\n" + " Post\n" + "-~~~~-\n");
        U.getWall().writePost(Ul);
        ok();
        userWallPage(U);
    }

    //This is the posts page
    public static void viewPostsPage(User U) {
        System.out.println("\n" + "-~~~~~-\n" + " Posts\n" + "-~~~~~-\n");
        U.getWall().showPosts();
        System.out.println("\n[0] Back\n");
        int input = inputCheck(U.getWall().getPosts().size(), 0);
        if (input == 0) {
            userWallPage(U);
        } else {
            postActionsPage(U, U.getWall().getPosts().get(input - 1));
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Friend actions
    public static void friendActionsPage(User U) {
        System.out.println("\n### You have selected " + U + ". ###\n");
        System.out.println("[1] View Friend's Wall\n" + "[2] View Common Friends\n" + "[3] Unfriend\n" + "[0] Back\n");
        switch (SocialNetwork.inputCheck(3, 0)) {
            case 0 -> friendsListPage();
            case 1 -> userWallPage(U);
            case 2 -> Ul.showCommonFriends(U);
            case 3 -> Ul.removeFriend(U);
        }
    }

    public static void commonFriendsPage(User U) {
        System.out.println("\n" + "-~~~~~~~~~~~-\n" + " Common Friends\n" + "-~~~~~~~~~~-\n");
        Ul.showCommonFriends(U);
        ok();
        friendActionsPage(U);

    }

    public void removeFriendPage(User U) {
        System.out.println("\n" + "-~~~~~~~~~~~~~-\n" + " Remove Friend\n" + "-~~~~~~~~~~~~~~-\n");
        System.out.println("### Are you sure you want to unfriend " + U + "? ###\n");
        System.out.println("[1] Yes\n" + "[0] No\n");
        switch (inputCheck(1, 0)) {
            case 0 -> friendActionsPage(U);
            case 1 -> {
                Ul.removeFriend(U);
                ok();
                friendsListPage();
            }
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Post actions
    public static void postActionsPage(User U, Post P) {
        System.out.println("\n### You have selected the following post. ###\n");
        System.out.println(P + "\n");
        System.out.println("[1] New Comment\n" + "[2] View Comments\n" + "[3] Like\n" + "[4] View Likes\n" + "[0] Back\n");
        switch (SocialNetwork.inputCheck(4, 0)) {
            case 0 -> Menu.viewPostsPage(U);
            case 1 -> {
                newCommentPage(U, P);
                ok();
                postActionsPage(U, P);
            }
            case 2 -> {
                viewCommentsPage(U, P);
                ok();
                postActionsPage(U, P);
            }
            case 3 -> {
                likePostPage(P);
                postActionsPage(U, P);
            }
            case 4 -> {
                viewPostLikesPage(P);
                ok();
                postActionsPage(U, P);
            }
        }
    }

    public static void newCommentPage(User U, Post P) {
        System.out.println("\n" + "-~~~~~~~-\n" + " Comment\n" + "-~~~~~~~-\n");
        P.writeComment(Ul);
    }

    public static void viewCommentsPage(User U, Post P) {
        System.out.println("\n" + "-~~~~~~~~-\n" + " Comments\n" + "-~~~~~~~~-\n");
        P.showComments(P);
        System.out.println("\n[0] Back\n");
        int input = SocialNetwork.inputCheck(P.comments.size(), 0);
        if (input == 0) {
            postActionsPage(U, P);
        } else {
            commentActionsPage(U, P, P.comments.get(input - 1));
        }
    }

    public static void likePostPage(Post P) {
        System.out.println("\n" + "-~~~~-\n" + " Like\n" + "-~~~~-\n");
        P.like(Ul);
    }

    public static void viewPostLikesPage(Post P) {
        System.out.println("\n" + "-~~~~~-\n" + " Likes\n" + "-~~~~~-\n");
        P.showLikes();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Comment actions
    public static void commentActionsPage(User U, Post P, Comment C) {
        System.out.println("\n### You have selected the following comment. ###\n");
        System.out.println(C + "\n");
        System.out.println("[1] Like\n" + "[2] View Likes\n" + "[0] Back\n");
        switch (SocialNetwork.inputCheck(4, 0)) {
            case 0:
                viewCommentsPage(U, P);
            case 1:
                likeCommentPage(U, C);
                commentActionsPage(U, P, C);
                break;
            case 2:
                viewCommentLikesPage(C);
                Menu.ok();
                commentActionsPage(U, P, C);
                break;
        }
    }

    public static void likeCommentPage(User U, Comment C) {
        System.out.println("\n" + "-~~~~-\n" + " Like\n" + "-~~~~-\n");
        C.like(Ul);
    }

    public static void viewCommentLikesPage(Comment C) {
        System.out.println("\n" + "-~~~~~-\n" + " Likes\n" + "-~~~~~-\n");
        C.showLikes();
    }
}
