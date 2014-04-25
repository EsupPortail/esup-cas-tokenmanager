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
package org.jasig.cas.web.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.jasig.cas.TestUtils;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.ImmutableAuthentication;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.validation.ImmutableAssertionImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Unit test for {@link Cas10ResponseView} class.
 *
 * @author Scott Battaglia
 * @author Marvin S. Addison
 */
public class Cas10ResponseViewTests extends TestCase {

    private final Cas10ResponseView view = new Cas10ResponseView();

    private Map<String, Object> model;

    protected void setUp() throws Exception {
        this.model = new HashMap<String,Object>();
        List<Authentication> list = new ArrayList<Authentication>();
        list.add(new ImmutableAuthentication(new SimplePrincipal("test")));
        this.model.put("assertion", new ImmutableAssertionImpl(list,
            TestUtils.getService("TestService"), true));
    }

    public void testSuccessView() throws Exception {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        this.view.setSuccessResponse(true);
        this.view.render(this.model, new MockHttpServletRequest(), response
            );
        assertEquals("yes\ntest\n", response.getContentAsString());
    }

    public void testFailureView() throws Exception {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        this.view.setSuccessResponse(false);
        this.view.render(this.model, new MockHttpServletRequest(),
            response);
        assertEquals("no\n\n", response.getContentAsString());
    }
}
