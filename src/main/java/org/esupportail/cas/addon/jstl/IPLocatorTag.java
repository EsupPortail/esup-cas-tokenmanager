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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.json.JSONObject;

/**
 *
 * @author gsouquet
 */
public class IPLocatorTag extends TagSupport {
    
    private String ipAddress;
    
    @Override
    public int doStartTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        
        try {
            URL url = new URL("http://freegeoip.net/json/" + this.ipAddress);
            HttpURLConnection con = this.sendGETRequest(url);
            // Handle the response
            int responseCode = con.getResponseCode();
            String response = responseReader(con.getInputStream());

            response = response.replaceAll("\"", "\\\"");

            JSONObject responseJson = new JSONObject(response);
            
            if(responseJson.isNull("city")) {
                out.print("Unknown");
            } else if (responseJson.getString("country_code").equals("RD")) {
                out.print(this.ipAddress);
            } else {
                out.print(responseJson.getString("city") + ", " + responseJson.getString("country_code"));
            }
            
        } catch (Exception e) {
            try {
                out.print("Unknown");
            } catch (IOException ex1) { }
            return SKIP_BODY;
        }
        
        
        return SKIP_BODY;
    }
    
    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    private HttpURLConnection sendGETRequest(URL url) throws Exception {
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("GET");
       con.setRequestProperty("User-Agent", "Mozilla/5.0");
       return con;
    }
    
    private String responseReader(InputStream inputStream) throws Exception {
       
       BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
       
       String inputLine;
       StringBuilder response = new StringBuilder();
       
       while((inputLine = in.readLine()) != null) {
           response.append(inputLine);
       }
       in.close();
       return response.toString();
    }
    
}
