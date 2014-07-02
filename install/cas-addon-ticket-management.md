# cas-addon-ticket-management

This project aims to expose data from the Ticket Registry via a REST API. It also contains some custom `Credential` that will provide extra information for the user authentication.

Requirements :
* CAS 4.0.0+
* Authentication via LDAP

## Install

### 1. Main CAS pom.xml

```xml
<modules>
	...
	<module>cas-addon-ticket-management</module>
	...
</modules>
```

### 2. cas-server-webapp : pom.xml

```xml
<dependency>
	<groupId>org.esupportail.cas.addon</groupId>
	<artifactId>cas-addon-ticket-management</artifactId>
	<version>${cas-addon-ticket-management.version}</version>
</dependency>
```

### 4. cas-server-webapp : web.xml

You should probably update `cors.allowOrigin` param-value to prevent other apps to access the token-service REST API.

```xml
<filter-mapping>
	<filter-name>springSecurityFilterChain</filter-name>
	<url-pattern>/rest/*</url-pattern>
</filter-mapping>
...
<servlet>
	<servlet-name>token-service</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>token-service</servlet-name>
	<url-pattern>/rest/*</url-pattern>
</servlet-mapping>

<filter>
	<filter-name>CORS</filter-name>
	<filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>CORS</filter-name>
	<servlet-name>token-service</servlet-name>
	<init-param>
		<param-name>cors.allowOrigin</param-name>
		<param-value>*</param-value>
	</init-param>
	<init-param>
		<param-name>cors.supportedMethods</param-name>
		<param-value>GET, POST, DELETE</param-value>
	</init-param>
</filter-mapping>
```

### 3. cas-server-webapp : securityContext.xml

```xml
<sec:http auto-config="true" entry-point-ref="notAuthorizedEntryPoint" pattern="/rest/**" use-expressions="true">
    <sec:intercept-url access="hasIpAddress('${cas.securityContext.status.allowedSubnet}')" pattern="/rest/**"/>
</sec:http>
```

### 4. cas-server-webapp : token-service-servlet.xml

Don't look for this file you will have to create it : `src/main/webapp/WEB-INF/token-service-servlet.xml`

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <context:annotation-config />
    <context:component-scan base-package="org.esupportail.cas.addon.service"/>
    <mvc:annotation-driven/>
	<bean class="org.esupportail.cas.addon.utils.TicketRegistryUtils"/>
</beans>
```

## Optionnal improvements for this addon 

cas-addon-ticket-management brings it very own Credential that handle user-agent and ipAddress to get extra-infos about this authentication and help the user to identify easily its diffent sessions. 

### 1. Authentication extras infos : 

#### 1.1. Activate Remember me

Please see [CAS 4.0.0 documentation](https://jasig.github.io/cas/4.0.0/installation/Configuring-Authentication-Components.html#long-term-authentication) to activate `Remember Me` feature

#### 1.2. cas-server-webapp : login-webflow.xml

```xml
<!-- var[name=credential] tag already exists and you just need to update the class attribute -->
<var name="credential" class="org.esupportail.cas.addon.authentication.ExtrasInfosRememberMeUsernamePasswordCredential" />
 
...
 
<view-state id="viewLoginForm" view="casLoginView" model="credential">
    <binder>
        ...
        <binding property="userAgent" />
        <binding property="ipAddress" />
    </binder>
    ...
</view-state>
```

#### 1.3. cas-server-webapp : deployerConfigContext.xml

```xml
<bean id="authenticationManager" class="org.jasig.cas.authentication.PolicyBasedAuthenticationManager">
  ...
  <property name="authenticationMetaDataPopulators">
    <util:list>
      ...
      <bean class="org.esupportail.cas.addon.authentication.principal.ExtrasInfosAuthenticationMetaDataPopulator" />
    </util:list>
  </property>
  ...
</bean>
```

#### 1.4. cas-server-webapp : casLoginView.jsp

```html
<input type="hidden" name="ipAddress" value="${pageContext.request.remoteAddr}"/>
<input type="hidden" name="userAgent" value="${header['user-agent']}" />
```

### 2. CAS Authentication via REST API

#### 2.1 Activate CAS REST API

Please see [CAS 4.0.0 documentation](https://jasig.github.io/cas/4.0.0/protocol/REST-Protocol.html) to activate `REST API` feature

#### 2.2 REST API Support extra info credential

If you want to benefit from the extra info feature through the REST API you should update `/src/main/java/org/jasig/cas/integration/restlet/TicketResource.java`
In the method `obtainCredentials`

```java
final ExtrasInfosRememberMeUsernamePasswordCredential c = new ExtrasInfosRememberMeUsernamePasswordCredential();
```

You can now give extra `userAgent` and `ipAdress` to your REST API requests.



