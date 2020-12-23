# SoftwareAcademy-P9-Patient

## Installation

### Database installation
* execute CreateDatabase.sql file


###Docker image construction in project directory :

docker build --build-arg JAR_FILE=target/*.jar -t p9-patient .

### Docker execution :

docker run -p 9102:9102 --name Patient p9-patient


execute command line to start all components: docker-compose up -d

### divers
* paramétrage du proxy pour node js
npm config set proxy http://user:pwd@prafapxymwgvipadm.siege.axa-fr.intraxa:8080


