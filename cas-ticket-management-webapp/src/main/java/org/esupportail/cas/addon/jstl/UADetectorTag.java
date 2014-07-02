package org.esupportail.cas.addon.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * The Class UADetectorTag.
 */
public class UADetectorTag extends TagSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3744500480257123384L;

	/** The user agent. */
	private String userAgent;

	/**
	 * This tag will parse the given user agent and then print the browser and the operating system
	 */
	@Override
	public int doStartTag() throws JspException {

		UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
		ReadableUserAgent agent = parser.parse(this.userAgent);

		String uaOS = agent.getOperatingSystem().getName();
		String uaBrowser = agent.getName();

		try {

			JspWriter out = this.pageContext.getOut();

			if(uaOS.length() <= 0 || uaBrowser.length() <= 0) {
				out.print("Unknown");
			} else {
				out.print(uaBrowser + ", " + uaOS);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

	/**
	 * Gets the user agent.
	 *
	 * @return the user agent
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * Sets the user agent.
	 *
	 * @param userAgent the new user agent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}