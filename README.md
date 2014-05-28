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

#### Add addon dependency to cas-server-webapp

```xml
<dependencies>
	...
    <dependency>
      <groupId>org.esupportail.cas.addon</groupId>
      <artifactId>cas-addon-token-manager-webapp</artifactId>
      <version>${project.version}</version>
    </dependency>
	...
</dependencies>

#### Run the build process

Simply go to `cas-addon-token-manager-webapp/` and run `ant build`, this will do the whole configuration setup int `cas-server-webapp`.

__Warning:___ This will override your CAS Server initial configuration, please make sure to make a backup before running the build process.

## 2. Deployment 

Package your app using maven and deploy `.war` to your favorite Tomcat server. 

You can now access :

* User token manager : `/cas/tokenManager`
* Admin token manager : `/cas/tokenManager/admin`