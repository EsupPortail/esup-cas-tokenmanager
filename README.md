# cas-token-manager

This project is a CAS addon to manage user's Ticket Granting Ticket
The plugin aim to be compatible with CAS _4.0.0+_

_Warning : This is only a proof of concept, do not use in a production environment_

## 1. Configuration

### 1.1 Enable Remember Me Authentication

[Activate CAS Remember Me](https://jasig.github.io/cas/4.0.0/installation/Configuring-Authentication-Components.html#long-term-authentication)

Add this folder into CAS project folder, and add this line to the main `pom.xml`

### 1.3 Cas Token Manager configuration

#### 1.3.1 Add project into main pom.xml

```xml
<modules>
	...
	<module>cas-addon-webapp-token-manager</module>
	...
</modules>
```

#### 1.3.2 Configure cas.properties

Open `cas-server-webapp/src/main/webapp/WEB-INF/cas.properties` and add 

```
# 7 200 seconds => 2 hours
tgt.timeToKillInSeconds=7200
# 604 800 seconds => 1 week
# 7 257 600 seconds => 3 months
tgt.rememberMeTimeToKillInSeconds=604800
```

#### 1.3.3 Copy static files

Copy `cas-addon-webapp-token-manager/src/main/webapp/` to `cas-server-webapp/src/main/webapp/`

#### 1.3.4 Configure cas-servlet.xml

```xml
  <bean
      id="handlerMappingC"
      class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
      <props>
        ...
        <prop key="/tokenManager">revocationController</prop>
      </props>
    </property>
    ...
  </bean>

  ...
  
  <bean id="revocationController" class="org.jasig.cas.admin.revocation.UserRevocationController"
        p:centralAuthenticationService-ref="centralAuthenticationService"
        p:expirationPolicyInSeconds="${tgt.rememberMeTimeToKillInSeconds}"
        p:rememberMeExpirationPolicyInSeconds="${tgt.rememberMeTimeToKillInSeconds}">
    <constructor-arg index="0" ref="ticketRegistry" />
  </bean>

```

#### 1.3.5 Configure web.xml

Add this line to web.xml

```xml
  <servlet-mapping>
    <servlet-name>cas</servlet-name>
    <url-pattern>/tokenManager</url-pattern>
  </servlet-mapping>

  ...

  <jsp-config>
    <taglib>
      <taglib-uri>/cas.tld</taglib-uri>
      <taglib-location>/WEB-INF/jstl/cas.tld</taglib-location>
    </taglib>
  </jsp-config>
```

#### 1.3.6 Configure classes/default_views.properties

Add this line to map revocationController key (of cas-servlet.xml file) to the jsp file

```
revocationView.(class)=org.springframework.web.servlet.view.JstlView
revocationView.url=/WEB-INF/view/jsp/revocation.jsp
```

#### 1.3.7 Configure deployerConfigContext.xml

Change the authenticationMetaDataPopulators tag with this one

```xml
    <property name="authenticationMetaDataPopulators">
        <list>
            <bean class="org.jasig.cas.authentication.SuccessfulHandlerMetaDataPopulator" />
            <bean class="org.jasig.cas.authentication.principal.RememberMeAuthenticationMetaDataPopulator" />
            <bean class="org.jasig.cas.authentication.principal.ExtrasInfosAuthenticationMetaDataPopulator" />
          </list>
    </property>
```

#### 1.3.8 Configure login-webflow.xml

Change the `credentials` bean with this one

```xml
<var name="credentials" class="org.jasig.cas.authentication.principal.ExtrasInfosRememberMeUsernamePasswordCredential" />
```

#### 1.3.9 Configure spring-configuration/securityContext.xml

Todo : not implemented yet

#### 1.3.10 update casLoginView.jsp

update `cas-server-webapp/src/main/webapp/WEB-INF/view/jsp/default/ui/casLoginView.jsp` and add the following in the login form.

```jsp
    <input type="hidden" name="userAgent" value="${header['User-Agent']}"/>
    <input type="hidden" name="ipAddress" value="<%=request.getRemoteAddr()%>"/>
```

## 2. Deployment

Simply run `mvn clean package install`

Copy `cas/cas-server-webapp/target/cas.war` to `$CATALINA_HOME/webapp`

run `sh $CATALINA_HOME/bin/startup.sh`

and go to `https://localhost:8443/cas/`