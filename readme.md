# Pokédexer

Pokédexer is a software to track Pokémon card collections.

This webserver allows to access your Pokémon collection at anytime from anywhere. You can add cards to your collection and keep track of value, status and sets.

Each card has a status:

- **interested**: you have the intention to add this card to your collection
- **bought**: you bought the card, but it has not arrived yet
- **ungraded**: you have the card at your place, but its not graded yet
- **in grading**: the card is send off to a grading institute
- **graded**: the graded card is in your hands

The status is a key feature to keep track of which cards you already own. You might forget that you already sended a card to e.g. PSA about a year ago. This should prevent that you buy a card multiple times.

## Development

The default properties are meant for production. There is a `dev` profile for Spring Boot defined in `application-dev.properties`. The default database in production and dev is the H2 in-memory database.

### Development Profile

Run the application with either JVM argument `-Dspring.profiles.active=dev` or the environment variable `SPRING_PROFILES_ACTIVE=dev`.

This profile will automatically create a user with default credentials as defined in `application-dev.properties`. This skips the manual setup page when starting the application to allow for faster turnaround times.

### PostgreSQL Profile

There is Spring Boot profile to run against a local postgresql database in `application-postgres.properties`. If you have Docker (and compose) installed you can run the `docker-compose.yml` file in the `dev` folder to start a configured postgresql database. The data is stored in a `data` folder inside the `dev` folder.

Then run the application with either JVM argument `-Dspring.profiles.active=postgres` or the environment variable `SPRING_PROFILES_ACTIVE=postgres`.

### SASS Stylesheets

Pokédexer uses SASS stylesheets. You can automatically let them be compiled to `*.css` files when you run the maven command

```bash
mvn sass:watch
```

This command will run until you manually stop it, during that time all changes to `*.scss` files will automatically be detected and trigger a recompile.

### Build

This project is build with Maven.

#### Jar File

To build a runnable `jar` file execute

```bash
mvn clean package
```

This will run the unit tests as well. If you want to skip the tests for faster build times you can use the command

```bash
mvn clean package -DskipTests
```

#### Docker Image

If you have docker installed, you can build the docker image into your local docker daemon:

```bash
mvn clean package jib:dockerBuild
```

If you want to skip the unit tests feel free to add the `-DskipTests` argument to the command.

## License

[GNU GPL v3 License](LICENSE.md)

Pokédexer is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Pokédexer is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
