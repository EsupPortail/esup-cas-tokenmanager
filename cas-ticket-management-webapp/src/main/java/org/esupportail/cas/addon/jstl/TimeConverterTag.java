package org.esupportail.cas.addon.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The Class TimeConverterTag.
 */
public class TimeConverterTag extends TagSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2626884950612481977L;

	/** The Constant MIN. */
	private static final int MIN  = 60;

	/** The Constant HR. */
	private static final int HR   = MIN * 60;

	/** The Constant DAY. */
	private static final int DAY  = HR * 24;

	/** The time. */
	private long time;

	/**
	 * Converts last time used date into a human readable value
	 * For example : 2 minutes ago, or 1 day ago
	 */
	@Override
	public int doStartTag() throws JspException {

		long comparison = (System.currentTimeMillis() - this.time) / 1000;
		int numberOfDay = (int) (comparison / DAY);
		int numberOfHour = (int) (comparison / HR);
		int numberOfMin = (int) (comparison / MIN);

		try {
			JspWriter out = this.pageContext.getOut();

			if (numberOfDay > 0) {
				out.print(numberOfDay + " day" + pluralAppender(numberOfDay) + " ago");
				return SKIP_BODY;
			} else if (numberOfHour > 0) {
				out.print(numberOfHour + " hour" + pluralAppender(numberOfHour) + " ago");
				return SKIP_BODY;
			} else if (numberOfMin > 1) {
				out.print(numberOfMin + " minute" + pluralAppender(numberOfMin) + " ago");
				return SKIP_BODY;
			} else {
				out.print("Now");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * If n bigger than 1 then append a 's' to the string.
	 *
	 * @param n the n
	 * @return the char
	 */
	private static char pluralAppender(int n) {
		return n > 1 ? 's' : ' ';
	}

}