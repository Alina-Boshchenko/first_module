INSERT INTO users (id, username, password, account_non_locked, failed_login_attempts, role)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'user@test.com', '$2a$10$zf20v7TmmBqokFPsj/rCduC9RSVPHOHl4Ily20UINfK9uY/FE6WaK',
     true, 0,'USER'),

    ('4680fbf8-254f-4b9d-95f8-f5be1d2816c3', 'admin@test.com', '$2a$10$zf20v7TmmBqokFPsj/rCduC9RSVPHOHl4Ily20UINfK9uY/FE6WaK',
     true, 0,'SUPER_ADMIN'),

    ('4a7485b7-79a7-4a0b-929a-67adca7594c4', 'moderator@test.com', '$2a$10$zf20v7TmmBqokFPsj/rCduC9RSVPHOHl4Ily20UINfK9uY/FE6WaK',
     true, 0,'MODERATOR');