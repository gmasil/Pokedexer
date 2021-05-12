# Liquibase

Changing the database scheme requires to update the liquibase scripts to initialize the database correctly.

The liquibase scripts have to be written manually and **previous steps may not be changed!**

To help writing liquibase scripts a full creation script can be generated with the liquibase maven plugin.

## Workflow

Run the script

```bash
./generate-db.sh
```

**This script requires a local docker installation!**

Then you will find the generated liquibase file in this folder: `db.changelog-generated.yaml`.

**Important:** The java code has to be compilable and runnable.

Now you can compare the `db.changelog-generated.yaml` to the real liquibase script file in `src/main/resources/db/changelog/db.changelog-master.yaml`.

Remember not to modify any of the existing changesets, only use the generated file to easily find the changes.

**Example:** If you added a field to an entity, the generated file will show you the creation of a table including that field/column. You have to write an additional changeset to the liquibase script altering the existing table and adding the new field. Then you might add additional constraints if they exist in the generated file.

## Function

The script executes the following steps:

1. Start postgres database without any data
2. Compile app and build image to local docker daemon
3. Start app with hibernate DDL auto enabled to initialize postgres DB scheme
4. Execute liquibase maven plugin to create script from DB scheme
5. Stop postgres and app containers

**Note:** This workflow does not work with an H2 in memory database, so a postgres is used.

If you dont have docker installed you can install a postgres database to your system and modify the script accordingly.
Make sure to start with a clean database without any tables or data.
