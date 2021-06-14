FROM openjdk:11
ADD build/libs/balance_tracker_gateway-0.01.jar balance_tracker_gateway-0.01.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "balance_tracker_gateway-0.01.jar", "&", "disown"]