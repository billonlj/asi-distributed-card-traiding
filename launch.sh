mvn clean package -DskipTests
docker-compose down
docker-compose build
docker-compose up