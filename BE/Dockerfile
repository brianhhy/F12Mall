# 🔧 Build Stage
FROM maven:3.8.7-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package

FROM tomcat:9.0.105-jre17

RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /app/target/f12mall.war /usr/local/tomcat/webapps/f12mall.war

COPY context.xml /usr/local/tomcat/conf/context.xml

EXPOSE 5000

CMD ["catalina.sh", "run"]