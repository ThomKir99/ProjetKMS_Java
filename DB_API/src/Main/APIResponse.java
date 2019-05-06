package Main;

public class APIResponse {

	public Boolean successful;
	public String ErrorMessage;

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
	public void setSeccessful(Boolean state){
		successful = state ;
	}

	public void setErrorMessage(String Message){
		ErrorMessage = Message;
	}

}
