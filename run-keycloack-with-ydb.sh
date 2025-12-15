mvn clean package -Djooq.codegen.skip=true

cp target/keycloak-ydb-extension-1.0-SNAPSHOT.jar docker/ydb/providers/keycloak-ydb-extension-1.0-SNAPSHOT.jar

docker-compose -f docker/ydb/docker-compose.yml up -d