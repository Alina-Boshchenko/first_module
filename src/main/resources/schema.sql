CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
                       failed_login_attempts INT NOT NULL DEFAULT 0
);