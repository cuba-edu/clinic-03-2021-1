FROM openjdk:11
COPY . /opt/clinic
CMD java -Dspring.profiles.active=docker -Dapp.home=/opt/clinic-home -jar /opt/clinic/clinic-core.jar
