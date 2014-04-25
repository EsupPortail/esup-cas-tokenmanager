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
package org.jasig.cas.web.support;

import com.github.inspektr.common.web.ClientInfo;
import com.github.inspektr.common.web.ClientInfoHolder;
import org.jasig.cas.authentication.AuthenticationManager;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.test.MockRequestContext;

import javax.sql.DataSource;

import static org.junit.Assert.fail;

/**
 * Unit test for {@link InspektrThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter}.
 *
 * @author Marvin S. Addison
 * @version $Revision$ $Date$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:/applicationContext.xml",
        "classpath:/jpaTestApplicationContext.xml",
        "classpath:/inspektrThrottledSubmissionContext.xml"
})
public class InspektrThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapterTests
        extends AbstractThrottledSubmissionHandlerInterceptorAdapterTests {

    @Autowired
    private InspektrThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter throttle;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;


    @Before
    public void setUp() throws Exception {
        new JdbcTemplate(dataSource).execute(
                "CREATE TABLE COM_AUDIT_TRAIL ( " +
                "AUD_USER      VARCHAR(100)  NOT NULL, " +
                "AUD_CLIENT_IP VARCHAR(15)    NOT NULL, " +
                "AUD_SERVER_IP VARCHAR(15)    NOT NULL, " +
                "AUD_RESOURCE  VARCHAR(100)  NOT NULL, " +
                "AUD_ACTION    VARCHAR(100)  NOT NULL, " +
                "APPLIC_CD     VARCHAR(5)    NOT NULL, " +
                "AUD_DATE      TIMESTAMP      NOT NULL)");
    }


    protected AbstractThrottledSubmissionHandlerInterceptorAdapter getThrottle() {
        return throttle;
    }


    @Override
    protected MockHttpServletResponse loginUnsuccessfully(final String username, final String fromAddress) throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("POST");
        request.setParameter("username", username);
        request.setRemoteAddr(fromAddress);
        MockRequestContext context = new MockRequestContext();
        context.setCurrentEvent(new Event("", "error"));
        request.setAttribute("flowRequestContext", context);
        ClientInfoHolder.setClientInfo(new ClientInfo(request));

        getThrottle().preHandle(request, response, null);

        try {
            authenticationManager.authenticate(badCredentials(username));
        } catch (AuthenticationException e) {
            getThrottle().postHandle(request, response, null, null);
            return response;
        }
        fail("Expected AuthenticationException");
        return null;
    }
    
    private UsernamePasswordCredentials badCredentials(final String username) {
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
        credentials.setUsername(username);
        credentials.setPassword("badpassword");
        return credentials;
    }
}
