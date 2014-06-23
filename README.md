# cas-token-manager

This project is a CAS addon to manage user's Ticket Granting Ticket
The plugin aim to be compatible with CAS _4.0.0+_

_Warning : This is only a proof of concept, do not use in a production environment_

V2 version is coming...

### Cas Token Manager configuration

Documentation is accessible there : `http://www.esup-portail.org/display/ESPADHERENT/Addon+%3A+Gestionnaire+de+token` (This is still a work in progress documentation, but as soon as it's finished it will be open to everyone).

### Warning

Do not forget to configure your proxy settings, if you are using a Tomcat server, edit `bin/setenv.sh`

```
JAVA_OPTS="... -Dhttp.proxyHost=cache.example.org -Dhttp.proxyPort=3128"
``

You can now access :

* User token manager : `/cas/tokenManager`
* Admin token manager : `/cas/tokenManager/admin`