terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

/* 
    env variables are expected for AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, and AWS_REGION
    as per https://registry.terraform.io/providers/hashicorp/aws/latest/docs#environment-variables
*/
provider "aws" {}

data "aws_ami" "amazon-linux-2" {
 most_recent = true


 filter {
   name   = "owner-alias"
   values = ["amazon"]
 }


 filter {
   name   = "name"
   values = ["amzn2-ami-hvm*"]
 }
}

resource "aws_instance" "an_ec2" {
    ami           = data.aws_ami.amazon-linux-2.id
    instance_type = "t3.micro"
}