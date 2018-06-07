package kz.foodmaster.filial.business;


import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String userLogin;
    private String userPass;
    

    public User() {
    	userLogin = "";
    	userPass = "";
    }

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
}
