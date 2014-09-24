package fb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.Photo;
import facebook4j.Tag;

public class FbUtils {

	public static double[] getPhotoData(Facebook facebook, PagableList<Photo> photos, double totalLikes)
			throws FacebookException {
		double likesNumber=totalLikes;
		double size = photos.size();
		System.out.println("*****PHOTO*****");
		for (Photo photo : photos) {
			if(photo.equals(photos.get(0))) continue;
			PagableList<Like> likes = photo.getLikes();
			likesNumber+= getLikesData(facebook, likes);
		}
		if (null != photos.getPaging().getNext()) {
			Paging<Photo> paging = photos.getPaging();
			PagableList<Photo> photosN = facebook.fetchNext(paging);
			double[] array= getPhotoData(facebook, photosN,totalLikes);
			array[0]+=likesNumber;
			array[1]+=size;
		}
		return new double[]{likesNumber,size};
	}

	public static int getTagsData(Facebook facebook, PagableList<Tag> tags)
			throws FacebookException {
		// TODO Auto-generated method stub
		int size = tags.size();
		if (null != tags.getPaging() && null != tags.getPaging().getNext()) {
			Paging<Tag> paging = tags.getPaging();
			PagableList<Tag> tagsN = facebook.fetchNext(paging);
			size += getTagsData(facebook, tagsN);
		}
		return size;
	}

	public static int getLikesData(Facebook facebook, PagableList<Like> likes)
			throws FacebookException {
		int size = likes.size();
		if (null != likes.getPaging() && null != likes.getPaging().getNext()) {
			Paging<Like> paging = likes.getPaging();
			PagableList<Like> likesN = facebook.fetchNext(paging);
			size += getLikesData(facebook, likesN);
		}
		return size;
	}
	
	public static int getUserAge(String birthday) throws ParseException {
		// TODO Auto-generated method stub
		Date userBirthday = new SimpleDateFormat("MM/dd/yyyy").parse(birthday);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userBirthday);
		Date today = new Date();
		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(today);
		return todayCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
	}

}
