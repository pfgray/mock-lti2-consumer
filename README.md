To run with docker:
----
```
cd mock-lti2-consumer
sudo docker build -t mock-lti2-consumer .
sudo docker run -d -p 6000:8080 -t mock-lti2-consumer
```
To run on tomcat:
----
```
cd mock-lti2-consumer
mvn clean install
```
deploy the war file located in:
`mock-lti2-consumer/target/mock-lti2-consumer-*.war`

Making requests against the server:
----
endpoints:
`/register`
`/launch`
`/tc_profile/{profile_id}`
`/tc_registration/{profile_id}`
`/Result/{sourcedid}`
`/Settings`
