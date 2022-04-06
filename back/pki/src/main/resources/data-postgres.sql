--Loinke za logovanje svih korisnika su istog naziva kao i korisničko ime
--Primer po jednog korisnika za svaku rolu: Customer - username: milica, password: milica, WeekendHouseOwner - username: antonije, password: antonije
--BoatOwner - username: nikola, password: nikola, Instructor - username: borisa, password: borisa

SET datestyle = dmy;
insert into role (name) values ('ROLE_CUSTOMER');
insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_BOAT_OWNER');
insert into role (name) values ('ROLE_INSTRUCTOR');
insert into role (name) values ('ROLE_WEEKEND_HOUSE_OWNER');


insert into customer (category, discount, points, version, penals_reseting_date, address, city, country, email, first_name, is_activated, is_deleted, last_name, password, phone, username, want_deleting, hash_code, penals, role_id)
    values ('Silver', 10, 45, 0, '03.01.2022 14:00:00', 'Presernova 3', 'Bled', 'Slovenia', 'health.care.clinic.psw+milica@gmail.com', 'Milica', true, false, 'Antic', '$2a$10$hd.PcSgRocME1rIrpLhXWOO/uKacPl4oyjf3k5DGHaBhesm6wC3SK', '0651234432', 'milica', false, '', 2, 1);
insert into customer (category, discount, points, version, penals_reseting_date, address, city, country, email, first_name, is_activated, is_deleted, last_name, password, phone, username, want_deleting, hash_code, penals, role_id)
    values ('Bronze', 0, 25, 0, '03.01.2021 14:00:00', 'Masarikova 23', 'Novi Sad', 'Serbia', 'health.care.clinic.psw+predrag@gmail.com', 'Predrag', true, false, 'Miric', '$2a$10$l.fpbmzdA7ooTKv5nb5vheG7fX5lN2jxVQMUdsANPpVpGR4q7fDvy', '0656544432', 'predrag', false, '', 4, 1);

--------- WEEKEND HOUSE
insert into weekend_house_owner (address, city, country, email, first_name, is_activated, is_deleted, last_name, password, phone, username, want_deleting, motive, role_id)
    values ('Makarska 42', 'Beograd', 'Serbia', 'health.care.clinic.psw+antonije@gmail.com', 'Antonije', true, false, 'Pusic', '$2a$10$tfmCyLyvVUpTAqmApmZwUej3aT3WiUVOQu8sx1/dk0AE3LHrqJVnC', '0654677567', 'antonije', false, '', 5);


insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Lovcenska 23', 5, 'Mnogo lepa vikendica', '123edas123', 'Heaven house', 1235, 'Be good', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Partizanska 44', 15, 'Izuzetno lepa vikendica', '123edas123', 'Hell house', 12135, 'Be bad', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Rumenacka 2', 7, 'Fantastic', '123edas123', 'Paradise island', 6000, 'Be good', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Novosadska 144', 4, 'Nice view', '123edas123', 'Sunny valley', 4300, 'Be bad', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Grmuska 2', 5, 'Bas je lijepa', '123edas123', 'Orthodox', 6900, 'Be good', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Cirpanova 4', 9, 'Vrh tebra', '123edas123', 'Great Serbia', 10000, 'Be bad', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Hadzi Ruvimova 14', 4, 'Nice view', '123edas123', 'Sunny mount', 4000, 'Be bad', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Presernova 2', 5, 'Bas je lijepa', '123edas123', 'House', 6000, 'Be good', 1);

insert into weekend_house (address, bed_number, description, image_path, name, price, rules, weekend_house_owner_id)
    values ('Filipa Filipovica 4', 2, 'Vrh tebra', '123edas123', 'Cheese', 1000, 'Be bad', 1);


insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('27.02.2022 14:00:00', null, 4, 23000, '23.02.2022 14:00:00', '21.02.2022 14:00:00', 1, 1, false);

insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('07.02.2022 14:00:00', null, 7, 16000, '03.02.2022 14:00:00', '21.02.2022 14:00:00', 2, 2, false);

insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('28.02.2022 14:00:00', null, 7, 16000, '23.02.2022 14:00:00', '21.02.2022 14:00:00', 1, 2, true);

insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('08.01.2022 14:00:00', null, 7, 16000, '03.01.2022 14:00:00', '21.12.2021 14:00:00', 1, 5, false);

insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('18.01.2021 14:00:00', null, 7, 16000, '03.01.2021 14:00:00', '21.12.2021 14:00:00', 1, 7, false);

insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('18.04.2022 14:00:00', '30.01.2022 14:00:00', 7, 16000, '29.03.2022 14:00:00', '08.01.2022 14:00:00', null, 7, false);

insert into weekend_house_reservation(end_date_time, end_special_offer, people_number, price, start_date_time, start_special_offer, customer_id, weekend_house_id, is_cancelled)
    values ('10.04.2022 14:00:00', '30.01.2022 14:00:00', 4, 26000, '09.03.2022 14:00:00', '16.01.2022 14:00:00', null, 3, false);



INSERT INTO additional_service(name, price) VALUES ('WiFi', 200);
INSERT INTO additional_service(name, price) VALUES ('Klima', 1000);
INSERT INTO additional_service(name, price) VALUES ('Mini bar', 2000);
INSERT INTO additional_service(name, price) VALUES ('Jakuzi', 5000);
INSERT INTO additional_service(name, price) VALUES ('Sporet', 300);

INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (1, 1);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (1, 2);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (2, 2);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (3, 2);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (3, 3);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (4, 4);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (5, 5);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (6, 5);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (7, 4);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (7, 5);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (8, 5);
INSERT INTO weekend_house_services(weekend_house_id, service_id) VALUES (9, 2);

INSERT INTO term(end_date_time, start_date_time, weekend_house_id) VALUES ('18.01.2022 14:00:00', '10.01.2022 14:00:00', 1);
INSERT INTO term(end_date_time, start_date_time, weekend_house_id) VALUES ('04.02.2022 14:00:00', '20.01.2022 14:00:00', 1);
INSERT INTO term(end_date_time, start_date_time, weekend_house_id) VALUES ('18.02.2022 14:00:00', '10.02.2022 14:00:00', 2);
INSERT INTO term(end_date_time, start_date_time, weekend_house_id) VALUES ('04.02.2022 14:00:00', '30.01.2022 14:00:00', 3);

INSERT INTO weekend_house_feedbacks(grade_house, grade_owner, is_approved, note_house, note_owner, weekend_house_reservation_id)
            VALUES (6, 7, true, 'I have expected better..', 'Okey', 1);
INSERT INTO weekend_house_feedbacks(grade_house, grade_owner, is_approved, note_house, note_owner, weekend_house_reservation_id)
            VALUES (9, 7, true, 'Nice', 'Okey', 1);
INSERT INTO weekend_house_feedbacks(grade_house, grade_owner, is_approved, note_house, note_owner, weekend_house_reservation_id)
            VALUES (6, 7, true, 'Not bad', 'Hmm', 4);

--------- BOAT
INSERT INTO boat_owner(address, city, country, email, first_name, is_activated, is_deleted, last_name, password, phone, username, want_deleting, motive, role_id)
	VALUES ('Brdarska 142', 'Beograd', 'Serbia', 'health.care.clinic.psw+nikola@gmail.com', 'Nikola', true, false, 'Karic', '$2a$10$797QMButu1E5Qu6psORITOWE8gLRrxTaAVF6roezaNwAP5iDv7gNe', '0654477567', 'nikola', false, 'ehh', 3);

INSERT INTO boat_owner(address, city, country, email, first_name, is_activated, is_deleted, last_name, password, phone, username, want_deleting, motive, role_id)
	VALUES ('Gogoljeva 14', 'Novi Sad', 'Serbia', 'health.care.clinic.psw+andrija@gmail.com', 'Andrija', true, false, 'Boric', '$2a$10$Va5zIXmRLx/8wn5A291rdeRuoZqPADGaFs.aIXBDjA3atgrDvbzcG', '0654477522', 'andrija', false, 'ehh', 3);


INSERT INTO boat(address, capacity, description, image_path, name, price, rules, boat_owner_id)
	values ('Sumadijska 23', 5, 'Mnogo lep brod', '123edas123', 'Heaven boat', 12000, 'Be good', 1);
INSERT INTO boat(address, capacity, description, image_path, name, price, rules, boat_owner_id)
	values ('Grncarska 3', 15, 'Zjuu lepote', '123edas123', 'Goddess', 34000, 'Be awesome', 1);
INSERT INTO boat(address, capacity, description, image_path, name, price, rules, boat_owner_id)
	values ('Rumenacka 23', 25, 'Dodji pa proceni', '123edas123', 'Brlja', 41000, 'Be nice', 2);


INSERT INTO boat_reservation(capacity, end_date_time, end_special_offer, is_cancelled, price, start_date_time, start_special_offer, boat_id, customer_id)
	values (2, '27.02.2022 14:00:00', null, false, 23000, '23.02.2022 14:00:00', '21.02.2022 14:00:00', 1, 1);
INSERT INTO boat_reservation(capacity, end_date_time, end_special_offer, is_cancelled, price, start_date_time, start_special_offer, boat_id, customer_id)
	values (5, '20.02.2022 14:00:00', null, false, 28000, '13.02.2022 14:00:00', '21.02.2022 14:00:00', 2, 1);
INSERT INTO boat_reservation(capacity, end_date_time, end_special_offer, is_cancelled, price, start_date_time, start_special_offer, boat_id, customer_id)
	values (5, '24.02.2022 14:00:00', '20.01.2022 14:00:00', false, 28000, '23.02.2022 14:00:00', '11.01.2022 14:00:00', 2, null);

INSERT INTO boat_services(boat_id, service_id) VALUES (1, 2);
INSERT INTO boat_services(boat_id, service_id) VALUES (1, 5);
INSERT INTO boat_services(boat_id, service_id) VALUES (2, 4);
INSERT INTO boat_services(boat_id, service_id) VALUES (2, 3);
INSERT INTO boat_services(boat_id, service_id) VALUES (2, 1);
INSERT INTO boat_services(boat_id, service_id) VALUES (1, 1);

INSERT INTO term_boat(end_date_time, start_date_time, boat_id) VALUES ('18.01.2022 14:00:00', '10.01.2022 14:00:00', 1);
INSERT INTO term_boat(end_date_time, start_date_time, boat_id) VALUES ('22.01.2022 14:00:00', '20.01.2022 14:00:00', 1);
INSERT INTO term_boat(end_date_time, start_date_time, boat_id) VALUES ('18.01.2022 14:00:00', '10.01.2022 14:00:00', 2);
INSERT INTO term_boat(end_date_time, start_date_time, boat_id) VALUES ('18.02.2022 14:00:00', '02.02.2022 14:00:00', 2);

INSERT INTO boat_feedbacks(grade_boat, grade_owner, is_approved, note_boat, note_owner, boat_reservation_id)
	VALUES (6, 7, true, 'I have expected better services..', 'Okey', 1);
INSERT INTO boat_feedbacks(grade_boat, grade_owner, is_approved, note_boat, note_owner, boat_reservation_id)
	VALUES (10, 8, true, 'Not bad', 'Okey', 1);
INSERT INTO boat_feedbacks(grade_boat, grade_owner, is_approved, note_boat, note_owner, boat_reservation_id)
	VALUES (9, 9, true, 'Very good', 'Nice', 2);


--------- FISHING
INSERT INTO instructor(address, city, country, email, first_name, is_activated, is_deleted, last_name, password, phone, username, want_deleting, motive, role_id)
	VALUES ('Sombroska 12', 'Subotica', 'Serbia', 'health.care.clinic.psw+borisa@gmail.com', 'Borisa', true, false, 'Stojic', '$2a$10$TwjDOLQ3Z3tRBeYs6soNZO8hn6b69ocY4lMCadxjaRP7RwGEDgeyW', '0654471567', 'borisa', false, 'Good', 4);


INSERT INTO fishing_lesson(address, cancel_conditions, description, fishing_equipment, image_paths, max_number_of_people, name, price, rules, fishing_lesson_instructor_id)
	VALUES ('Uzicka 213', 'Mogu da otkazem', 'Dodji pa proceni', 'Stap za pecanje, crvi', '123edas123', 4, 'River heaven', 3500, 'Be nice, be good', 1);
INSERT INTO fishing_lesson(address, cancel_conditions, description, fishing_equipment, image_paths, max_number_of_people, name, price, rules, fishing_lesson_instructor_id)
	VALUES ('Suboticka 23', 'Mogu da otkazem', 'Dodji pa proceni', 'Stap za pecanje, crvi', '123edas123', 6, 'Lake hell', 5500, 'Be nice, be good', 1);

INSERT INTO fishing_lesson_reservation(end_date_time, end_special_offer, is_cancelled, max_people_number, price, start_date_time, start_special_offer, customer_id, fishing_lesson_id)
	VALUES ('20.02.2022 14:00:00', null, false, 4, 28000, '13.02.2022 14:00:00', '21.02.2022 14:00:00', 2, 1);
INSERT INTO fishing_lesson_reservation(end_date_time, end_special_offer, is_cancelled, max_people_number, price, start_date_time, start_special_offer, customer_id, fishing_lesson_id)
	values ('27.02.2022 14:00:00', null, false, 2, 23000, '23.02.2022 14:00:00', '21.02.2022 14:00:00', 1, 2);
INSERT INTO fishing_lesson_reservation(end_date_time, end_special_offer, is_cancelled, max_people_number, price, start_date_time, start_special_offer, customer_id, fishing_lesson_id)
	values ('27.03.2022 14:00:00', null, false, 2, 23000, '23.03.2022 14:00:00', '21.02.2022 14:00:00', 1, 2);
INSERT INTO fishing_lesson_reservation(end_date_time, end_special_offer, is_cancelled, max_people_number, price, start_date_time, start_special_offer, customer_id, fishing_lesson_id)
	values ('07.01.2022 14:00:00', null, false, 2, 23000, '03.01.2022 14:00:00', '21.02.2022 14:00:00', 1, 2);
INSERT INTO fishing_lesson_reservation(end_date_time, end_special_offer, is_cancelled, max_people_number, price, start_date_time, start_special_offer, customer_id, fishing_lesson_id)
	values ('04.01.2022 14:00:00', null, false, 2, 23000, '01.01.2022 14:00:00', '21.02.2022 14:00:00', 1, 1);
INSERT INTO fishing_lesson_reservation(end_date_time, end_special_offer, is_cancelled, max_people_number, price, start_date_time, start_special_offer, customer_id, fishing_lesson_id)
	values ('04.03.2022 14:00:00', '24.01.2022 14:00:00', false, 2, 23000, '01.03.2022 14:00:00', '11.01.2022 14:00:00', null, 1);

INSERT INTO fishing_lesson_services(fishing_lesson_id, service_id) VALUES (1, 2);
INSERT INTO fishing_lesson_services(fishing_lesson_id, service_id) VALUES (1, 5);
INSERT INTO fishing_lesson_services(fishing_lesson_id, service_id) VALUES (2, 4);
INSERT INTO fishing_lesson_services(fishing_lesson_id, service_id) VALUES (2, 3);
INSERT INTO fishing_lesson_services(fishing_lesson_id, service_id) VALUES (2, 1);
INSERT INTO fishing_lesson_services(fishing_lesson_id, service_id) VALUES (1, 1);

INSERT INTO term_fishing_lesson(end_date_time, start_date_time, fishing_lesson_id) VALUES ('18.01.2022 14:00:00', '10.01.2022 14:00:00', 1);
INSERT INTO term_fishing_lesson(end_date_time, start_date_time, fishing_lesson_id) VALUES ('22.01.2022 14:00:00', '20.01.2022 14:00:00', 1);
INSERT INTO term_fishing_lesson(end_date_time, start_date_time, fishing_lesson_id) VALUES ('18.01.2022 14:00:00', '10.01.2022 14:00:00', 2);
INSERT INTO term_fishing_lesson(end_date_time, start_date_time, fishing_lesson_id) VALUES ('18.02.2022 14:00:00', '02.02.2022 14:00:00', 2);

INSERT INTO fishing_lesson_feedbacks(grade_fishing_lesson, grade_owner, is_approved, note_fishing_lesson, note_owner, fishing_lesson_reservation_id)
	VALUES (6, 7, true, 'I have expected better services..', 'Okey', 1);
INSERT INTO fishing_lesson_feedbacks(grade_fishing_lesson, grade_owner, is_approved, note_fishing_lesson, note_owner, fishing_lesson_reservation_id)
	VALUES (10, 8, true, 'Not bad', 'Okey', 1);
INSERT INTO fishing_lesson_feedbacks(grade_fishing_lesson, grade_owner, is_approved, note_fishing_lesson, note_owner, fishing_lesson_reservation_id)
	VALUES (9, 9, true, 'Very good', 'Nice', 2);


---------SUBSCRIPTIONS
INSERT INTO customers_subscriptions_boat(boat_id, customer_id) VALUES (1, 1);
INSERT INTO customers_subscriptions_fishing_lessons(fishing_lessons_id, customer_id) VALUES (1, 1);
INSERT INTO customers_subscriptions_weekend_house(weekend_house_id, customer_id) VALUES (1, 1);
INSERT INTO customers_subscriptions_weekend_house(weekend_house_id, customer_id) VALUES (3, 1);
INSERT INTO customers_subscriptions_weekend_house(weekend_house_id, customer_id) VALUES (2, 1);
INSERT INTO customers_subscriptions_weekend_house(weekend_house_id, customer_id) VALUES (2, 2);

