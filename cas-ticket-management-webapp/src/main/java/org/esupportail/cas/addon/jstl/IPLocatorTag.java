package org.esupportail.cas.addon.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.esupportail.cas.addon.model.IPInformation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

/**
 * The Class IPLocatorTag.
 */
public class IPLocatorTag extends TagSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4100059326136948572L;

	/** The ip address. */
	private String ipAddress;

	private final String API_GEOIP_URL = "http://freegeoip.net/json/{ipAddress}";

	/**
	 * This tag will geocode the given IP address based on the REST API
	 * If the given IP address does not match anything then the tag will print the IP address
	 */
	@Override
	public int doStartTag() throws JspException {

		JspWriter out = this.pageContext.getOut();

		try {

			IPInformation ipInfo = this.getIPInformation(this.ipAddress);

			// FreeGeoIP returns RD if IP address is private (ex : 127.0.0.1, 192.168.1.1, ...)
			if(ipInfo.getCountryCode().equalsIgnoreCase("RD")) {
				out.print(this.ipAddress);
			} else {
				String location = ipInfo.getCity() + ", " + ipInfo.getCountryCode();
				out.print(location);
			}
		} catch (Exception e) {
			// Catch user's network issue
			try {
				e.printStackTrace();
				out.print("Unknown");
			} catch (IOException ex1) { }
		}
		return SKIP_BODY;
	}

	@Cacheable("ip_cache")
	private IPInformation getIPInformation(String ipAddress) {
		RestTemplate restTemplate = new RestTemplate();
		IPInformation ipInfo = restTemplate.getForObject(this.API_GEOIP_URL, IPInformation.class, ipAddress);
		return ipInfo;
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

}