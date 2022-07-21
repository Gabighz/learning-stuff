docker build -t gabeghz/multi-client:latest -t gabeghz/multi-client:$SHA -f ./client/Dockerfile ./client
docker build -t gabeghz/multi-server:latest -t gabeghz/multi-server:$SHA -f ./server/Dockerfile ./server
docker build -t gabeghz/multi-worker:latest -t gabeghz/multi-worker:$SHA -f ./worker/Dockerfile ./worker

docker push gabeghz/multi-client:latest
docker push gabeghz/multi-server:latest
docker push gabeghz/multi-worker:latest

docker push gabeghz/multi-client:$SHA
docker push gabeghz/multi-server:$SHA
docker push gabeghz/multi-worker:$SHA

kubectl apply -f k8s
kubectl set image deployments/client-deployment client=gabeghz/multi-client:$SHA
kubectl set image deployments/server-deployment server=gabeghz/multi-server:$SHA
kubectl set image deployments/worker-deployment worker=gabeghz/multi-worker:$SHA