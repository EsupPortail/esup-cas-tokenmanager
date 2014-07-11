# cas-token-manager

This project is a CAS addon to manage user's Ticket Granting Ticket
The plugin aim to be compatible with CAS _4.0.0+_

_V2 has just been released_

### Cas Token Manager configuration

Documentation for installing these projet is availaible
* [CAS Addon Ticket Management](https://www.esup-portail.org/wiki/display/ESPADHERENT/Manuel+d%27installation+%3A+CAS+addon+ticket+management)
* [CAS Ticket Management Webapp](https://www.esup-portail.org/wiki/display/ESPADHERENT/Manuel+d%27installation+%3A+Ticket+Management+webapp)

### Warning

Do not forget to configure your proxy settings, if you are using a Tomcat server, edit `bin/setenv.sh`

```
JAVA_OPTS="... -Dhttp.proxyHost=cache.example.org -Dhttp.proxyPort=3128"
```