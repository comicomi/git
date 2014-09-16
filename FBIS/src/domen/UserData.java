package domen;

import java.util.Calendar;
import java.util.Date;

public class UserData {

	private String id;
	private int friendsNumber;
	private double averageNumberOfLikes;
	private int tagsNumber;
	private Date dateTimeCreated;
	private Date dateTimeUpdated;
	private String gender;
	private int age;
	private int likesNumber;
	
	public UserData() {
		// TODO Auto-generated constructor stub		
	}

	

	public UserData(String id, int friendsNumber, double averageNumberOfLikes,
			int tagsNumber, Date dateTimeCreated, Date dateTimeUpdated,
			String gender, int age, int likesNumber) {
		super();
		this.id = id;
		this.friendsNumber = friendsNumber;
		this.averageNumberOfLikes = averageNumberOfLikes;
		this.tagsNumber = tagsNumber;
		this.dateTimeCreated = dateTimeCreated;
		this.dateTimeUpdated = dateTimeUpdated;
		this.gender = gender;
		this.age = age;
		this.likesNumber = likesNumber;
	}



	public int getFriendsNumber() {
		return friendsNumber;
	}

	public void setFriendsNumber(int friendsNumber) {
		this.friendsNumber = friendsNumber;
	}

	public double getAverageNumberOfLikes() {
		return averageNumberOfLikes;
	}

	public void setAverageNumberOfLikes(double averageNumberOfLikes) {
		this.averageNumberOfLikes = averageNumberOfLikes;
	}

	public int getTagsNumber() {
		return tagsNumber;
	}

	public void setTagsNumber(int tagsNumber) {
		this.tagsNumber = tagsNumber;
	}

	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}

	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}

	public Date getDateTimeUpdated() {
		return dateTimeUpdated;
	}

	public void setDateTimeUpdated(Date dateTimeUpdated) {
		this.dateTimeUpdated = dateTimeUpdated;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLikesNumber() {
		return likesNumber;
	}

	public void setLikesNumber(int likesNumber) {
		this.likesNumber = likesNumber;
	}

	public String isNew() {
		// TODO Auto-generated method stub
		// if(dateTimeCreated.compareTo(anotherDate))
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTimeCreated);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(dateTimeUpdated);
		if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == calendar2
						.get(Calendar.MONTH)
				&& calendar.get(Calendar.DAY_OF_MONTH) == calendar2
						.get(Calendar.DAY_OF_MONTH))
			return "yes";
		return "no";
	}

	public String getAgeRange() {
		// TODO Auto-generated method stub
		if (age <= 19)
			return "teenagers";
		if (age > 19 && age <= 25)
			return "youngAdults";
		if (age > 25 && age <= 34)
			return "adults";
		if (age > 34)
			return "seriousAdults";
		return "error";
	}

	public String getClassValue() {
		// TODO Auto-generated method stub
		if (likesNumber >= friendsNumber / 10)
			return "noticed";
		return "unnoticed";
	}

	public String getTime() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTimeCreated);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(hour>5 && hour<11) return "morning";
		else if(hour>=11 && hour<16) return "midday";
		else if(hour>=16 && hour<21) return "afternoon";
		else if(hour>=21 && hour<2) return "night";
		else return "lateNight";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
