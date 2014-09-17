package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UsersUtil {

	private static String usersFileName=System.getProperty("user.dir")+"\\data\\"+"users.txt";

	private static List<String> users=new ArrayList<>();	
	

	public static boolean cantHelp(String userId) throws IOException{		
		BufferedReader in = new BufferedReader(new FileReader(usersFileName)); 
		boolean end=false;
		while (!end) {
			String id=in.readLine();
			if(id==null) end=true;
			else users.add(id.trim());			
		}
		in.close();
		return users.contains(userId);
	}


	public static void addUser(String id) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(usersFileName,true)));
		out.println(id);
		out.close();
	}
	 
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}
}
