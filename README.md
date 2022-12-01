# WebTodoList
A simple web todo list app with roles coded with Java EE and MariaDB

## Configure context for the database

Create META-INF folder
```sh
# in project root folder
mkdir src/main/webapp/META-INF
touch src/main/webapp/META-INF/context.xml
```
and paste the content (make sure to modify parameters) in `context.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource name="jdbc/<DATABASE>"
              auth="Container" type="javax.sql.DataSource"
              maxTotal="20" maxIdle="5" maxWaitMillis="10000"
              driverClassName="<DATABASE JDBC DRIVER>"
              username="<USERNAME>" password="<PASSWORD>"
              url="jdbc:<DATABASE_ENVIRONMENT>://<HOST>:<PORT>/<DATABASE>?useSSL=false"/>
</Context>
```
