# cas-ticket-management-webapp

## 1. Configuration

Open `src/main/webapp/WEB-INF/config.properties` and edit this file with your own settings

* `ldap.authn.baseDn` stands for the base of the ldap connection
* `ldap.baseSearch` stands for the branch where the users are
* `ldap.group.base` stands for the branch where the groups are
* `ldap.group.attributeName` stands for the name of the attribute hosting the group name. 

## 2. Build

Simply run 

```
mvn clean package install
```

## 3. Deploy 

Grab `target/cas-ticket-management.war` and copy it to you Tomcat Server.