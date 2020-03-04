# card-service-appointment

A REST API for car service appointment.

## technology considerations

* Computing platform is JVM 11.
* Language used is Java
* Web framework is Spring Boot.
* API communicating with JSON.
* Built using maven (spring default) or gradle.
* High level tests using restassured, low level tests using junit 4 (dependency of restassured), db tested with testcontainers.
* Travis CI (CircleCI if Travis doesn't work out).
* PostreSQL or MySQL as data store.
* liquibase for db migration.