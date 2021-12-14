
# Setup

## Postgresql

### Start a postgres database :
```
docker run --name expatrio-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
```

### Connect to the database :
```
psql --host=127.0.0.1 --port=5432 --username=postgres
```

## Flyway
```
./gradlew flywayMigrate
```

## Jooq code generation
```
./gradlew generateJooq  
```