package tr;

public class APIResponse {
	private String content = "";
	private String action = "";
	private String blogInfo = "";
	private String posts = "";

	private String[] postStrings = new String[20]; // 20 matches the number in the call, should make final var
	private String[] rebloggedFrom = new String[postStrings.length];
	
	public APIResponse(String a, String b) {
		setContent(a);
		setAction(b);
		// System.out.println(blogInfo); supposed to use "blogInfo".
		substringResponse(a, b);
		
		fillRebloggedFrom();
	}

	public void substringResponse(String a, String b) {

		int parenCount = 0;
		int endLoc = 0;
		int srtLoc;
		switch (b) {
		case "blog":
			srtLoc = a.indexOf(b) + b.length() + 2;
			searchrestofstring: for (int i = srtLoc; i < a.length(); i++) {
				switch (a.charAt(i)) {
				case '{':
					parenCount++;
					break;
				case '}':
					parenCount--;
					break;
				default:
					break;
				}
				if (parenCount == 0) {
					endLoc = i + 1;
					break searchrestofstring;
				}
			}
			blogInfo = a.substring(srtLoc, endLoc);
			break;
		case "posts":
			srtLoc = a.indexOf(b, a.indexOf(b) + 1) + b.length() + 2;
			parenCount = 0;
			endLoc = 0;
			for (int i = srtLoc; i < a.length() - 4; i++) {
				switch (a.charAt(i)) {
				case '[':
					parenCount++;
					break;
				case ']':
					parenCount--;
					break;
				default:
					break;
				}
				if (parenCount == 0) {
					endLoc = i + 1;
					posts = a.substring(srtLoc, endLoc);
					// System.out.println(posts);
					separateposts();
					break;
				}
			}

			break;
		default:
			break;
		}

	}

	private void separateposts() {
		int p = 0; // count posts, keep track of which element of the array to add to.
		int j = 0; // track depth of {}
		
		int startLoc = 0;
		
		for (int i = 1; i < posts.length(); i++) {
			switch (posts.charAt(i)) {
			case '{':
				if (j == 0) {
					startLoc = i;
				}
				j++;
				break;
			case '}':
				j--;
				if (j == 0) {
					i++;
				}
				break;
			
			default:
				break;
			}
			if (j == 0) {
				try {
					postStrings[p] = posts.substring(startLoc, i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("exception");
					System.exit(0);
				}
				p++;
			}
		}
		// System.out.println("hello " + j);
		for (int i = 0; i < postStrings.length; i++) {
			System.out.println(postStrings[i]);
			
		}
		
	}

	private void fillRebloggedFrom() {
		int a = 0; // find '\"reblogged_from_name\": ' in string of post
		String postSrc = "\"reblogged_from_name\": "; 
		for (int i = 0; i < postStrings.length; i++) {
			try {
				a = postStrings[i].indexOf(postSrc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("you fucked up");
				System.exit(0);
			}
			for (int j = a + postStrings.length; j < postStrings[i].length(); j++) {
				System.out.println(postStrings[i].charAt(j));
				System.exit(0);
			}
		}
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
