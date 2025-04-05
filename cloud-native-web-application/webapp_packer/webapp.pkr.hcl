packer {
  required_plugins {
    amazon = {
      version = ">= 1.0.0"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

# Define the source AMI (Ubuntu 24.04)
source "amazon-ebs" "ubuntu" {
  ami_name      = "my-webapp-ami-{{timestamp}}"      # AMI name with timestamp
  instance_type = "t2.micro"                         # EC2 instance type for building the AMI
  region        = "us-east-2"                        # AWS region
  source_ami    = "ami-0cb91c7de36eed2cb"            # Ubuntu 24.04 AMI ID
  ssh_username  = "ubuntu"                           # Default SSH username for Ubuntu
  associate_public_ip_address = true                # Public IP for the instance
}

# Define the build steps to install dependencies and copy the app
build {
  # Reference the source from above
  sources = ["source.amazon-ebs.ubuntu"]

  # Copy the Spring Boot JAR file from your local machine to the AMI
  provisioner "file" {
    source      = "movie.jar"
    destination = "/home/ubuntu/movie.jar"
  }

  # Provision the JDK
  provisioner "shell" {
    inline = [
      "sudo apt update",
      "sudo apt install -y openjdk-17-jdk",
      "java -version"
    ]
  }

  remove nginx and cloud watch part for assignment-10
  //nginx install
  provisioner "shell" {
    inline = [
      "sudo apt update",
      "sudo apt install -y nginx",
      "sudo systemctl enable nginx"
    ]
  }

  //file nginx
  provisioner "file" {
    source      = "file/nginx.conf"
    destination = "/tmp/nginx.conf"
  }

  provisioner "shell" {
    inline = [
      "sudo mv /tmp/nginx.conf /etc/nginx/nginx.conf",
      "sudo systemctl restart nginx"
    ]
  }

  provisioner "file" {
    source      = "./config/amazon-cloudwatch-agent.json"
    destination = "/tmp/amazon-cloudwatch-agent.json"
  }

  provisioner "shell" {
    script = "./scripts/amazon-cloudwatch-agent-setup.sh"
  }
}
