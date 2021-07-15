# Getting Started of Spring boot with docker and CI/CD

## Docker
### Simple docker build
 
* Docker to build and create the image command: 
>docker build -t twb/spring-boot-docker .

* Docker to run the image and create de container command: 
>docker run --name spring-docker -p 8080:8080 twb/spring-boot-docker

* To extract dependency from a jar (unpack the jar): 
>jar -xf jarfile_name.jar

* To create a docker image without docker build command: 
>mvn spring-boot:build-image -Dspring-boot.build-image.imageName=twb/spring-boot-docker2

* To run jar with docker by specifying the profile: 
>docker run --name spring-docker -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t twb/spring-boot-docker

* Debugging the Application in a Docker Container:
>docker run --name spring-docker -e "JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" -p 8080:8080 -p 5005:5005 -t twb/spring-boot-docker

### Spring Boot Plugins
* With Spring Boot 2.3 you have the option of building an image from Maven or Gradle directly with Spring Boot.
* set the plugin as shown below: 
> 
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <image>
                <name>twb.spring-boot-docker</name>
            </image>
        </configuration>
    </plugin>
* To run the build: 
> mvn spring-boot:build-image

* To get started quickly, you can run the Spring Boot image generator without even changing your pom.xml (remember that the Dockerfile, if it is still, there is ignored):
> mvn spring-boot:build-image -Dspring-boot.build-image.imageName=twb/spring-boot-docker
### Spotify Maven Plugin
* Add in the ``pom.xml`` file the plugins below: 
>   
    <plugin>
        <groupId>com.spotify</groupId>
        <artifactId>dockerfile-maven-plugin</artifactId>
        <version>1.4.8</version>
        <configuration>
            <repository>twb/${project.artifactId}</repository>
        </configuration>
    </plugin>
  
  
+ Follow following steps for window 10 docker to enable port 2375 on docker
  > Step 1: Right click on "Dcoker Desktop is running icon "
  
  > Step 2: click on Settings
  
  > Step 3: In “General Tab” you must enable checkbox “Expose Demon on tcp://localhost:2375 without TLS” 
+ To build the docker image  
> mvn com.spotify:dockerfile-maven-plugin:build 

* Spotify can be use build image and push to docker hub. See the new pom.xml plugin. 
    * Obviously, the last step is to create a settings.xml under ~/.m2 path and add the Docker Hub credentials. 
    * Here, I add an example that you just need to replace username, password, and email. 
    > <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                                https://maven.apache.org/xsd/settings-1.0.0.xsd">
          <servers>
              <server>
                  <id>registry.hub.docker.com</id>
                  <username>USERNAME</username>
                  <password>PASSWORD</password>
                  <configuration>
                      <email>EMAIL_ADDRESS</email>
                  </configuration>
              </server>
          </servers>
      </settings>
    * To build the docker image  
    > mvn com.spotify:dockerfile-maven-plugin:build 
    * To push to docker hub repository
    > mvn dockerfile:push

### Dockerizing Java Apps using Jib
* Jib is an open-source Java tool maintained by Google for building Docker images of Java applications. It simplifies containerization since with it, we don't need to write a dockerfile.

* And actually, we don't even have to have docker installed to create and publish the docker images ourselves.
* You need to provide our DockerHub credentials to .m2/settings.xml as shown in spotify section
* You can can use jib-maven-plugin, to containerize our application with a simple command:
> mvn compile com.google.cloud.tools:jib-maven-plugin:2.5.0:build -Dimage=$IMAGE_PATH
* where IMAGE_PATH is the target path in the container registry.

#### Simplifying the Maven Jib Command
* Also, we can shorten our initial command by configuring the plugin in our pom instead, like any other maven plugin.
>  
    <project>
      ...
      <build>
          <plugins>
              ...
              <plugin>
                  <groupId>com.google.cloud.tools</groupId>
                  <artifactId>jib-maven-plugin</artifactId>
                  <version>2.5.0</version>
                  <configuration>
                      <to>
                          <image>${image.path}</image>
                      </to>
                  </configuration>
              </plugin>
              ...
          </plugins>
      </build>
      ...
    </project>
* With this change, we can simplify our maven command:
> mvn compile jib:build


## CI/CD
### Concourse
* Concourse is a pipeline-based automation platform that can be used for CI and CD. 
* Everything in Concourse is stateless and everything runs in a container, except the CLI.
* See the example of config file in: ``src/main/ci/build.yml``

### Jenkins
* See Dockerfile to run jenkins in src/main/docker folder
> docker run -p 8080:8080 --name=jenkins-master -d --env JAVA_OPTS="-Xmx8192m" --env JENKINS_OPTS=" --handlerCountMax=300" myjenkins

### Circle CI
* It is continuous integration and delivery platform that allows you to rapidly test and release software.
* You can login with your GitHub account. 
* Follow this [link](https://circleci.com/blog/dockerizing-java-apps-with-circleci-and-jib/) 
to see step by step how to setup your CI/CD app by pushing docker image on docker hub.
* To see more config pipeline properties, [cliek here](https://circleci.com/docs/2.0/workflows/?section=pipelines)


## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/#build-image)
* Spring Boot with Docker [Getting started Spring](https://spring.io/guides/gs/spring-boot-docker/)  
* Spring Boot with Docker [Tipicals](https://spring.io/guides/topicals/spring-boot-docker)  
* https://www.geekyhacker.com/2019/07/14/how-to-use-spotify-docker-maven-plugin/
* https://www.linkedin.com/pulse/simple-guide-devops-cicd-jenkins-pipelines-docker-ramos-da-silva/
* https://technology.riotgames.com/news/putting-jenkins-docker-container
* https://www.jenkins.io/doc/book/installing/docker/
* https://www.baeldung.com/jib-dockerizing
* https://faun.pub/setup-a-circleci-pipeline-for-a-containerized-spring-boot-app-93045fa060de
* https://circleci.com/blog/dockerizing-java-apps-with-circleci-and-jib/
