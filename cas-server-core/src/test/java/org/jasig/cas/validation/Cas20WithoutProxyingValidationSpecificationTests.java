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
package org.jasig.cas.validation;

import org.jasig.cas.TestUtils;

import junit.framework.TestCase;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date: 2007-01-22 15:35:37 -0500 (Mon, 22 Jan
 * 2007) $
 * @since 3.0
 */
public class Cas20WithoutProxyingValidationSpecificationTests extends TestCase {

    private Cas20WithoutProxyingValidationSpecification validationSpecification;

    protected void setUp() throws Exception {
        this.validationSpecification = new Cas20WithoutProxyingValidationSpecification();
    }

    public void testSatisfiesSpecOfTrue() {
        assertTrue(this.validationSpecification.isSatisfiedBy(TestUtils
            .getAssertion(true)));
    }

    public void testNotSatisfiesSpecOfTrue() {
        this.validationSpecification.setRenew(true);
        assertFalse(this.validationSpecification.isSatisfiedBy(TestUtils
            .getAssertion(false)));
    }

    public void testSatisfiesSpecOfFalse() {
        assertTrue(this.validationSpecification.isSatisfiedBy(TestUtils
            .getAssertion(false)));
    }

    public void testDoesNotSatisfiesSpecOfFalse() {
        assertFalse(this.validationSpecification.isSatisfiedBy(TestUtils
            .getAssertion(false, new String[] {"test2"})));
    }

    public void testSettingRenew() {
        final Cas20WithoutProxyingValidationSpecification validation = new Cas20WithoutProxyingValidationSpecification(
            true);
        assertTrue(validation.isRenew());
    }
}