# Fib calculator

Made as part of the 'Docker and Kubernetes: The Complete Guide' course on Udemy by Stephen Grider.

An over-engineered fib calculator made for the sake of learning about
multi-container apps.

### App architecture:

![dev app arch](./dev_app_arch.png)

![prod app arch](./prod_app_arch.png)

![vpc setup](./vpc_setup.png)

### CI/CD pipeline:

![setup](./setup.png)

## For development, run
    docker-compose -f docker-compose-dev.yml up --build