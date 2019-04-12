package Main;

import java.io.IOException;
import User.Utilisateur;

public class Main {
	public static int idUser;
	public static Utilisateur userContext;

	public static void main(String[] args) throws IOException {
		fillUser();
		FXMLLoder.launch(FXMLLoder.class);
	}


	public static void fillUser() throws IOException{
		userContext = new Utilisateur(1,"bob");
	}

}
