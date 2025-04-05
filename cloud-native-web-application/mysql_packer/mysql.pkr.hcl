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
  ami_name      = "my-database-ami-{{timestamp}}"      # AMI name with timestamp
  instance_type = "t2.micro"                         # EC2 instance type for building the AMI
  region        = "us-east-2"                        # AWS region
  source_ami    = "ami-0cf26561ea322455d"            # mysql ami
  ssh_username  = "ubuntu"                           # Default SSH username for Ubuntu

  launch_block_device_mappings {
    device_name = "/dev/sda1"
    volume_size = 8
    volume_type = "gp3"
    delete_on_termination = true
  }

  launch_block_device_mappings {
    device_name = "/dev/sdz"
    volume_size = 20
    volume_type = "gp3"
    delete_on_termination = false
    snapshot_id = "snap-019fca5b9b092a016"
  }
}

# Define the build steps to install dependencies and copy the app
build {
  # Reference the source from above
  sources = ["source.amazon-ebs.ubuntu"]

  provisioner "shell" {
    inline = [
      "echo 'No CloudWatch provisioning included in this build.'"
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
