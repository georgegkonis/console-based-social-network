public class SocialNetworkApp {
	
	//Methods
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//Main
	public static void main(String[] args) {
		SocialNetwork.readUsersFromFile();
		if(SocialNetwork.users.size() == 0) {
			SocialNetwork.count = 0;
		}
		else {
			SocialNetwork.count = SocialNetwork.users.get(SocialNetwork.users.size() - 1).getId() + 1;
		}
		System.out.println("\n###### Number of Users: " + SocialNetwork.users.size() + " ######\n\n");
    	
		Menu.helloPage();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
