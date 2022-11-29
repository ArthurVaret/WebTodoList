# WebTodoList
A simple web todo list app with roles coded with Java EE and MariaDB

## Configure context for the database

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
