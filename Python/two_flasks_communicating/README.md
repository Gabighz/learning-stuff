A hypothetical real-world microservices app where there's:

- needs_the_validator is a more user-facing ervice that receives user data, receives a reply from validator if the data is ok, then does stuff

- validator is a service that must check if the user's information fits into some very simple criteria, specifically:
    - name > 3 and name < 30
    - all characters in name must be alphabetic
    - age > 18
    - age must be of type int

Start it up with:

    sudo sh ./docker-compose-build-and-run.sh production

or:

    sudo sh ./docker-compose-build-and-run.sh development

Alternatively, without docker-compose:

    chmod +x build-and-run.sh
    ./build-and-run.sh

The two_flasks_communicating.postman_collection.json file can be used for automated testing using Postman.

To improve with: 

- Given that the workload on the validator might get high, look to improve it with a scalability solution, specifically Docker Swarm given that its lightweight nature makes it appropriate for this small app.
- Better handle the scenario where the payload received is big.

TO DO: FastAPI version at: <a link\>
