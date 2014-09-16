package prva;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import weka.Classification;
import domen.UserData;
import facebook4j.Album;
import facebook4j.BatchRequest;
import facebook4j.BatchRequests;
import facebook4j.BatchResponse;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.Photo;
import facebook4j.ResponseList;
import facebook4j.Tag;
import facebook4j.User;
import facebook4j.internal.http.RequestMethod;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.json.DataObjectFactory;

/**
 * Servlet implementation class FbApi
 */
@Path("fbapi")
public class FbApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FbApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Facebook facebook = (Facebook) request.getSession().getAttribute(
				"facebook");
		try {
			// Get facebook posts
			UserData userData = new UserData();
			BatchRequests<BatchRequest> batch = new BatchRequests<>();
			batch.add(new BatchRequest(RequestMethod.GET, "v2.0/me"));
			
			System.out.println(System.getProperty("user.dir"));
			
			batch.add(new BatchRequest(RequestMethod.GET, "v2.0/me/friends"));
			facebook.executeBatch(batch);
			List<BatchResponse> results = facebook.executeBatch(batch);

			BatchResponse result1 = results.get(0);
			User me = DataObjectFactory.createUser(result1.asString());
			
			if(UsersUtil.cantHelp(me.getId())){
				response.sendRedirect(facebook.getOAuthAuthorizationURL("https://isproj.weebly.com/thanks2.html"));	
				return ;
			}
			
			userData.setId(me.getId());
			userData.setAge(getUserAge(me.getBirthday()));
			userData.setGender(me.getGender());	
			
			BatchResponse result2 = results.get(1);
			JSONObject jsonObject = result2.asJSONObject();
			JSONObject json = (JSONObject) jsonObject.get("summary");
			int friendsNumber = (int) json.get("total_count");
			
			userData.setFriendsNumber(friendsNumber);					
						
			ResponseList<Album> albums = facebook.getAlbums();
			for (Album album : albums) {
				if ("Profile Pictures".equalsIgnoreCase(album.getName())) {
					PagableList<Photo> photos = facebook.getAlbumPhotos(album
							.getId());
					Photo currentProfilePicture=photos.get(0);
					userData.setDateTimeCreated(currentProfilePicture.getCreatedTime());
					userData.setDateTimeUpdated(currentProfilePicture.getUpdatedTime());
					userData.setLikesNumber(getLikesData(facebook, currentProfilePicture.getLikes()));
					PagableList<Tag> tags = currentProfilePicture.getTags();
					userData.setTagsNumber(getTagsData(facebook, tags));
					double totalLikes=0;
					double[] array=getPhotoData(facebook, photos,totalLikes);
					userData.setAverageNumberOfLikes(array[0]/array[1]);
					break;
				}
			}
			
			Classification classification=new Classification();
			classification.addInstance(userData);
			UsersUtil.addUser(userData.getId());
			response.sendRedirect(facebook.getOAuthAuthorizationURL("https://isproj.weebly.com/thanks.html"));	

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getUserAge(String birthday) throws ParseException {
		// TODO Auto-generated method stub
		Date userBirthday = new SimpleDateFormat("MM/dd/yyyy").parse(birthday);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userBirthday);
		Date today = new Date();
		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(today);
		return todayCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private double[] getPhotoData(Facebook facebook, PagableList<Photo> photos, double totalLikes)
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

	private int getTagsData(Facebook facebook, PagableList<Tag> tags)
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

	private int getLikesData(Facebook facebook, PagableList<Like> likes)
			throws FacebookException {
		int size = likes.size();
		if (null != likes.getPaging() && null != likes.getPaging().getNext()) {
			Paging<Like> paging = likes.getPaging();
			PagableList<Like> likesN = facebook.fetchNext(paging);
			size += getLikesData(facebook, likesN);
		}
		return size;
	}

}
