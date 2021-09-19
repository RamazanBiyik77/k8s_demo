node {
    def mvnHome
    stage('Clone Project') {
          git branch: "master",
              url: 'https://github.com/RamazanBiyik77/k8s_demo.git'
    }
    stage('Create App Image') {
        helloWorldImage = docker.build("server-3:5000/hello-world", "-f app/hello-world/docker/Dockerfile .")
    }
    stage('Push Image To Registry') {
        helloWorldImage.push("5000/hello-world")
    }
    stage('Create NS on k8s') {
        try{
            sh "kubectl create ns hello-world"
        }
        catch (err) {
            unstable("Already exist")
            currentBuild.result = "SUCCESS"
        }
    }
    stage('Deploy to k8s') { 
        sh "kubectl apply -f k8s/hello-world/deployment.yaml"
        sh "kubectl apply -f k8s/hello-world/service.yaml"
    }
}
