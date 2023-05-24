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
	public static final String TUMBLR = ".tumblr.com";

	public static void main(String[] args) throws IOException {

		// Read in Data from User
		String user = "";

		String url = "";
		String apiResponse = "";

		// Format the url to request, and Call API
		url = apiCallFormatter(user, "info");
		apiResponse = apiCall(url);

		// Store response
		ArrayList<APIResponse> responses = new ArrayList<>();
		//responses.add(new APIResponse(apiResponse, "blog")); //  dont need this temprorarily
		// System.out.println(responses.get(0).getAction()); // prints "blog." 

		// Get posts of user
		url = apiCallFormatter(user, "posts");
		// System.out.println(url);
		// System.exit(0);
		url = "http://api.tumblr.com/v2/blog/" + user + TUMBLR 
				+"/posts?api_key=" + getAPIFromJSON()[0] + "&reblog_info=true";
		apiResponse = apiCall(url);

		responses.add(new APIResponse(apiResponse, "posts"));

	}

	public static String apiCallFormatter(String user, String action) throws IOException {
		String api = "?api_key=" + getAPIFromJSON()[0];
		String base = "http://api.tumblr.com/v2/blog";
		return base + "/" + user + TUMBLR + "/" + action + api;

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

	public static String apiCall(String url) {
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
