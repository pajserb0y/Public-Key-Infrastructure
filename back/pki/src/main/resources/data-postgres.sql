insert into role (id, name) values (1, 'ROLE_END_USER');
insert into role (id, name) values (2, 'ROLE_ADMIN');
insert into role (id, name) values (3, 'ROLE_INTER_USER');

insert into permission (id, name) values (1, 'getAllCertificates');
insert into permission (id, name) values (2, 'getAllCACertificates');
insert into permission (id, name) values (3, 'downloadCertificate');
insert into permission (id, name) values (4, 'userByEmail');
insert into permission (id, name) values (5, 'updateUser');

insert into role_permissions (role_id, permission_id) values (2, 1);
insert into role_permissions (role_id, permission_id) values (2, 2);
insert into role_permissions (role_id, permission_id) values (3, 2);
insert into role_permissions (role_id, permission_id) values (1, 3);
insert into role_permissions (role_id, permission_id) values (2, 3);
insert into role_permissions (role_id, permission_id) values (3, 3);
insert into role_permissions (role_id, permission_id) values (1, 4);
insert into role_permissions (role_id, permission_id) values (2, 4);
insert into role_permissions (role_id, permission_id) values (3, 4);
insert into role_permissions (role_id, permission_id) values (1, 5);
insert into role_permissions (role_id, permission_id) values (2, 5);
insert into role_permissions (role_id, permission_id) values (3, 5);

insert into users (id, email, first_name, last_name, password, role_id, is_activated, pin, is_blocked, salt, blocked_date, forgotten, missed_password_counter)
    values (1, 'health.care.clinic.psw+milica@gmail.com', 'Milica', 'Antic', '$2a$10$pyqb/.81/t6Sm0yM3HjFZeedBzeWpbZwMYD2bgI0J5TC/50DclFJ2', 1, true, '1111', false, 'asd', null, 0, 0);
insert into users (id, email, first_name, last_name, password, role_id, is_activated, pin, is_blocked, salt, blocked_date, forgotten, missed_password_counter)
    values (2, 'health.care.clinic.psw+admin@gmail.com', 'Admin', 'Admirovic', '$2a$10$H3iV53z/MFMe.bLYxJmRWu6aGB626E4AsrYbOteNFtAYQmmo5qIA.', 2, true, '1111', false, 'asd', null, 0, 0);

