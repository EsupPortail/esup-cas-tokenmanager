package org.esupportail.cas.addon.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class TicketOwner {

	@JsonProperty("owner")
	private String owner;

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
