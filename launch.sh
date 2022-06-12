mvn clean package -T 4
docker-compose down
docker-compose build
docker-compose up