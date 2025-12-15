# Local development
To run keycloak with keycloak-ydb extension. Build this project.

Current version is not really prepared so to success build, so use this: `mvn clean package -Djooq.codegen.skip=true`

Then put generated file in `target/keycloak-ydb-extension-1.0-SNAPSHOT.jar` to `docker/ydb/providers/keycloak-ydb-extension-1.0-SNAPSHOT.jar`

And finally run `docker-compose up -d` in `docker/ydb` directory

# Update jooq codegen:

1. Run ydb:
```
docker run -d --rm --name ydb-local -h localhost \
  --platform linux/amd64 \
  -p 2135:2135 -p 2136:2136 -p 8765:8765 -p 9092:9092 \
  -v $(pwd)/ydb_certs:/ydb_certs -v $(pwd)/ydb_data:/ydb_data \
  -e GRPC_TLS_PORT=2135 -e GRPC_PORT=2136 -e MON_PORT=8765 \
  -e YDB_KAFKA_PROXY_PORT=9092 \
  ydbplatform/local-ydb:latest
```

2. Remove all tables if they are not backward compatible

3. Migrate data `mvn liquibase:update`

4. Codegen jooq: `mvn jooq-codegen:generate`

5. Commit new generated code to repo

# Important
You cannot simultaneously run docker container from `Update jooq codegen` and docker-compose from `Local development`

That's why I always stop one of them to run another container

The problem was in jooq-codegen:generate task. In logs I see  `jdbc:ydb:${ydbContainerId}:` instead of `jdbc:ydb:localhost:`
and codegen can not run successfully.