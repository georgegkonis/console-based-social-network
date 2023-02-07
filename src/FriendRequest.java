import java.io.Serializable;

public class FriendRequest implements Serializable {

	private final String timestamp;
	private final int senderId;
	private final int receiverId;
	
	//Constructor
	public FriendRequest(int senderID, int receiverId){
		timestamp = SocialNetwork.getTimestamp();
		this.senderId = senderID;
		this.receiverId = receiverId;
	}

	public Integer getSenderId() {return this.senderId;}

	public Integer getReceiverId() {return this.receiverId;}

	@Override
	public String toString() {
		return "(" + timestamp + ") " + SocialNetwork.findUserFromID(getSenderId()).getFirstName() + " " + SocialNetwork.findUserFromID(getSenderId()).getLastName();
	}
}
