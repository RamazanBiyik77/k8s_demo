# -*- mode: ruby -*-
# vi: set ft=ruby :
IMAGE_NAME = "generic/ubuntu1804"
N = 1
ssh_pub_path = File.join Dir.home, ".ssh", "id_rsa.pub"
ssh_pub_key = IO.readlines(ssh_pub_path).first.strip

Vagrant.configure("2") do |config|
    config.ssh.insert_key = false

    config.vm.provider "virtualbox" do |v|
        v.memory = 4096
        v.cpus = 2
    end
      
    config.vm.define "server-1" do |master|
        master.vm.box = IMAGE_NAME
        master.vm.network "private_network", ip: "192.168.50.10"
        master.vm.hostname = "server-1"
        master.vm.network :forwarded_port, guest: 8080, host: 9090, id: 'tcp'
        master.vm.network :forwarded_port, guest: 22, host: 8002, id: 'ssh'
        master.vm.network :forwarded_port, guest: 30010, host: 30010, id: 'app'
        master.vm.provision "shell", inline: "echo '192.168.50.12 server-3' >> /etc/hosts"
        master.vm.provision "ansible" do |ansible|
            ansible.playbook = "ansible/playbooks/master-playbook.yml"
            ansible.extra_vars = {
                node_ip: "192.168.50.10",
            }
        end
    end
    

    config.vm.define "server-2" do |server2|
        server2.vm.box = IMAGE_NAME
        server2.vm.network "private_network", ip: "192.168.50.11"
        server2.vm.hostname = "server-2"
        server2.vm.network :forwarded_port, guest: 22, host: 8003, id: 'ssh'
        server2.vm.network :forwarded_port, guest: 8080, host: 9091, id: 'tcp'
        server2.vm.network :forwarded_port, guest: 30010, host: 30011, id: 'app'
        server2.vm.provision "shell", inline: "echo '192.168.50.12 server-3' >> /etc/hosts"
        server2.vm.provision "ansible" do |ansible|
            ansible.playbook = "ansible/playbooks/node-playbook.yml"
            ansible.extra_vars = {
                node_ip: "192.168.50.11",
            }
        end        
    end
    #Jenkins server
    config.vm.define "server-3" do |server3|
        server3.vm.box = IMAGE_NAME
        server3.vm.network "private_network", ip: "192.168.50.12"
        server3.vm.hostname = "server-3"
        server3.vm.network :forwarded_port, guest: 22, host: 8004, id: 'ssh'
        server3.vm.network :forwarded_port, guest: 8080, host: 9092, id: 'tcp'
        server3.vm.provision "ansible" do |ansible|
            ansible.playbook = "ansible/playbooks/jenkins-playbook.yaml"
            # ansible.verbose = true
            ansible.extra_vars = {
                node_ip: "192.168.50.12",
            }
        end 
    end
    config.vm.provision "file", source: "~/.ssh/id_rsa", destination: "~/.ssh/id_rsa"
    config.vm.provision "shell", privileged: false, inline: <<-SCRIPT_DOC
      echo -e \"\n#{ssh_pub_key}\" >> ~/.ssh/authorized_keys
      echo "Public key added to ~/.ssh/authorized_keys"
    SCRIPT_DOC
end
