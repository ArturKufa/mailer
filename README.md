# Mailer

## Goal list:

[x]Create simple microservice "Mailer", covering basic functionality like sending and receiving mails. Without authorization, database

[x]Dockerize Mailer by creating simple dockerimage.

[ ]Migrate project into Spring Cloud (add service discovery, load-balancer and so on)

- Add service discovery
- Add config server and extract config from mailer-core
- Add proxy as entrypoint to application

[ ]Replace inMemory storage by centralized Redis image.

[ ]Add permament storage like Cassandra or MongoDB (anything but noSQL)

...

## Another less important stuff I want to add:
- Add auth microservice that will work like proxy for mailer service
- Add ELK stack for full text search
