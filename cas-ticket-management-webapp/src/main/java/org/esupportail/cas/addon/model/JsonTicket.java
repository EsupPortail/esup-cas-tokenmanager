package org.esupportail.cas.addon.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsonTicket {

	@JsonProperty("id")
	private String id;

	@JsonProperty("owner")
	private String owner;

	@JsonProperty("creationTime")
	private long creationTime;

	@JsonProperty("lastTimeUsed")
	private long lastTimeUsed;

	@JsonProperty("authenticationAttributes")
	private Map<String, Object> authenticationAttributes;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getLastTimeUsed() {
		return this.lastTimeUsed;
	}

	public void setLastTimeUsed(long lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public Map<String, Object> getAuthenticationAttributes() {
		return this.authenticationAttributes;
	}

	public void setAuthenticationAttributes(
			Map<String, Object> authenticationAttributes) {
		this.authenticationAttributes = authenticationAttributes;
	}

}