# Monolith starter

- [x] Basic setup including actuator, spring-configuration-processor, lombok, logback etc
- [x] JWT auth
    ```bash
    $ RESPONSE=$(curl -s -X POST -H "Content-type: application/json" -d '{"username":"user", "password":"password"}' http://localhost:8080/authenticate)
    $ ACCESS_TOKEN=$(echo $RESPONSE | jq -r .accessToken)
    $ curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8080/users
    ```
- [ ] User registration
- [ ] Social login
- [ ] Open API Spec & Problem details
- [ ] Docker build
- [ ] Log and error tracing with sleuth, logbook, sentry