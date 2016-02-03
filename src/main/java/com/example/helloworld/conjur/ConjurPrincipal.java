package com.example.helloworld.conjur;

import java.security.Principal;

import net.conjur.api.Conjur;

public class ConjurPrincipal implements Principal {
    private final Conjur conjur;

    public ConjurPrincipal(Conjur conjur) {
        this.conjur = conjur;
    }

    public Conjur getConjur() {
        return conjur;
    } 

    @Override
    public String getName() {
        return conjur.getAuthn().getUsername();
    }
}