package tr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class logic {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		// Read in Data from User, define other useful things.
		// String user = ""+".tumblr.com";
		String user = "" + ".tumblr.com";

		String api = "info?api_key=" + getAPIFromJSON()[0];
		String base = "http://api.tumblr.com/v2/blog";// .tumblr.com/info?api_key={key}"
		String apiResponse = "";
		String url = base + "/" + user + "/" + api;
		
		//API Call
		apiResponse = name(url);
		
		//Organize responses
		ArrayList<APIResponse> responses = new ArrayList<>();
		responses.add(new APIResponse(apiResponse,"blog"));
		System.out.println(responses.get(0).getAction());

	}

	public static String[] getAPIFromJSON() throws IOException {
		Scanner s = new Scanner(new FileReader(""));
		String badJsonStr = "";
		String jsonStr = "";
		String[] result = { "", "" };
		while (s.hasNext()) {
			badJsonStr += s.next();
		}

		s.close();

		for (int i = 0; i < badJsonStr.length(); i++) {
			if (badJsonStr.charAt(i) != '\n' && badJsonStr.charAt(i) != ' ') {
				jsonStr += badJsonStr.charAt(i);
			}
		}

		// should be parsing json, not getting substrings.
		result[0] = jsonStr.substring(jsonStr.length() - 119, jsonStr.length() - 69);
		result[1] = jsonStr.substring(jsonStr.length() - 53, jsonStr.length() - 3);

		return result;
	}
	public static String name(String url) {
		String result = "";
		try {
			URL myUrl = new URL(url);
			URLConnection yc = myUrl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
			}
		} catch (Exception e) {
			System.out.println("something went wrong!");
			System.exit(0);
		}
		return result;
	}
}

