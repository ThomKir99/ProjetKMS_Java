package Main;

import java.util.ArrayList;
import java.util.List;
import User.Utilisateur;

public class Main {
	public static int idUser;
	public static List<Utilisateur> userList;

	public static void main(String[] args) {
		fillUserList();
		FXMLLoder.launch(FXMLLoder.class);
	}

	public static void fillUserList(){
		userList = new ArrayList<Utilisateur>();
		userList.add(new Utilisateur(1));
		userList.add(new Utilisateur(2));
	}

}
