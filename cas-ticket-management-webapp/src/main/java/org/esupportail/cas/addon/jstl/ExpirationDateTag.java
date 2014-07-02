package org.esupportail.cas.addon.jstl;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The Class ExpirationDateTag.
 */
public class ExpirationDateTag extends TagSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6644109354174707458L;

	/** The expiration policy in seconds. */
	private int expirationPolicyInSeconds;

	/** The creation date. */
	private Date creationDate;

	/** The var. */
	private String var;

	/**
	 * Calculate and print the expiration date based on the expiration policy and the creation date
	 */
	@Override
	public int doStartTag() throws JspException {

		long expirationTimestamp = this.creationDate.getTime() + this.expirationPolicyInSeconds * 1000;
		Date expirationDate = new Date(expirationTimestamp);

		this.pageContext.setAttribute(this.var, expirationDate);

		return SKIP_BODY;
	}

	/**
	 * Sets the expiration policy.
	 *
	 * @param expirationPolicyInSeconds the new expiration policy
	 */
	public void setExpirationPolicy(final int expirationPolicyInSeconds) {
		this.expirationPolicyInSeconds = expirationPolicyInSeconds;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Sets the var.
	 *
	 * @param var the new var
	 */
	public void setVar(final String var) {
		this.var = var;
	}

}