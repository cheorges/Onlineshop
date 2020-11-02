FROM tomee:latest

COPY config/postgresql-42.2.14.jar /usr/local/tomee/lib/
COPY config/tomee.xml /usr/local/tomee/conf/

COPY ear/build/libs/ /usr/local/tomee/webapps/

ENV DB_PORT "5432"
ENV DB_NAME "postgres"
ENV DB_USERNAME "postgres"
ENV DB_PASSWORD "postgres"
