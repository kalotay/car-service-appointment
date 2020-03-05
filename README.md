# card-service-appointment

A REST API for car service appointment.

## how to run
The web app assumes postgres running locally on port 5432. To change this modify application.properties.

If you have docker installed, you can run `setup-docker-postgres.sh` to create a a postgres database.

To run the app use `./gradlew bootRun`. To run tests use `./gradlew check`.

## example usage

### create an appointment
`curl -XPOST -d '{"price": 52.1, "appointmentTime": "2020-03-04T17:21:01.789", "details": "oil change"}' localhost:8080/appointment -H "Content-Type: application/json"`

### fetch an existing appointment
`curl localhost:8080/appointment/3`

### delete an appointment
`curl -XDELETE localhost:8080/appointment/3`

### update an appointment
`curl -XPUT -d '{"price": 52.1, "appointmentTime": "2020-03-04T17:21:01.789", "details": "oil change"}' localhost:8080/appointment/3 -H "Content-Type: application/json"`

### query appointments within a date range
`curl localhost:8080/appointment?from=2020-03-04T17:21:01.789&to=2020-03-06T17:21:01.789`

## technology considerations

* Computing platform is JVM 11.
* Language used is Java
* Web framework is Spring Boot.
* API communicating with JSON.
* Built using gradle.
* Travis CI.
* PostreSQL as data store.
* liquibase for db migration.