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
package org.esupportail.cas.addon.authentication;

import org.jasig.cas.authentication.RememberMeUsernamePasswordCredential;

/**
 * Credentials that wish to store extras infos for users
 * 
 * @author Germain Souquet
 * @version $Revision: 1.0 $ $Date: 2014/04/22
 *
 *
 */
public class ExtrasInfosRememberMeUsernamePasswordCredential extends RememberMeUsernamePasswordCredential implements ExtrasInfosCredential {

    private String userAgent;

    private String ipAddress;

    private static final long serialVersionUID = 6594051428434005804L;
    
    @Override
    public String getUserAgent() {
        return this.userAgent;
    }

    @Override
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }    
    
    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
}
