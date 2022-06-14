insert into role (id, name) values (1, 'ROLE_END_USER');
insert into role (id, name) values (2, 'ROLE_ADMIN');
insert into role (id, name) values (3, 'ROLE_INTER_USER');

insert into permission (id, name) values (1, 'getAllCertificates');
insert into permission (id, name) values (2, 'getAllCACertificates');
insert into permission (id, name) values (3, 'downloadCertificate');

insert into role_permissions (role_id, permission_id) values (2, 1);
insert into role_permissions (role_id, permission_id) values (2, 2);
insert into role_permissions (role_id, permission_id) values (3, 2);
insert into role_permissions (role_id, permission_id) values (1, 3);
insert into role_permissions (role_id, permission_id) values (2, 3);
insert into role_permissions (role_id, permission_id) values (3, 3);

insert into users (id, email, first_name, last_name, password, role_id, is_activated, pin, is_blocked, salt, blocked_date, forgotten, missed_password_counter)
    values (1, 'health.care.clinic.psw+milica@gmail.com', 'Milica', 'Antic', '$2a$10$hd.PcSgRocME1rIrpLhXWOO/uKacPl4oyjf3k5DGHaBhesm6wC3SK', 1, true, '1111', false, '', null, 0, 0);
insert into users (id, email, first_name, last_name, password, role_id, is_activated, pin, is_blocked, salt, blocked_date, forgotten, missed_password_counter)
    values (2, 'health.care.clinic.psw+admin@gmail.com', 'Admin', 'Admirovic', '$2a$10$FsvEMdKbdUaGEUtS84/29.3ys68hNwve0jPcfjAWnzIHsuOJi00iK', 2, true, '1111', false, '', null, 0, 0);

