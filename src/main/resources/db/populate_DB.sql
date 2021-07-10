DELETE FROM vote;
DELETE FROM dish;
DELETE FROM menu;
DELETE FROM restaurant;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, role)
VALUES ('user1', 'usesq1@gmail.com', '{noop}password', 'USER'), -- 100000
       ('admin1', 'ad1@mail.ru', '{noop}password', 'ADMIN'), -- 100001
       ('user2', 'usexz2@mail.ru', '{noop}password', 'USER'), -- 100002
       ('admin2', 'adm2@gmail.com', '{noop}password', 'ADMIN'); -- 100003

INSERT INTO restaurant (name)
VALUES ('Turkish'), -- 100004
       ('Arabic'), -- 100005
       ('Moldovan'), -- 100006
       ('Japanese'), -- 100007
       ('Russian'); -- 100008

INSERT INTO menu (date, restaurant_id)
VALUES ('2021-06-26', 100004), -- 100009
       ('2021-06-26', 100005), -- 100010
       (now(), 100006), -- 100011
       ('2021-06-26', 100007); -- 100012
INSERT INTO menu (date, restaurant_id, enabled)
VALUES (now(), 100008, false); -- 100013

INSERT INTO dish (name, price, menu_id)
VALUES ('Turkish dish 2', 5000, 100009),
       ('Turkish dish 1', 9000, 100009),
       ('Turkish dish 3', 6000, 100009),
       ('Arabic dish 1', 14000, 100010),
       ('Arabic dish 2', 10000, 100010),
       ('Moldovan dish 1', 4000, 100011),
       ('Moldovan dish 3', 17000, 100011),
       ('Moldovan dish 2', 9000, 100011),
       ('Japanese dish 1', 8000, 100012),
       ('Japanese dish 2', 20000, 100012),
       ('Japanese dish 3', 7500, 100012),
       ('Japanese dish 4', 5500, 100012);
INSERT INTO dish (name, price, menu_id, enabled)
VALUES ('Russian dish 1', 30000, 100013, false);

INSERT INTO vote (date, menu_id, user_id)
VALUES ('2021-06-26', 100011, 100000),
       ('2021-06-26', 100012, 100002),
       (now(), 100009, 100000),
       (now(), 100009, 100001),
       ('2021-06-26', 100012, 100003);


