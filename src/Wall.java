import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wall implements Serializable {

    private final List<Post> posts = new ArrayList<Post>();

    //Shows the posts on this wall
    public void showPosts() {
        if (posts.size() == 0) {
            System.out.println("### There are no posts yet. ###");
        } else {
            System.out.println("### There are " + posts.size() + " posts. ###\n");
            System.out.println("### Select a post. ###");
        }
        for (Post P : posts) {
            System.out.println("\n[" + (posts.indexOf(P) + 1) + "] " + P);
        }
    }

    public void writePost(User U) {
        posts.add(new Post(U.getId(), SocialNetwork.writeText()));
        System.out.println("\n### Posted! ###");
    }

    public List<Post> getPosts() {
        return posts;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
