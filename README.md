
# Notes
* I have never used Jooq, so maybe good practices are not well fulfilled.
* I follow some hexagonal & DDD principles, so domain doesn't relly on infrastructure or on jooq.
* For simplicity, I cast UUID to String.
* Password are encrypted with bcrypt.
* On login, a cookie with the JWT is sent back. This cookie is expected to be set in request header : Authorization: Bearer [TOKEN].
* H2 database is used for testing. End to end approach is used.
* Some validations are made with spring-validation.
* Since the password is never used for customers, I choose to not put it in the corresponding object.
* Pagination is implemented. Pages starts from 0.

# Improvements that could be made
* Change http status code from 500 to 400 on customer creation if email already exists.
* Cleanup H2 database between each tests.
* More tests could be made.

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

## Curl samples

Note : using jq to format json.

### Login

```
curl -v --location --request POST 'localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@exemple.com",
    "password": "password"
}'
```
The JWT is returned in the Set-cookie header. Copy/paste it for the next requests.

### Get customers

```
curl --location --request GET 'localhost:8080/customers?pageNumber=0&size=10' \
--header 'Authorization: Bearer [TOKEN]' | jq . 
```

### Create customer

```
curl --location --request POST 'localhost:8080/customers' \
--header 'Authorization: Bearer [TOKEN]' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstname": "oreste",
    "lastname": "viron",
    "email": "exemple-email@gmail.com",
    "description": "test"
}' | jq
```

### Update customer

```
curl --location --request PUT 'localhost:8080/customers/{id}' \
--header 'Authorization: Bearer [TOKEN]' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstname": "oreste ",
    "lastname": "viron",
    "description": "new description"
}' | jq
```

### Delete customer

```
curl -v --location --request DELETE 'localhost:8080/customers/{id}' \
--header 'Authorization: Bearer [TOKEN]' 
```