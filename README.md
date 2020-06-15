# Mailer

## How to run:
Run script "buildAndRun"


## Goal list:

[x]Create simple microservice "Mailer", covering basic functionality like sending and receiving mails. Without authorization, database

[x]Dockerize Mailer by creating simple dockerimage.

[x]Migrate project into Spring Cloud (add service discovery, load-balancer and so on)

- Add service discovery
- Add config server and extract config from mailer-core
- Add proxy as entrypoint to an application

[x]Added auth: 

- https://medium.com/tech-tajawal/microservice-authentication-and-authorization-solutions-e0e5e74b248a
- https://medium.com/@arjunac009/spring-boot-microservice-with-centralized-authentication-zuul-eureka-jwt-5719e05fde29

[ ]Replace inMemory storage by centralized Redis image. **(Write through cache using redison)**

[ ]Add permament storage like Cassandra or MongoDB (anything but noSQL)

...

## Another less important stuff I want to add:
- Add auth microservice that will work like proxy for mailer service
- Add ELK stack for full text search
