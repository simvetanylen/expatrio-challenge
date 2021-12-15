
# Notes
* I have never used Jooq, so maybe good practices are not well fulfilled.
* I follow some hexagonal & DDD principles, so domain doesn't relly on infrastructure or on jooq.
* For simplicity, I cast UUID to String.

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

# Usage

## Password

An admin user is created :
```
email=admin@exemple.com
password=password
```

The creation is made on App.kt if no admin user exists.