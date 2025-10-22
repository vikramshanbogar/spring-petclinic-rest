provider "aws" {
  region = "us-east-1" # change to your preferred region
}

# --- Variables ---
variable "db_username" {
  default = "testuser"
}

variable "db_password" {
  default = "TestPass123!"
}

# --- VPC & Subnets (for testing, public) ---
resource "aws_vpc" "test_vpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "subnet_a" {
  vpc_id            = aws_vpc.test_vpc.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "ap-south-1a"
}

resource "aws_subnet" "subnet_b" {
  vpc_id            = aws_vpc.test_vpc.id
  cidr_block        = "10.0.2.0/24"
  availability_zone = "ap-south-1b"
}

resource "aws_security_group" "db_sg" {
  vpc_id = aws_vpc.test_vpc.id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # open for testing only — don't use in production!
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# --- RDS Subnet Group ---
resource "aws_db_subnet_group" "test_db_subnet_group" {
  name       = "test-db-subnet-group"
  subnet_ids = [aws_subnet.subnet_a.id, aws_subnet.subnet_b.id]
}

# --- RDS Instance (single AZ, free-tier friendly) ---
resource "aws_db_instance" "postgres_test" {
  identifier        = "postgres-test-db"
  engine            = "postgres"
  engine_version    = "15.4"
  instance_class    = "db.t3.micro" # free-tier eligible
  allocated_storage = 20

  username = var.db_username
  password = var.db_password

  db_subnet_group_name = aws_db_subnet_group.test_db_subnet_group.name
  vpc_security_group_ids = [aws_security_group.db_sg.id]

  publicly_accessible = true
  skip_final_snapshot = true

  tags = {
    Name = "PostgresTestDB"
  }
}

output "db_endpoint" {
  value = aws_db_instance.postgres_test.address
}

output "db_username" {
  value = var.db_username
}

output "db_password" {
  value     = var.db_password
  sensitive = true
}
