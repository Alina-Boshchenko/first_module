package ru.boshchenko.oauth2.projections;

public interface UserProfileDTO {
    String getProvider();
    String getUsername();
    String getEmail();
}
