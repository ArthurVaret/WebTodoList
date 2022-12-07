# WebTodoList
A simple web todo list app with roles coded with Java EE and MariaDB/MySQL

## Prerequisites

Tomcat

## Create DB context

```sh
# in project root folder (./webtodolist) for example
mkdir src/main/webapp/META-INF
touch src/main/webapp/META-INF/context.xml
```
and paste the content (make sure to modify parameters) of `context.xml` below. 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource name="jdbc/<DATABASE>"
              auth="Container" type="javax.sql.DataSource"
              maxTotal="20" maxIdle="5" maxWaitMillis="10000"
              driverClassName="<CONNECTOR JDBC DRIVER>" 
              username="<USER>" password="<PASSWORD>"
              url="jdbc:<DB_ENV>://<HOST>:<PORT>/<DATABASE>?useSSL=false"/>
</Context>
```
The `<CONNECTOR JDBC DRIVER>` can be either `org.mariadb.jdbc.Driver` for MariaDB or `com.mysql.cj.jdbc` for MySQL
