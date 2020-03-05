#!/bin/sh
docker run --name appointment-postgres -d -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword postgres
sleep 5
docker exec -t appointment-postgres createdb -U postgres appointment
