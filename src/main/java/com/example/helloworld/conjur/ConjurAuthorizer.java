package com.example.helloworld.conjur;

import io.dropwizard.auth.Authorizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.conjur.api.Conjur;
import net.conjur.api.Role;

public class ConjurAuthorizer implements Authorizer<ConjurPrincipal> {
    final static Logger logger = LoggerFactory.getLogger(ConjurAuthorizer.class);

    @Override
    public boolean authorize(ConjurPrincipal conjurPrincipal, String privilege) {
        logger.info("Got authz req: " + privilege);

        final Conjur conjur = conjurPrincipal.getConjur();

        logger.info("Username is: " + conjur.getAccount());

        final Role myRole = conjur.authorization().getCurrentRole();
        return myRole.isPermitted("webservice:hello-world", privilege.toLowerCase());
    }
}