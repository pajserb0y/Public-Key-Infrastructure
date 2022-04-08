insert into role (id, name) values (1, 'ROLE_ADMIN');
insert into role (id, name) values (2, 'ROLE_ROOT');
insert into role (id, name) values (3, 'ROLE_CA');
insert into role (id, name) values (4, 'ROLE_END');

insert into users (id, email, first_name, last_name, password, role_id)
    values (1, 'health.care.clinic.psw+milica@gmail.com', 'Milica', 'Antic', '$2a$10$hd.PcSgRocME1rIrpLhXWOO/uKacPl4oyjf3k5DGHaBhesm6wC3SK', 1);
insert into users (id, email, first_name, last_name, password, role_id)
    values (2, 'health.care.clinic.psw+admin@gmail.com', 'Admin', 'Admirovic', '$2a$10$FsvEMdKbdUaGEUtS84/29.3ys68hNwve0jPcfjAWnzIHsuOJi00iK', 2);