package br.com.desafio_neki.dtos;

public class CredenciaisLoginDTO {
	private String userLogin;
	private String userPassword;

	public CredenciaisLoginDTO(String userLogin, String userPassword) {
		super();
		this.userLogin = userLogin;
		this.userPassword = userPassword;
	}

	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
