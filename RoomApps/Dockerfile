FROM arm32v7/adoptopenjdk:11-jre-hotspot-focal
COPY RoomWorker/target/RoomWorker-spring-boot.jar /RoomWorker.jar
#COPY RoomStatus/target/RoomStatus-spring-boot.jar /RoomStatus.jar
#COPY RoomObserver/target/RoomObserver-spring-boot.jar /RoomObserver.jar
ENTRYPOINT ["java","-jar","/RoomWorker.jar"]