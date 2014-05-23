# cas-token-manager

This project is a CAS addon to manage user's Ticket Granting Ticket
The plugin aim to be compatible with CAS _4.0.0+_

_Warning : This is only a proof of concept, do not use in a production environment_

## 1. Configuration

[Activate CAS Remember Me](https://jasig.github.io/cas/4.0.0/installation/Configuring-Authentication-Components.html#long-term-authentication)

Add this folder into CAS project folder, and add this line to the main `pom.xml`

### 1.2 Cas Token Manager configuration

####  Add project into main pom.xml

```xml
<modules>
  ...
  <module>cas-addon-webapp-token-manager</module>
  ...
</modules>
```

#### Copy static files

* `cas-addon-webapp-token-manager/src/main/java/webapp/WEB-INF/jstl` to `cas-server-webapp/src/main/java/webapp/WEB-INF/`
* `cas-addon-webapp-token-manager/src/main/java/webapp/WEB-INF/view/jsp/revocation.jsp` to `cas-server-webapp/src/main/java/webapp/WEB-INF/view/jsp/`

#### Run the build process

```
ant build
```

## 2. Deployment 

Package your app using maven and deploy `.war` to your favorite Tomcat server. 

Token manager interface is accessible from : `http://YOU_SERVER:8080/tokenManager`