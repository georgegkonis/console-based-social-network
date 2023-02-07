import java.io.Serializable;

public class Comment extends Post implements Serializable {

    //Constructor
    Comment(int senderID, String text) {
        super(senderID, text);
    }

    //Methods

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Overrides java.lang.Object.toString
    public String toString() {
        return "(" + timestamp + ") " + SocialNetwork.findUserFromID(senderID) + ": " + text + "  -{Likes: " + likes.size() + "}-";
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
