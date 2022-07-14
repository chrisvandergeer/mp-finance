FROM chrisvandergeer/wlp
COPY target/liberty/wlp/usr/servers/defaultServer/jdbc/postgresql-42.3.3.jar /wlp/usr/servers/defaultServer/jdbc/postgresql-42.3.3.jar
COPY src/main/liberty/config/server.xml /wlp/usr/servers/defaultServer/server.xml
COPY target/mp-finance.war /wlp/usr/servers/defaultServer/dropins/
CMD /wlp/bin/server run defaultServer
EXPOSE 9080