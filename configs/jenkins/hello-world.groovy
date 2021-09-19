
node {
    def mvnHome
    stage('Clone Project') {
          git branch: "master",
              url: 'https://github.com/RamazanBiyik77/k8s_demo.git'
    }
    stage('Create App Image') {
        helloWorldImage = docker.build("localhost:5000/hello-world", "-f app/hello-world/docker/Dockerfile .")
    }
    stage('Push Image To Registry') {
        helloWorldImage.push(latest)
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
}
