# RSO: Ovrednotenje izdelkov - mikrostoritev

## Prerequisites


```bash
docker run -d --name pg-ovrednotenje-izdelkov -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ocene -p 5432:5432 --network rso postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar ovrednotenje-izdelkov-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/ocene

## Run in IntelliJ IDEA
Add new Run configuration and select the Application type. In the next step, select the module api and for the main class com.kumuluz.ee.EeApplication.

Available at: localhost:8080/v1/ocene

## Docker commands
```bash
docker build -t ovrednotenje-izdelkov .   
docker images
docker run ovrednotenje-izdelkov    
docker tag ovrednotenje-izdelkov rso50/ovrednotenje-izdelkov   
docker push rso50/ovrednotenje-izdelkov
docker ps
```

```bash
docker network ls  
docker network rm rso
docker network create rso
docker run -d --name pg-ovrednotenje-izdelkov -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ocene -p 5432:5432 --network rso postgres:13
docker inspect pg-ovrednotenje-izdelkov
docker run -p 8080:8080 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-ovrednotenje-izdelkov:5432/ocene ovrednotenje-izdelkov
```

## Consul
```bash
consul agent -dev
```
Available at: localhost:8500

Key: environments/dev/services/image-catalog-service/1.0.0/config/rest-properties/maintenance-mode

Value: true or false

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f ocene-deployment.yaml 
kubectl apply -f ocene-deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs image-catalog-deployment-6f59c5d96c-rjz46
kubectl delete pod image-catalog-deployment-6f59c5d96c-rjz46
```
Secrets: https://kubernetes.io/docs/concepts/configuration/secret/

