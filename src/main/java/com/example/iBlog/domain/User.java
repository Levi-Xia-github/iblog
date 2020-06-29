

package com.example.iBlog.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue	
	private  int userNumber;
	@Column(length=50)
	@NotBlank
	@Size(min=2,max=6)
	private String userLoginname;
	@Column(length=50)
	@NotBlank
	@Size(min=2,max=5)
	private String userName;
	//@Column(length=1024)
	@NotBlank
	private String userPassword;
	@Column(length=50)
	@NotBlank
	private String userTel;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	private LocalDate userBirth;
	@Column(length=10)
	@NotBlank
	private String userGender;
	@Column(length=50)
	@NotBlank
	private String userMail;
	@Column(length=20)
	private String userIdentity ="普通用户";
	private boolean userState = true;
	@Column(length=300)
	@NotBlank
	private String userDescribe;
	@Transient
	private int age;
	
	@OneToMany(mappedBy="user")
	private Set<Article> articles = new HashSet<>();
	
	@OneToMany(mappedBy="user")
	private Set<Comment> comments = new HashSet<>();
	
	@OneToMany(mappedBy="replyUser")
	private Set<Comment> comments2 = new HashSet<>();
	

	@OneToMany(mappedBy="user")
	private Set<Label> labels = new HashSet<>();

	/*---------------*/
	@ManyToMany(fetch = FetchType.LAZY)   //多对多
	@JoinTable(					//jointable。维护方加此注释
			name="Fcous",		//name是表名，
              //joincolumns需要将此entity中的什么字段添加到表的什么字段，name是存储在多对多关系表中的字段名，referencedColumnName为此外键
			joinColumns={@JoinColumn(name="fcouserNumber", referencedColumnName="userNumber")},
               //inverseJoinColumns,name字段是关系entity Role的id以role_id存储在关系表tyg_user_role中
			inverseJoinColumns={@JoinColumn(name="befcouserrNumber", referencedColumnName="userNumber")})
	private List<User> BeFcousers;

	@ManyToMany(mappedBy = "BeFcousers")
	private List<User> Fcousers;
	/*---------------*/
	public User() {
	}


	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserLoginname() {
		return userLoginname;
	}

	public void setUserLoginname(String userLoginname) {
		this.userLoginname = userLoginname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public LocalDate getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(LocalDate userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public boolean isUserState() {
		return userState;
	}

	public void setUserState(boolean userState) {
		this.userState = userState;
	}

	public String getUserDescribe() {
		return userDescribe;
	}

	public void setUserDescribe(String userDescribe) {
		this.userDescribe = userDescribe;
	}

	public int getAge() {
		return LocalDate.now().getYear()-this.userBirth.getYear();
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Label> getLabels() {
		return labels;
	}

	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}
	public Set<Comment> getComments2() {
		return comments2;
	}

	public void setComments2(Set<Comment> comments2) {
		this.comments2 = comments2;
	}
	
}
