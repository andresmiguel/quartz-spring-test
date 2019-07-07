docker-build-image:
	docker image rm quartz-spring-test:latest
	mvn clean package -DskipTests
	docker build --build-arg JAR_FILE="target/quartz-spring-test-0.0.1-SNAPSHOT.jar" -t quartz-spring-test .


