# Patients

Patients service provides API to manage patients. All APIs are secured and can be accessed only by 
authorized users with proper permissions. Authorization is implemented using OAuth 2.0 protocol 
and Keycloak as authorization server. 

## Setup

Hosts file should be updated to use Keycloak properly.
Go to `hosts` file and add `127.0.0.1 keycloak` at the end of the file. 
Path to file on Windows: `C:\Windows\System32\drivers\etc\hosts`.

Service can be launched from IDE or docker. <br />
Run service from docker:
1. Build project using gradle
2. Go to the project root directory and run `docker build -t "patients-management:latest" .`
3. Run `docker-compose up'

Once the services are up and running Keycloak can be access via the following link: <br />
`http://localhost:8080/`. <br />
Realm for patients management (with client and supported roles) is already imported to Keycloak 
and no need to create it manually. 
But new user with 'practitioner' role should be created manually. To do it go to 
PatientsManagementRealm realm and create new user. Info that should be specified for user:
1. username
2. email
3. first name
4. last name
5. password (temporary = false)
6. Role mapping (add 'practitioner' role)

Repeat same steps to create user with 'patient' role.

After creating users their credentials can be used to fetch accessToken and use it for accessing 
exposed APIs.
Postman collection contains method to fetch accessToken. Before calling method go to postman local 
environment and set values for `username` and `password` variables. After fetching access token 
it will be saved to postman variable and used in all other requests.

## Open API
Service provides support for OpenAPI. <br />
Link to Swagger UI, which can be used to invoke and explore API: <br />
`http://localhost:8083/swagger-ui/index.html#`

## Postman
Project contains 2 files in folder `./postman-collections`. They can be imported in postman.
One file is a postman collection with APIs, another one is needed to import local postman 
environment with specific variables.
