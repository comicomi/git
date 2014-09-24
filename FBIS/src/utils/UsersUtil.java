package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UsersUtil {

	private static String usersFileName="users.txt";
	private static String dataFolderPath="data";

	private static List<String> users=new ArrayList<>();	
	

	public static boolean cantHelp(String userId) throws IOException{
		File dataFolder=new File(dataFolderPath);
		if(!dataFolder.exists()) dataFolder.mkdir();
		File file=new File(dataFolder+"\\"+usersFileName);
		if(!file.exists() && !file.isDirectory()) file.createNewFile();
		BufferedReader in = new BufferedReader(new FileReader(dataFolderPath+"\\"+usersFileName)); 
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
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(dataFolderPath+"\\"+usersFileName,true)));
		out.println(id);
		out.close();
	}
	
}
