# Zotteltec - Onlineshop
> Java EE 8 Onlineshop Besipiel | FFHS
## Quickstart
```
docker pull tomee
docker run -d -p 8080:8080 tomee
```
## External Database
### Example: *Postgres*
#### Setup database
Add to WEB-INF a resources.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<tomee>
    <Resource id="jdbc/default" type="javax.sql.DataSource">
        JdbcDriver = org.postgresql.Driver
        JdbcUrl = jdbc:postgresql://localhost:5432/postgres
        UserName = postgres
        Password = postgres
        jtaManaged = true
    </Resource>
</tomee>
```

#### Setup a Tomeee
1. Install tomee server `brew install tomee-plus`  
2. Copy newst [Postgres-Driver](https://jdbc.postgresql.org/) to the `tomee/lib` folder  

#### Database
```
docker pull postgres
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```