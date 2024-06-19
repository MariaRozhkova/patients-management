# Patients-generator
Patients-generator automatically creates some patients via REST API.

## Setup

Make sure PatientsManagement service is already running on port 8083.

Service can be launched from IDE or from terminal with proper program arguments. First argument 
represents 
username of user with 'practitioner' role, another argument - its password. These credentials 
are needed as only user with 'practitioner' role has permissions to create patients. <br />
Run app from terminal:
1. build project using gradle
2. Run `java -jar patients-generator.jar <username> <password>`
