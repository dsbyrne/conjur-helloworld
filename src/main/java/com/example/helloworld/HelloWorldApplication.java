package com.example.helloworld;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.auth.Authenticator;

import com.example.helloworld.conjur.ConjurAuthFilter;
import com.example.helloworld.conjur.ConjurAuthorizer;
import com.example.helloworld.conjur.ConjurAuthenticator;
import com.example.helloworld.resources.HelloWorldResource;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
            configuration.getTemplate(),
            configuration.getDefaultName()
        );

        final ConjurAuthFilter conjurAuthFilter = new ConjurAuthFilter.Builder<>()
            .setAuthenticator(new ConjurAuthenticator())
            .setAuthorizer(new ConjurAuthorizer())
            .buildAuthFilter();

        environment.jersey().register(conjurAuthFilter);
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(resource);
    }

}