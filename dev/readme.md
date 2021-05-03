# PostgreSQL Development

If you want to test with a postgresql database you can run this `docker-compose.yml` file by executing

```bash
docker-compose up
```

in this directory.

This will start up a postgres database preconfigured to match the settings in the `application-postgres.properties`.

After starting postgres run the application with either JVM argument `-Dspring.profiles.active=postgres` or the environment variable `SPRING_PROFILES_ACTIVE=postgres`.

The data is stored in a `data` folder inside the `dev` folder. If you want to start with a clean database stop postgres, delete the `data` folder and restart the postgres database.
