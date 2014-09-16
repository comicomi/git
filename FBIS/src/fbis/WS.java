package fbis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Path("zahtev")
public class WS {
	//@GET
	//  @Produces(MediaType.TEXT_PLAIN)
	//http://localhost:8080/FBIS/rest/zahtev ----> url za poziv servisa!!!
	public String sayPlainTextHello() {
	    return "{'text': 'Hello Jersey'}";
	}
	  // This method is called if XML is request
	
	
}
