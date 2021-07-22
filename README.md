# Monolith starter

- [x] Basic setup including actuator, spring-configuration-processor, lombok, logback etc
- [x] JWT auth
    ```bash
    $ ACCESS_TOKEN=$(curl -s -X POST -H "Content-type: application/json" -d '{"username":"user", "password":"password"}' http://localhost:8080/authenticate | jq -r .accessToken)
    $ curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8080/users
    ```
- [x] User registration
    ```bash
    $ curl -s -H "Content-type: application/json" -XPOST http://localhost:8080/registration -d '{"username":"user", "password":"password", "email":"user@example.com"}'
    ```
- [ ] Social login
- [ ] Open API Spec & Problem details
- [ ] Docker build
- [ ] Log and error tracing with sleuth, logbook, sentry
