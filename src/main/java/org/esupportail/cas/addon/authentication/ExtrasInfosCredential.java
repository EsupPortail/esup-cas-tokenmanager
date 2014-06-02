package org.esupportail.cas.addon.authentication;

import org.jasig.cas.authentication.Credential;

/**
 * The Interface ExtrasInfosCredential.
 */
public interface ExtrasInfosCredential extends Credential {

	/** The authentication attribute user agent. */
	String AUTHENTICATION_ATTRIBUTE_USER_AGENT = "userAgent";

	/** The authentication attribute ip address. */
	String AUTHENTICATION_ATTRIBUTE_IP_ADDRESS = "ipAddress";

	/**
	 * Gets the user agent.
	 *
	 * @return the user agent
	 */
	String getUserAgent();

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	String getIpAddress();

	/**
	 * Sets the user agent.
	 *
	 * @param userAgent the new user agent
	 */
	void setUserAgent(String userAgent);

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	void setIpAddress(String ipAddress);

}
