package weka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.FacebookClient;
import com.restfb.types.Location;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
import com.restfb.types.User.Education;

import domen.UserData;

public class Kontroler {
	List<UserData> podaci;
	static Kontroler objekat;
	
	private Kontroler() {
		podaci=new ArrayList<>();
	}
	
	public static Kontroler vratiObjekatKontrolera(){
		if(objekat==null) objekat=new Kontroler();
		return objekat;
	}

	public void uzmiPodatkeZaJednuInstancu(
			FacebookClient facebookClient, User userA, User userB) {
		// TODO Auto-generated method stub
		System.out.println(userA+ " *****  "+userB);
		UserData friendAData= uzmiPodatkeZaPrijetelja(facebookClient, userA);
		UserData friendBData=uzmiPodatkeZaPrijetelja(facebookClient, userB);
		userA.getPolitical();
		userA.getLocale();
	}

	private UserData uzmiPodatkeZaPrijetelja(FacebookClient facebookClient, User user) {
		// TODO Auto-generated method stub
		String id=user.getId();
		Date bday = user.getBirthdayAsDate();
		String relationship=user.getRelationshipStatus();
		String fName=user.getFirstName();
		String lName=user.getLastName();
		String gender=user.getGender();
		Location location=nadjiLokaciju(facebookClient, user.getLocation());
		List<Education> educations=user.getEducation();
		Location hometown=nadjiLokaciju(facebookClient,user.getHometown());
	//	return new UserData(id,fName,lName,gender,bday,educations,location,hometown,relationship);
		return null;
	}

	private Location nadjiLokaciju(FacebookClient facebookClient, NamedFacebookType location) {
		// TODO Auto-generated method stub
		return facebookClient.fetchObject(location.getId(), Location.class,null);		
	}
	
}
