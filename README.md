
## Docker  Image Used   
`ubuntu/nginx`

Ubuntu OS with web service nginx can be found in the canonical official hub page  [here](https://hub.docker.com/r/ubuntu/nginx)

## Setup Docker Environment 
- Install VirtualBox VM with guest OS Ubuntu
- Install Docker inside the VM
- Add the value `-H tcp://0.0.0.0:2375`  in the `ExecStart` variable on the **docker.service** file so that 
docker API gets exposed to the host (guest machine in this case)
- Configure a port-forward in Virtual Box so that host machine can access the guest machine 


##  Setup Kubernetes Environment 

- Install Minikube using virtualbox as driver (no need to install docker in the host machine)
- Install kubeclt
- start  minikube cluster with 1 control-plane node and 1 worker
  - `minikube start --nodes 2 -p multinode`
- create a kubernetes service to expose deployment 
  - `kubectl expose deployment ubuntu-nginx-deployment --type=LoadBalancer --name=ubuntu-nginx-service`
- create a proxy to the cluster API server allowing rest calls from the host machine
  - `kubectl proxy  --port=9090`

##  Endpoints Description
**Docker**
* **/create-start-docker-nginx**
  * Calls  docker API endpoints `/containers/create` to create a container and `/containers/<id>/start` to start it
* **/stop-docker-nginx**
  * Calls docker API endpoint `/containers/<id>/stop` to stop the running container
* **/get-page-docker**
  * Calls docker API endpoint`/info` summarized information about the environment such as number of  total containers, running containers, paused containers, number , images and etc

**Kubernetes**
* **/create-start-docker-k8s**
  * Calls KS8 API endpoint `/apis/apps/v1/namespaces/<namespace>/deployments` to create a deployment with a pod and the same dock image (`ubuntu/nginx`)
* **/stop-docker-k8s**
  * Calls KS8 APi endpoint `/apis/apps/v1/namespaces/default/deployments/ubuntu-nginx-deployment` to remove the deployment stopping the container
* **/get-page-k8s**
  * Calls the  KS8 service URL targeting the  nginx container and shows the HTML welcome page 

##  Project Structure 
Overview of the project structure 

**Controller**
- DockerController
  - Handles docker endpoints and redirects to DockerService to process
- KubernetesController
  - Handles ks8 endpoints and redirects to KubernetesService to process

 **Service**
- DockerService
  - Make the call docker API and processes the result 
- KubernetesService
  - Make the call ks8 API and processes the result 

## Tests

Tests evidence to all the endpoints  can be found [here](https://docs.google.com/document/d/10f_oyIRw7u6fsUJpIvZXkVC6BwZNADuHM_L0GdWfl9A/edit?usp=sharing) 


##  Future Improvement 
- Make use of secure API calls through authentication 
- Deploy the SpringBoot application as a container 








