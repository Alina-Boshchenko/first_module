INSERT INTO users (username, password, is_account_non_locked, failed_login_attempts, roles)
VALUES
    ( 'user@test.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', true, 0, 'USER'),
    ( 'admin@test.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', true, 0, 'SUPER_ADMIN'),
    ( 'moderator@test.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', true, 0, 'MODERATOR');