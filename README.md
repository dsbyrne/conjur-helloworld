To run:

    $ mvn package
    $ java -jar target/HelloWorldService-0.0.1-SNAPSHOT.jar server hello-world.yml

To test, you need a Conjur appliance (currently containing a webservice named 'hello-world'). Then make a curl request to the server:

    $ token="$(curl -k -X POST -d "demo" https://conjur/api/authn/users/demo/authenticate | base64)"
    $ curl -H "Authorization: Token token=\"$token\"" localhost:8080/hello-world
