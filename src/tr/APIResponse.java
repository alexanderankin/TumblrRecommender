package tr;

public class APIResponse {
	private String content = "";
	private String action = "";
	private String blogInfo = "";
	private String posts = "";

	// private String[] postStrings = new String[20];

	public APIResponse(String a, String b) {
		setContent(a);
		setAction(b);
		System.out.println(blogInfo);
		substringResponse(a, b);
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
					separateposts();
				}
			}

			break;
		default:
			break;
		}

	}

	private void separateposts() {
		int j = 0; // count posts, keep track of which element of the array to add to.
		for (int i = 0; i < posts.length(); i++) {

		}
		System.out.println("hello " + j);
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
