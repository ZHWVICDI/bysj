package com.qinhan.videoblog.dal.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="videoblog_user")
public class User {
	//用户id
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	 //用户名
	@NotBlank(message = "用户名不能为空")
	 @Column(name="username",nullable=false)
	private String username;
	 //密码
	@NotBlank(message = "密码不能为空")
	@Length(min = 6,message = "密码必须在6位以上")
	 @Column(name="password",nullable=false)
	private String password;
	 //用户邮箱
	@Email(message = "请输入一个正确的邮箱")
	 @Column(name="email",nullable=false)
	private String email;
	//用户qq
	 @Column(name="qq")
	 private String qq;
	//用户手机号码
	@Column(name="telephone")
	private String telephone;
	//用户年龄
	@Column(name="age")
	private int age;
	 //性别
	 @Column(name="gender",nullable=false)
	private String gender;
	 //用户状态  FROZEN}NORMAL 不为空，且默认值为normal
	 @Column(name="state",columnDefinition = "varchar(50) default 'NORMAL'")
	private String state;
	 //用户生日
	 @Column(name="birthday")
	 @Temporal(TemporalType.DATE)
	private Date birthday;
	//用户头像url
	 @Column(name="headingUrl")
	private String headingUrl;
	 //用户地址
	 @Column(name="address")
	private String address;
	 //用户注册时间
	 @Column(name="usercreateTime")
	 @CreationTimestamp//由数据库创建时间
	 @Temporal(TemporalType.TIMESTAMP)//指定日期精度
	 private Date registerDate;
	 //用户简介
	 @Column(name = "userInfo",columnDefinition = "varchar(512) default '这个人很懒，什么都没有留下！'")
	 private String userAbout;

	public String getUserAbout() {
		return userAbout;
	}

	public void setUserAbout(String userAbout) {
		this.userAbout = userAbout;
	}

	public User() {
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getHeadingUrl() {
		return headingUrl;
	}
	public void setHeadingUrl(String headingUrl) {
		this.headingUrl = headingUrl;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", qq='" + qq + '\'' +
				", telephone='" + telephone + '\'' +
				", age=" + age +
				", gender='" + gender + '\'' +
				", state='" + state + '\'' +
				", birthday=" + birthday +
				", headingUrl='" + headingUrl + '\'' +
				", address='" + address + '\'' +
				", registerDate=" + registerDate +
				", userAbout='" + userAbout + '\'' +
				'}';
	}
}
