package com.example.helloworld;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotEmpty
    private String applianceUrl;

    @NotEmpty
    private String conjurIdentity;

    @NotEmpty
    private String conjurApiKey;

    @NotEmpty
    private String databaseUser;

    @NotEmpty
    private String databasePassword;

    @NotEmpty
    private String databaseUrl;

    @NotEmpty
    private String databaseName;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    public String getApplianceUrl() {
        return applianceUrl;
    }

    public String getConjurIdentity() {
        return conjurIdentity;
    }

    public String getConjurApiKey() {
        return conjurApiKey;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}