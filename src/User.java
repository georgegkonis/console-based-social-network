import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {


	private final int id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private boolean firstLogin;
	private final Wall wall;
	private final List<Integer> friendList;
	private final List<FriendRequest> friendRequestList;

	//Constructor
	public User(String username, String password, String email){
		this.username = username;
		this.password = password;
		this.email = email;
		firstName = "Unnamed";
		lastName = "Unnamed";
		firstLogin = true;
		id = SocialNetwork.count++;
		wall = new Wall();
		friendList = new ArrayList<>();
		friendRequestList = new ArrayList<>();
	}
	
	// Returns the username
	public String getUsername() {return this.username;}
	
	// Returns the password
	public String getPassword() {return this.password;}
	
	// Returns the email
	public String getEmail() {return this.email;}
	
	// Returns the first name
	public String getFirstName() {return this.firstName;}
	
	// Returns the last name
	public String getLastName() {return this.lastName;}
	
	// Returns whether this is the first login
	public Boolean isFirstLogin() {return this.firstLogin;}
	
	// Returns the id
	public int getId() {return this.id;}
	
	// Returns the wall
	public Wall getWall() {return this.wall;}

	public List<Integer> getFriendList() {
		return friendList;
	}

	public List<FriendRequest> getFriendRequestList() {
		return friendRequestList;
	}

	// Overrides java.lang.Object.toString
	@Override
	public String toString() {return firstName + " " + lastName;}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Sets the username
	public void setUsername(String username ) {this.username = username;}
	
	// Sets the password
	public void setPassword(String password ) {this.password = password;}
	
	// Sets the email
	public void setEmail(String email ) {this.email = email;}
	
	// Sets the first name
	public void setFirstName(String firstName) {this.firstName = firstName;}
	
	// Sets the last name
	public void setLastName(String lastName) {this.lastName = lastName;}
	
	// Sets the isFirstLogin variable to false
	public void istFirstLogin() {this.firstLogin = false;}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Checks whether the user U is this user
	public Boolean isThisUser(User user) {
		return getId() == user.getId();
	}
	
	// Checks whether the user U is on this user's friend list
	public Boolean isFriend(User U) {
		return friendList.contains(U.getId());
	}
	
	// Checks whether this user has sent a friend request to the user U
	public Boolean hasSentFriendRequest(User user) {
		return user.getFriendRequestList().stream()
				.anyMatch(friendRequest -> friendRequest.getSenderId() == getId());
	}
	
	// Checks whether this user has received a friend request from the user U
	public Boolean hasReceivedFriendRequest(User user) {
		return friendRequestList.stream()
				.anyMatch(friendRequest -> friendRequest.getSenderId() == user.getId());
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Shows a list of this user's friends
	public void showFriends() {
		System.out.println("### You have " + friendList.size() + " friends. ###");
		if(friendList.size() > 0) {
			System.out.println("\n### Select a friend. ###\n");
		}
		int i = 0;			
		for(User U : SocialNetwork.users) {
			if(isFriend(U)){					
				System.out.println("[" + ++i + "] " + U);	
								
			}			
		}
	}
	
	// Shows a list of this user's non-friends (basically every user that this user can send a friend request to) and saves them on a list (temporarily)
	public void showNonFriends() {
		SocialNetwork.nonFriendList.clear();
		int i = 0;
		for(User U : SocialNetwork.users) {
			if(!isFriend(U) && !isThisUser(U)){
				SocialNetwork.nonFriendList.add(U.getId());
				System.out.print("[" + ++i + "] " + U);
				if(hasSentFriendRequest(U)) {
					System.out.println(" (Sent) ");
				}
				else if(hasReceivedFriendRequest(U)) {
					System.out.println(" (Pending)");
				}
				else {
					System.out.println("");
				}
			}
		}
		if(i == 0) {
			System.out.println("### No suitable users found. ###");
		}
	}
	
	// Shows a list of this user's friend requests
	public void showFriendRequests() {
		System.out.println("### You have " + friendRequestList.size() + " friend requests. ###");
		if(friendRequestList.size() > 0) {
			System.out.println("\n### Select a friend request. ###\n");
		}
		for(FriendRequest FR : friendRequestList) {
			System.out.println("[" + (friendRequestList.indexOf(FR) + 1) + "] "  + FR);
		}
	}
	
	// Shows a list of the common friends of this user and user U
	public void showCommonFriends(User U) {
		List<Integer> commonFriends = new ArrayList<>(friendList);
		List<Integer> UfriendList = new ArrayList<>(U.friendList);
		commonFriends.retainAll(UfriendList);
		System.out.println("\nYou have " + commonFriends.size() + " common friends with " + U + ". ###");
		if(!(commonFriends.size() == 0)) {
			System.out.println("");
			for(Integer I : commonFriends) {
				System.out.println(SocialNetwork.findUserFromID(I));
			}
		}
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// This user sends a friend request to user U
	public void sendFriendrequest(User U) {
		if(hasSentFriendRequest(U)) {
			System.out.println("\n### You have already sent a friend request to " + U + ". ###");
			}
		else if(hasReceivedFriendRequest(U)) {
			System.out.println("\n### You already have a pending friend request from " + U + ". ###");
		}
		else {														
			U.friendRequestList.add(new FriendRequest(getId(), U.getId()));
			System.out.println("\n### Friend request sent to " + U + ". ###");
		}
	}
	
	// This user accepts or rejects a friend request from user U
	public void receiveFriendRequest(FriendRequest FR) {
		System.out.println("\n### Do you want to accept the friend request from " + SocialNetwork.findUserFromID(FR.getSenderId()) + "? ###\n");
		System.out.println("[1] Yes\n" + "[0] No\n");
		switch (SocialNetwork.inputCheck(1, 0)) {
			case 1 -> {
				friendList.add(FR.getSenderId());
				SocialNetwork.findUserFromID(FR.getSenderId()).friendList.add(getId());
				System.out.println("\n### " + SocialNetwork.findUserFromID(FR.getSenderId()) + " is now your friend ###");
			}
			case 0 -> System.out.println("\n### " + SocialNetwork.findUserFromID(FR.getSenderId()) + " is crying alone ###");
		}
		friendRequestList.remove(FR);
	}
	
	// This user removes user U (friend) from the friend list of this user
	public void removeFriend(User user) {
			this.friendList.remove(user.getId());
			user.friendList.remove(this.getId());
			System.out.println("\n### You have unfriended " + user.getFirstName() + ". ###");
	}
}