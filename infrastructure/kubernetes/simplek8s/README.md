# Simple kubernetes cluster

If running locally, on MacOS, and got minikube installed, kubectl would first need:

    minikube start --driver=hyperkit

Can start it up with:

    kubectl apply -f client-pod.yaml
    kubectl apply -f client-node-port.yaml

or preferably instead:

    kubectl apply -f client-deployment.yaml

Can be checked with:

    kubectl get pods
    kubectl get services
    kubectl get deployments

Remove an object:

    kubectl delete -f config-file

Get detailed info about an object:

    kubectl describe object_type object_name
such as

    kubectl describe pod client-pod

Can visit the node in your browser by pasting the output of:

    minikube ip

in the address bar and add :31515 at the end of the ip address.

Other notes:
- A node is a virtual or physical machine.
- Can use minikube >1.10.1 for multi-node clusters.

## Object Types in k8s:
- Pods = Runs one or more closely related containers
    - When used solely, good for one-off dev purposes; rarely used in production (because of limitations like updating config)
- Services = Sets up networking in a k8s cluster
- Deployment = Maintains a set of identical pods, ensuring they have the correct config and that the right number exists
    - Good for dev and for production

## Ways to trigger Deployment updates when image is updated
Kubectl flat out rejects unchanged config files, so we can:

- Manually delete pods to get the Deployment to recreate them with the latest version
    - Cons: Deleting pods manually seems silly, is error-prone, and user-unfriendly
- Tag built images with a real version number and specify that version in the config file
    - Cons: Adds an extra strep in the production deployment process. No, we can't use env variables. Can be a tremendous pain
- Use an imperative command to update the image version the deployment should use
    - Cons: Uses an imperative command
    - This will be used here.

E.g. in your app's directory:

    docker build -t stephengrinder/multi-client:v5 .
    docker push stephengrinder/multi-client:v5
    kubectl set image deployment/client-deployment client=stephengrinder/multi-client:v5

What the above command means:

    kubectl set image object_type / object_name container_name = new_image

### Why your docker ps might show up empty or unrelated to the cluster
Because your docker-client might be connected to the local docker-server of Docker for Mac/Win instead of the docker-server running inside of a node.

Reconfigure with:

    eval $(minikube docker-env)

minikube docker-env contains the necessary env variables to reconfigure our shell.

Why mess with Docker in a node?
- Use debugging techniques with the docker cli
    - E.g docker logs container-id, docker exec -it container-id sh
    - Although the same commands can be used with kubectl
- Delete cached images
    - Reminder: docker system prune -a

### Clean up
    kubectl delete all --all
