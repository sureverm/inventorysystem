FROM maven:3.6.3-openjdk-11

USER root

# Create the home directory for the new app user.
RUN mkdir -p /home/uid1000

# Create an app user so our program doesn't run as root.
RUN groupadd -r uid1000 &&\
    useradd -r -u 1000 -g uid1000 -d /home/uid1000 -s /sbin/nologin -c "Docker image user" uid1000

# Set the home directory to our app user's home.
ENV HOME=/home/uid1000

RUN chown -R uid1000:1000 /home/uid1000

USER uid1000

ENV DB_URL=jdbc:mysql://sql11.freesqldatabase.com:3306/sql11401642?useSSL=false

ENV DB_USERNAME=sql11401642

ENV DB_PASSWORD=5TWM5Iga88

WORKDIR .

EXPOSE 8090 

ARG JAR_FILE=target/inventory-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} .

ENTRYPOINT ["java","-jar","/inventory-0.0.1-SNAPSHOT.jar"]
