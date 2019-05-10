package Main;

import java.sql.ResultSet;

public class APIResponse {

	public Boolean successful;
	public String ErrorMessage;
	public Boolean groupCompletion;
	public APIResponse() {
		successful = false;
		ErrorMessage = "";

	}

	public Boolean getSuccessful(){
		return successful;
	}

	public String getErrorMessage(){
		return ErrorMessage;
	}

	public boolean getNoGroupAvantDrag(){
		return groupCompletion;
	}

	public void setNoGroupAvantDrag(boolean state){
		groupCompletion = state ;
	}

	public void setSeccessful(Boolean state){
		successful = state ;
	}

	public void setErrorMessage(String Message){
		ErrorMessage = Message;
	}



}
