DELETE FROM user_meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- password
INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');
-- admin
INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 100000);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_ADMIN', 100001);

INSERT INTO user_meals (description, calories, user_id) VALUES ('description1', 555, 100000);
INSERT INTO user_meals (description, calories, user_id) VALUES ('description2', 1000, 100000);
INSERT INTO user_meals (description, calories, user_id) VALUES ('description3', 200, 100000);

INSERT INTO user_meals (description, calories, user_id) VALUES ('description1', 555, 100001);
INSERT INTO user_meals (description, calories, user_id) VALUES ('description2', 1000, 100001);
INSERT INTO user_meals (description, calories, user_id) VALUES ('description3', 200, 100001);
