# cas-token-manager

This project is a CAS addon to manage user's Ticket Granting Ticket

Warning : This is only a proof of concept, do not use in a production environment


## 1. Configuration

### 1.1 Enable Remember Me Authentication

[Activate CAS Remember Me](https://wiki.jasig.org/display/CASUM/Remember+Me)

### 1.2 Enable CAS REST API

[Activate CAS REST API](https://wiki.jasig.org/display/casum/restful+api)

Add this folder into CAS project folder, and add this line to the main `pom.xml`

### 1.3 Cas Token Manager configuration

#### 1.3.1 Add project into main pom.xml

```
<modules>
	...
	<module>cas-server-webapp-admin</module>
	...
</modules>
```

#### 1.3.2 Copy static files

Copy `cas-server-webapp-admin/src/main/webapp/` to `cas-server-webapp/src/main/webapp/`

#### 1.3.3 Configure cas-servlet.xml

```
  <bean
      id="handlerMappingC"
      class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
      <props>
        ...
        <prop key="/tests/revocation.html">revocationController</prop>
      </props>
    </property>
    ...
  </bean>

  ...
  
  <bean id="revocationController" class="org.jasig.cas.admin.revocation.UserRevocationController"
        p:centralAuthenticationService-ref="centralAuthenticationService">
    <constructor-arg index="0" ref="ticketRegistry" />
  </bean>

```

#### 1.3.4 Configure web.xml

Add this line to web.xml

```
  <servlet-mapping>
    <servlet-name>cas</servlet-name>
    <url-pattern>/tests/revocation.html</url-pattern>
  </servlet-mapping>
```

#### 1.3.5 Configure classes/default_views.properties

Add this line to map revocationController key (of cas-servlet.xml file) to the jsp file

```
revocationView.(class)=org.springframework.web.servlet.view.JstlView
revocationView.url=/WEB-INF/view/jsp/revocation.jsp
```

#### 1.3.6 Configure deployerConfigContext.xml

Change the authenticationMetaDataPopulators tag with this one

```
	<property name="authenticationMetaDataPopulators">
	    <list>
        	<bean class="org.jasig.cas.authentication.principal.ExtrasInfosAuthenticationMetaDataPopulator" />
      	</list>
	</property>
```

#### 1.3.7 Configure login-webflow.xml

Change the `credentials` bean with this one

```
<var name="credentials" class="org.jasig.cas.authentication.principal.ExtrasInfosRememberMeUsernamePasswordCredentials" />
```

#### 1.3.8 Configure spring-configuration/securityContext.xml

Todo : not implemented yet

### 1.4 Modify CAS API REST

Add the `cas-server-webapp-admin` maven dependency to the restlet `pom.xml`

You will have to change the credentials type in `cas-server-integration-restlet/src/main/java/org/jasig/cas/integration/restlet/TicketResource.java`
Change the first line of the `obtainCredentials()` method with this one

```
final ExtrasInfosRememberMeUsernamePasswordCredentials c = new ExtrasInfosRememberMeUsernamePasswordCredentials();
```

## 2. Deployment

Simply run `mvn clean package install`

Copy `cas/cas-server-webapp/target/cas.war` to `$CATALINA_HOME/webapp`

run `sh $CATALINA_HOME/bin/startup.sh`

and go to `https://localhost:8443/cas/`