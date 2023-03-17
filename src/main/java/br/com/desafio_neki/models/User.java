package br.com.desafio_neki.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "userId"
)
@Entity
@Table(name = " \"user\" ")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer userId;

	@Column(name = "login")
    private String login;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
	
	@Column(name = "last_login_date")
	private String lastLoginDate;

	@OneToMany(mappedBy = "user")
  	private Set<UserSkill> user_skills;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<UserSkill> getUser_skills() {
		return user_skills;
	}

	public void setUser_skills(Set<UserSkill> user_skills) {
		this.user_skills = user_skills;
	}

	public String getUserLogin() {
		return login;
	}

	public void setUserLogin(String login) {
		this.login = login;
	}

	public String getUserPassword() {
		return password;
	}

	public void setUserPassword(String password) {
		this.password = password;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}