# SoftwareAcademy-P9-Patient
NE PLUS UTILISER DEPRECATED, remplacé par patientV2
## Installation

### Database installation
* execute CreateDatabase.sql file

### prerequis Docker
be sure that host is correctly implemented in C:\Windows\System32\drivers\etc

#### image construction
* Eureka: on Eureka directory execute : 
    docker build --build-arg JAR_FILE=target/*.jar -t p9-eureka .
* Config: on Config directory execute : 
    docker build --build-arg JAR_FILE=target/*.jar -t p9-config .
* Actuator: on Actuator directory execute : 
    docker build --build-arg JAR_FILE=target/*.jar -t p9-spring-admin .
* Zuul: on Zuul directory execute : 
    docker build --build-arg JAR_FILE=target/*.jar -t p9-zuul .
* Zipkin: download image : 
    docker pull openzipkin/zipkin
* Patient: on Patient directory execute : 
    docker build --build-arg JAR_FILE=target/*.jar -t p9-patient .

#### Docker execution for all project
* On patient project directory : docker-compose up -d

### Docker image construction in project directory :

docker build --build-arg JAR_FILE=target/*.jar -t p9-patient .

### Docker execution :

docker run -p 8082:8082 --name Patient p9-patient



execute command line to start all components: docker-compose up -d

### divers
* paramétrage du proxy pour node js


### lancement de zipkin 
* depuis le répertoire de zipkin : java -jar zipkin-server-2.23.2-exec.jar
* lancer : http://localhost:9411 
