package org.esupportail.cas.addon.authentication;

import org.jasig.cas.authentication.RememberMeUsernamePasswordCredential;

/**
 * The Class ExtrasInfosRememberMeUsernamePasswordCredential.
 */
public class ExtrasInfosRememberMeUsernamePasswordCredential extends RememberMeUsernamePasswordCredential implements ExtrasInfosCredential {

	/** The user agent. */
	private String userAgent;

	/** The ip address. */
	private String ipAddress;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6594051428434005804L;

	/* (non-Javadoc)
	 * @see org.esupportail.cas.addon.authentication.ExtrasInfosCredential#getUserAgent()
	 */
	@Override
	public String getUserAgent() {
		return this.userAgent;
	}

	/* (non-Javadoc)
	 * @see org.esupportail.cas.addon.authentication.ExtrasInfosCredential#setUserAgent(java.lang.String)
	 */
	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/* (non-Javadoc)
	 * @see org.esupportail.cas.addon.authentication.ExtrasInfosCredential#getIpAddress()
	 */
	@Override
	public String getIpAddress() {
		return this.ipAddress;
	}

	/* (non-Javadoc)
	 * @see org.esupportail.cas.addon.authentication.ExtrasInfosCredential#setIpAddress(java.lang.String)
	 */
	@Override
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
