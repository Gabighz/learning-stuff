# Two flasks communicating

This is a simple example of a microservices architecture using two Flask applications:

1. **needs_the_validator**: A user-facing service that receives user data, validates it using the `validator` service, and could perform additional tasks upon successful validation.
2. **validator**: A service that validates user data based on simple criteria:
- Name length: between 3 and 30 characters
- Name: only alphabetic characters allowed
- Age: must be an integer
- Age: must be greater than 18

## Getting Started
### Prerequisites
- Docker
- Docker Compose (optional, but highly preferred)

### Running the Application
You can run the application using Docker Compose or Docker with shell scripts.

#### Using Docker Compose
Run the following command for the production environment:

    sudo sh ./docker-compose-build-and-run.sh production

Or for the development environment:

    sudo sh ./docker-compose-build-and-run.sh development

#### Without Docker Compose
Make the shell script executable and run it:

    chmod +x build-and-run.sh
    ./build-and-run.sh

## Testing
The two_flasks_communicating.postman_collection.json file can be used to perform automated testing with Postman.

## Future Improvements

- Implement a scalability solution such as Docker Swarm for handling high workloads on the validator service. Docker Swarm, given its lightweight nature, makes it appropriate for this small app.
- Optimize handling of large payloads within the application.
    - Currently, this is attempted with multi-threading, generators, and streaming.

## Related Projects
FastAPI version (coming soon): `<link>`
