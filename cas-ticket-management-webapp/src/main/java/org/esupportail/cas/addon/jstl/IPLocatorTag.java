package org.esupportail.cas.addon.jstl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.json.JSONObject;

/**
 * The Class IPLocatorTag.
 */
public class IPLocatorTag extends TagSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4100059326136948572L;

	/** The ip address. */
	private String ipAddress;

	/**
	 * This tag will geocode the given IP address based on http://freegeoip.net REST API
	 * If the given IP address does not match anything then the tag will print the IP address
	 */
	@Override
	public int doStartTag() throws JspException {

		JspWriter out = this.pageContext.getOut();

		try {
			URL url = new URL("http://freegeoip.net/json/" + this.ipAddress);
			HttpURLConnection con = this.sendGETRequest(url);
			// Handle the response
			String response = responseReader(con.getInputStream());

			response = response.replaceAll("\"", "\\\"");

			JSONObject responseJson = new JSONObject(response);

			if(responseJson.isNull("city")) {
				out.print("Unknown");
			} else if (responseJson.getString("country_code").equals("RD")) {
				out.print(this.ipAddress);
			} else {
				out.print(responseJson.getString("city") + ", " + responseJson.getString("country_code"));
			}

		} catch (Exception e) {
			try {
				out.print("Unknown");
			} catch (IOException ex1) { }
			return SKIP_BODY;
		}


		return SKIP_BODY;
	}

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Send get request.
	 *
	 * @param url the url
	 * @return the http url connection
	 * @throws Exception the exception
	 */
	private HttpURLConnection sendGETRequest(URL url) throws Exception {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		return con;
	}

	/**
	 * Response reader.
	 *
	 * @param inputStream the input stream
	 * @return the string
	 * @throws Exception the exception
	 */
	private String responseReader(InputStream inputStream) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

		String inputLine;
		StringBuilder response = new StringBuilder();

		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

}