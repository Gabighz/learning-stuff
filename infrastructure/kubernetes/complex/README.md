## A bit more complex k8s project

K8s version of:
https://github.com/Gabighz/learning-stuff/tree/master/infrastructure/docker/complex

After running the below command to load the config files in k8s, the app can be visited in your local browser at the IP that's output by 'minikube ip'.

## Misc new commands being used vs simplek8s:
Load multiple config files:

    kubectl apply -f k8s

Get logs:

    kubectl logs <pod-name>

Access minikube's cool dashboard:

    minikube dashboard

## New objects being used vs simplek8s:
- **ClusterIP** = Exposes a set of pods to other objects in the cluster (would be otherwise inaccessible); does not allow traffic from the outside world
- **Ingress** = Exposes a set of services to the outside world (supposedly better than LoadBalancer). Specifically going to use [ingress-nginx](http://github.com/kubernetes/ingress-nginx) for this project.
- **Secret** = Securely stores a piece of information in the cluster, such as a database password

## Postgres PVC
Postgres, unlike redis which is an in-memory data store, stores data in a filesystem.

To have a consistent filesystem across pods with such databases, we need a Persistent Volume Claim to carry data over to a new pod that contains a postgres container, for example, in case the one we were using crashes and was deleted by Deployment. We really don't want loss of data on any database such as postgres.

This volume is on the host machine and is outside the postgres container(s). Postgres writes to / reads from this volume. Although, these replicated databases must be aware of each other, otherwise it's a recipe for disaster.

Volume in generic container terminology: Some type of mechanism that allows a container to access a filesystem outside itself.

Volume in K8s: An **object** that allows a container to store data at the pod level. Useful if we're expecting that a container within a pod might crash and we're ok if data is lost when the pod would be deleted.

Persistent Volume: Long-term durable storage that's separate from any pod. (like a store room - statically provisioned)

Persistent Volume Claim: Storage options that a pod would have access to in a particular cluster (like a billboard that advertises what the store room should have; if the store room doesn't have it on hand, it fabricates it on the fly - dynamically provisioned)

Can use the following commands to see more information about where and how would volumes be dynamically provisioned:

    kubectl get storageclass
    kubectl describe storageclass

In the case of local dev, a slice of the hard drive would be claimed. On a cloud provider, by default, Google Cloud Persistent Disk, Azure File/Disk or AWS EBS would be used.

Get info about present persistent volumes and claims:

    kubectl get pv
    kubectl get pvc

## Creating a Secret object
Not using a config file because it would defeat its purpose, so we do it imperatively like this:

    kubectl create secret <type_of_secret> <secret_name> --from-literal (as opposed to from file) key=value

More concretely for this project:

    kubectl create secret generic pgpassword --from-literal PGPASSWORD=12345

Get info about present secrets:

    kubectl get secrets

## Ingress Service
The ingress controller can be installed through minikube's addon system:

    minikube addons enable ingress

or with Helm:

    helm upgrade --install ingress-nginx ingress-nginx \
    --repo https://kubernetes.github.io/ingress-nginx \
    --namespace ingress-nginx --create-namespace

or without Helm (also visiting the link in a browser is useful to find out more):

    kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.0/deploy/static/provider/cloud/deploy.yaml

## Misc notes
- We could combine the contents of any config files, separating them with '---', but we consider it's more maintainable and readable to keep them in the current separation.
- Keep in mind that the worker is the slowest component in our app (does the fib calcs) and might need to scale out and have multiple instances.
- Currently using one redis server, but we can set it up in cluster mode, where there are multiple copies of redis that communicate with each other and enhance overall stability and throughput. Same goes for postgres.

### Diagrams

![arch](./arch.jpeg)

![path to production](./steps.jpeg)