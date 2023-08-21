# notifier app
Simple notifier app based on task execution/schedule
## Table of contents
* [General](#general)
* [Stack](#stack)

## General
The App implements DND (Do-Not-Disturb) notifier principle.
Each user has specific time slots when he (or she) is available for incoming messages.
If some event occures inside one of these time slots user receive message immediately.
But if it's miss available time slot user will be notified only when next slot is coming.

## Stack
Project created with:
* Java 17
* Spring Boot version: 3.1.2
* Spring Data JPA
* Spring MVC
* Liquibase
* Docker-compose version: 3.8
