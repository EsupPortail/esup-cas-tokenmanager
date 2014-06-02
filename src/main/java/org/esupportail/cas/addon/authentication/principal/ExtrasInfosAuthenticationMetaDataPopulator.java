package org.esupportail.cas.addon.authentication.principal;

import java.util.Map;

import org.esupportail.cas.addon.authentication.ExtrasInfosCredential;
import org.jasig.cas.authentication.AuthenticationBuilder;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.Credential;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtrasInfosAuthenticationMetaDataPopulator.
 */
public final class ExtrasInfosAuthenticationMetaDataPopulator implements
AuthenticationMetaDataPopulator {


	/* (non-Javadoc)
	 * @see org.jasig.cas.authentication.AuthenticationMetaDataPopulator#populateAttributes(org.jasig.cas.authentication.AuthenticationBuilder, org.jasig.cas.authentication.Credential)
	 */
	@Override
	public void populateAttributes(AuthenticationBuilder builder, Credential credential) {
		if (credential instanceof ExtrasInfosCredential) {
			final ExtrasInfosCredential r = (ExtrasInfosCredential) credential;

			Map<String, Object> attributes = builder.getAttributes();

			attributes.put(ExtrasInfosCredential.AUTHENTICATION_ATTRIBUTE_USER_AGENT, r.getUserAgent());
			attributes.put(ExtrasInfosCredential.AUTHENTICATION_ATTRIBUTE_IP_ADDRESS, r.getIpAddress());
		}
	}
}
