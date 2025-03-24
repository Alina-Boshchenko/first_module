package ru.boshchenko.oauth2.projections;

public interface UserFull {
    Long getId();
    String getProviderId();
    String getProvider();
    String getUsername();
    String getEmail();
}
