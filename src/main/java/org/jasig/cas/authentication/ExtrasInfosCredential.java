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
package org.jasig.cas.authentication;

import org.jasig.cas.authentication.Credential;

/**
 * Credentials that wish to store extras infos for users
 * 
 * @author Germain Souquet
 * @version $Revision: 1.0 $ $Date: 2014/04/22
 *
 *
 */

public interface ExtrasInfosCredential extends Credential {
    
	String AUTHENTICATION_ATTRIBUTE_USER_AGENT = "userAgent";

	String AUTHENTICATION_ATTRIBUTE_IP_ADDRESS = "ipAddress";

	String getUserAgent();

	String getIpAddress();

	void setUserAgent(String userAgent);

	void setIpAddress(String ipAddress);
        
}
