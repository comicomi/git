package fb.predict;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weka.Classification;
import domen.UserData;
import facebook4j.Album;
import facebook4j.BatchRequest;
import facebook4j.BatchRequests;
import facebook4j.BatchResponse;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PagableList;
import facebook4j.Photo;
import facebook4j.ResponseList;
import facebook4j.Tag;
import facebook4j.User;
import facebook4j.internal.http.RequestMethod;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.json.DataObjectFactory;
import fb.utils.FbUtils;

/**
 * Servlet implementation class FbPrediction
 */
public class FbPrediction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FbPrediction() {
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
			UserData userData = new UserData();
			BatchRequests<BatchRequest> batch = new BatchRequests<>();
			batch.add(new BatchRequest(RequestMethod.GET, "v2.0/me"));
			batch.add(new BatchRequest(RequestMethod.GET, "v2.0/me/friends"));
			facebook.executeBatch(batch);

			List<BatchResponse> results = facebook.executeBatch(batch);

			BatchResponse result1 = results.get(0);
			User me = DataObjectFactory.createUser(result1.asString());
			userData.setId(me.getId());
			userData.setAge(FbUtils.getUserAge(me.getBirthday()));
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
					Photo currentProfilePicture = photos.get(0);
					userData.setDateTimeCreated(currentProfilePicture
							.getCreatedTime());
					userData.setDateTimeUpdated(currentProfilePicture
							.getUpdatedTime());
					PagableList<Tag> tags = currentProfilePicture.getTags();
					userData.setTagsNumber(FbUtils.getTagsData(facebook, tags));
					double totalLikes = 0;
					double[] array = FbUtils.getPhotoData(facebook, photos,
							totalLikes);
					userData.setAverageNumberOfLikes(array[0] / array[1]);
					break;
				}
			}

			Classification classification = new Classification(null);
			classification.addInstance(userData, false);
			double classificationResult = classification.classify();
			if (classificationResult == 0)
				response.sendRedirect(facebook
						.getOAuthAuthorizationURL("https://isproj.weebly.com/yes.html"));
			else
				response.sendRedirect(facebook
						.getOAuthAuthorizationURL("https://isproj.weebly.com/no.html"));

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
