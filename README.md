# asi-distributed-card-traiding


## Card service

### Docker

Create network
`docker network create card-service-network`

Run postgresql
``

Run service
`docker run -p 8080:8080 asi/card-service -t card-service --network="card-service-network"
`