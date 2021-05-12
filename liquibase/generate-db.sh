#! /bin/bash

set -e

cd $(dirname "$(readlink -f "$0")")

docker-compose up -d

ARTIFACT_ID=$(mvn -f ../pom.xml org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.artifactId -q -DforceStdout)
VERSION=$(mvn -f ../pom.xml org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

mvn -f ../pom.xml package jib:dockerBuild -DskipTests

IMAGE="registry.gmasil.de/docker/${ARTIFACT_ID}:${VERSION}"

docker run -d --rm \
  --network "pokedexer-liquibase-net" \
  --name "pokedexer-liquibase-initializer" \
  -e "SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQL95Dialect" \
  -e "SPRING_DATASOURCE_URL=jdbc:postgresql://pokedexer-liquibase-db:5432/pokedexer" \
  -e "SPRING_DATASOURCE_USERNAME=pokedexer" \
  -e "SPRING_DATASOURCE_PASSWORD=pokedexer" \
  -e "SPRING_JPA_HIBERNATE_DDL_AUTO=update" \
  -e "SPRING_LIQUIBASE_ENABLED=false" \
  ${IMAGE}

echo "waiting for 10 seconds to init database"
sleep 10

mvn -f ../pom.xml liquibase:generateChangeLog -Dliquibase.propertyFile=liquibase/liquibase.properties

docker stop pokedexer-liquibase-initializer
docker-compose down
