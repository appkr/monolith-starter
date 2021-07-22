INSERT INTO users (id, username, password, email, enabled)
VALUES (1, 'user', '$2a$10$vXluNNME63bAv5Qo0OPC5Ol6ZfFdHAzc/zbznPyCusdHo3cK4tfFu', 'user@example.com', 1),
       (2, 'admin', '$2a$10$L0mSs4QKcd.iOthYG/kT8..TvBjYcFYEwX0xT87IYlZ14wvRoG6Si', 'admin@example.com', 1); -- the password is "password"

INSERT INTO authorities(id, authority)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO authority_user(authority_id, user_id)
VALUES (1, 1),
       (2, 2);