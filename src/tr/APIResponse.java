package tr;

public class APIResponse{
	private String content = "";
	private String action = "";
	private String thisisapartofthejsonineedtosleep = "";
	
	public APIResponse (String a, String b){
		setContent(a);
		setAction(b);
		thisisapartofthejsonineedtosleep = getResponse(content);
		System.out.println(thisisapartofthejsonineedtosleep);
		
	}
	public String getResponse (String a){
		int srtLoc = a.indexOf("blog")+"blog".length()+2;
		int parenCount = 0;
		int endLoc = 0;
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
				endLoc = i+1;
				break searchrestofstring;
			}
		}
		return a.substring(srtLoc,endLoc);
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
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
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
