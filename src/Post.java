import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Post implements Serializable {

    //Lists
    HashSet<Integer> likes = new HashSet<Integer>();
    List<Comment> comments = new ArrayList<Comment>();

    //Variables
    protected String timestamp;
    protected String text;
    protected int senderID;

    //Constructor
    Post(int senderID, String text) {
        timestamp = SocialNetwork.getTimestamp();
        this.text = text;
        this.senderID = senderID;
    }

    //Methods

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Returns the sender id
    public int getSenderID() {
        return senderID;
    }

    //Overrides java.lang.Object.toString
    public String toString() {
        return "(" + timestamp + ") " + SocialNetwork.findUserFromID(senderID) + ": " + text + "  -{Likes: " + likes.size() + "}-{Comments: " + comments.size() + "}-";
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Checks whether or no specified user has liked this post
    public Boolean hasLiked(User U) {
		return likes.contains(U.getId());
	}

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Shows the comments on this post
    public void showComments(Post P) {
        if (comments.size() == 0) {
            System.out.println("### There are no comments yet. ###");
        } else {
            System.out.println("### There are " + comments.size() + " comments. ###\n");
            System.out.println("### Select a comment. ###\n");
            System.out.println(P);
        }
        for (Comment C : comments) {
            System.out.println("\n[" + (comments.indexOf(C) + 1) + "]--->" + C);
        }
    }

    //Shows the users who have liked this post
    public void showLikes() {
        if (likes.size() == 0) {
            System.out.println("### No one has liked this post yet. ###");
        }
        for (Integer I : likes) {
            System.out.println(SocialNetwork.findUserFromID(I) + " likes this");
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Specified user comments on this post
    public void writeComment(User U) {
        comments.add(new Comment(U.getId(), SocialNetwork.writeText()));
        System.out.println("\n### Commented! ###");
    }

    //Specified user likes or dislikes this post
    public void like(User U) {
        if (!hasLiked(U)) {
            likes.add(U.getId());
            System.out.println("### Liked! ###");
            Menu.ok();
        } else {
            System.out.println("### You have already liked this. Whould you like to dislike it? ###\n");
            System.out.println("[1] Yes\n" + "[0] No\n");
            switch (SocialNetwork.inputCheck(1, 0)) {
                case 1:
                    likes.remove(U.getId());
                    System.out.println("\n### Disliked! ###");
                    Menu.ok();
                    break;
                case 0:
                    break;
            }
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
