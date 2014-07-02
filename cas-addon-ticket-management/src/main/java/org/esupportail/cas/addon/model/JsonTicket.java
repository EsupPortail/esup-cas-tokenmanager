package org.esupportail.cas.addon.model;

import java.util.Map;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.AbstractTicket;

public class JsonTicket {

	private String id;
	private String owner;
	private long creationTime;
	private long lastTimeUsed;
	private Map<String, Object> authenticationAttributes;

	public JsonTicket(TicketGrantingTicket tgt) {

		Authentication auth = tgt.getAuthentication();

		this.id = tgt.getId();
		this.owner = auth.getPrincipal().getId();
		this.creationTime = tgt.getCreationTime();
		this.lastTimeUsed = ((AbstractTicket)tgt).getLastTimeUsed();
		this.authenticationAttributes = auth.getAttributes();
	}

	public String getId() {
		return this.id;
	}

	public String getOwner() {
		return this.owner;
	}

	public long getCreationTime() {
		return this.creationTime;
	}

	public long getLastTimeUsed() {
		return this.lastTimeUsed;
	}

	public Map<String, Object> getAuthenticationAttributes() {
		return this.authenticationAttributes;
	}

}