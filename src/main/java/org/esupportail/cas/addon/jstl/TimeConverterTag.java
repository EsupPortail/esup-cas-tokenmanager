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
package org.esupportail.cas.addon.jstl;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author gsouquet
 */
public class TimeConverterTag extends TagSupport {
    
    private static final int MIN  = 60;
    private static final int HR   = MIN * 60;
    private static final int DAY  = HR * 24;
    private long time;
    
    @Override
    public int doStartTag() throws JspException {
        
        long comparison = (System.currentTimeMillis() - this.time) / 1000;
        int numberOfDay = (int) (comparison / DAY);
        int numberOfHour = (int) (comparison / HR);
        int numberOfMin = (int) (comparison / MIN);
        
        try {
            JspWriter out = pageContext.getOut();
            
            if (numberOfDay > 0) {                
                out.print(numberOfDay + " day" + pluralAppender(numberOfDay) + " ago");
                return SKIP_BODY;
            } else if (numberOfHour > 0) {
                out.print(numberOfHour + " hour" + pluralAppender(numberOfHour) + " ago");
                return SKIP_BODY;
            } else if (numberOfMin > 1) {
                out.print(numberOfMin + " minute" + pluralAppender(numberOfMin) + " ago");
                return SKIP_BODY;
            } else {
                out.print("Now");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    private static char pluralAppender(int n) {
        return (n > 1) ? 's' : ' ';
    }
    
}
