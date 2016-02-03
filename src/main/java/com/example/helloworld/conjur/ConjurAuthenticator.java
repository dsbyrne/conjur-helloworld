package com.example.helloworld.conjur;

import com.google.common.base.Optional;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.AuthenticationException;

import net.conjur.api.Conjur;

public class ConjurAuthenticator implements Authenticator<Conjur, ConjurPrincipal> {
    @Override
    public Optional<ConjurPrincipal> authenticate(Conjur conjur) throws AuthenticationException {
        if (conjur != null) {
            return Optional.of(new ConjurPrincipal(conjur));
        }
        return Optional.absent();
    }
}