/*
 * Copyright 2014 Jasig.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jasig.cas.jstl;

import java.util.Date;
import javax.servlet.jsp.JspException;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author gsouquet
 */
public class ExpirationDateTag extends TagSupport {
    
    private int expirationPolicyInSeconds;
    private Date creationDate;
    private String var;
    
    @Override
    public int doStartTag() throws JspException {
        
        long expirationTimestamp = this.creationDate.getTime() + (this.expirationPolicyInSeconds * 1000);
        Date expirationDate = new Date(expirationTimestamp);
        
        pageContext.setAttribute(this.var, expirationDate);
        
        return SKIP_BODY;
    }
    
    public void setExpirationPolicy(final int expirationPolicyInSeconds) {
        this.expirationPolicyInSeconds = expirationPolicyInSeconds;
    }
    
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setVar(final String var) {
        this.var = var;
    }
    
}