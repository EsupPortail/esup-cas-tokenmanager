/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.esupportail.cas.addon.authentication.principal;

import org.esupportail.cas.addon.authentication.ExtrasInfosCredential;
import java.util.Map;
import org.jasig.cas.authentication.AuthenticationBuilder;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.Credential;

/**
 * Determines if the credentials provided are for Remember Me Services and then sets the appropriate
 * Authentication attribute if remember me services have been requested.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2005/08/19 18:27:17 $
 * @since 3.2.1
 *
 */
public final class ExtrasInfosAuthenticationMetaDataPopulator implements
    AuthenticationMetaDataPopulator {


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
