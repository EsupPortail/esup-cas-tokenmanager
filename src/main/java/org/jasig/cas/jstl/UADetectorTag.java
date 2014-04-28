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

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 *
 * @author gsouquet
 */
public class UADetectorTag extends TagSupport {
    
    private String userAgent;
    
    @Override
    public int doStartTag() throws JspException {
        
        UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
        ReadableUserAgent agent = parser.parse(this.userAgent);
        
        String uaOS = agent.getOperatingSystem().getName();
        String uaBrowser = agent.getName();
        
        try {
            
            JspWriter out = pageContext.getOut();
            
            if(uaOS.length() <= 0 || uaBrowser.length() <= 0) {
                out.print("Unknown");
            } else {
                out.print(uaBrowser + ", " + uaOS);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return SKIP_BODY;
    }
    
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
}
