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
package org.jasig.cas.authentication.principal;

import java.util.Map;

/**
 * Credentials that wish to store extras infos for users
 * 
 * @author Germain Souquet
 * @version $Revision: 1.0 $ $Date: 2014/04/22
 *
 *
 */
public class ExtrasInfosRememberMeUsernamePasswordCredentials extends RememberMeUsernamePasswordCredentials implements ExtrasInfosCredentials {

    private Map<String, String> extrasInfos;

    private static final long serialVersionUID = 6594051428434005804L;
    
    @Override
    public Map<String, String> getExtrasInfos() {
        return this.extrasInfos;
    }

    @Override
    public void setExtrasInfos(Map<String, String> extrasInfos) {
        this.extrasInfos = extrasInfos;
    }    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.extrasInfos != null ? this.extrasInfos.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
         if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExtrasInfosRememberMeUsernamePasswordCredentials other = (ExtrasInfosRememberMeUsernamePasswordCredentials) obj;
        if (!this.extrasInfos.equals(other.getExtrasInfos())) {
            return false;
        }
        return true;
    }
    
}
