# K8S CLUSTER WITH JENKINS DEPLOYMENT CHANNEL
## Steps

- Create 3 virtual box vm with ubuntu 18.04. For details see [Vagrantfile](https://github.com/RamazanBiyik77/k8s_demo/blob/master/Vagrantfile)
    - Server-1 --> Hostname=server-1, Duty= Kubernetes Master node
    - Server-2 --> Hostname=server-2, Duty= Kubernetes Worker node
    - Server-3 --> Hostname=server-3, Duty= Contains docker registry and jenkins
- 2 nodes for kubernetes 1 node for registry and jenkins. This installation made with ansible. Ansible triggered inside [Vagrantfile](https://github.com/RamazanBiyik77/k8s_demo/blob/master/Vagrantfile) 
    * [Kubernetes Master Playbook](https://github.com/RamazanBiyik77/k8s_demo/blob/master/ansible/playbooks/master-playbook.yml)
    * [Kubernetes Node Playbook](https://github.com/RamazanBiyik77/k8s_demo/blob/master/ansible/playbooks/node-playbook.yml)
    *  [Jenkins Playbook](https://github.com/RamazanBiyik77/k8s_demo/blob/master/ansible/playbooks/jenkins-playbook.yaml)
- Registy installation included inside jenkins ansible playbook. For tasks please [see](https://github.com/RamazanBiyik77/k8s_demo/blob/master/ansible/jenkins/tasks/registry.yml)
- Hello world app written in Python Flask. All dependencies like dockerfile, requirements.txt inclueded base path. Please [see](https://github.com/RamazanBiyik77/k8s_demo/tree/master/app/hello-world)
- For K8S configs please [see](https://github.com/RamazanBiyik77/k8s_demo/tree/master/k8s/hello-world)
- For jenkins pipeline configs please [see](https://github.com/RamazanBiyik77/k8s_demo/blob/master/configs/jenkins/hello-world.groovy)


#### Prerequisites

* **Local tests are made on linux based manjaro OS**
* Ansible
* Vagrant
* Current user have to have ssh keys on ~/.ssh directory with named id_rsa and id_rsa.pub. If not files can be created with `ssh-keygen` command
* On your local machine this port should not be used by your other apps `9090`, `9091`, `9092`, `8002`, `8003`, `8004`, `30010`, `30011`

# Installation Steps

### Prepare Virtualbox VMS

1- On base directory or on [Vagrantfile](https://github.com/RamazanBiyik77/k8s_demo/blob/master/Vagrantfile) directory run this:
```sh
vagrant up
```
All 3 VMS requirements will be installed with this command. This ll take sometime according to your net speed. It took 20 min for me.

To SSH into servers run

```sh
vagrant ssh <hostname_of_server>
```

Endpoints:

- Jenkins http://localhost:9092 Username:admin Password: admin

### Jenkins Pipeline Creation

Go to your jenkins endpoint and log in with username(admin) and password(admin)

All jenkins requirement like plugins installed with jenkins ansible taks. You should not need to install extra plugin.

- On the main menu of the jenkins click `New Item`. This is for creating new job.
- Fill the  item name section as you desired.
- Paste [this](https://github.com/RamazanBiyik77/k8s_demo/blob/master/configs/jenkins/hello-world.groovy) content to Pipeline Script section. And save.
- Your pipelin is created. Click "Build Now" button on the left pane

### Final

You set everything. Now go to 

http://localhost:30010/

Enjoy your app.