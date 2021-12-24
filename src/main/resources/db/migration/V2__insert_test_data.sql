insert into users (id, code, email, is_moderator, name, password, photo, reg_time) values (1, '111', '1@gmail.com', 1 ,  'John Kar', 'yaJohn','photo', '2020-6-24 00:00:00');
insert into users (id, code, email, is_moderator, name, password, photo, reg_time) values (2, '222', '1@gmail.com', 0 ,  'David Young', 'yaDavid','photo', '2019-6-24 00:00:00');
insert into users (id, code, email, is_moderator, name, password, photo, reg_time) values (3, '333', '1@gmail.com', 0 ,  'Roger Perry', 'yaRoger','photo', '2018-6-24 00:00:00');
insert into users (id, code, email, is_moderator, name, password, photo, reg_time) values (4, '444', '1@gmail.com', 1 ,  'John Fields', 'yaJohn','photo', '2017-6-24 00:00:00');
insert into users (id, code, email, is_moderator, name, password, photo, reg_time) values (5, '555', '1@gmail.com', 0 ,  'Karen Murray', 'yaKaren','photo', '2016-6-24 00:00:00');
insert into users (id, code, email, is_moderator, name, password, photo, reg_time) values (6, '666', '1@gmail.com', 1 ,  'Jeanette Gomez', 'yaJeanette','photo', '2015-6-24 00:00:00');

insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    values(1, 1, 'ACCEPTED',
        'Sony представила многослойную технологию компоновки датчиков CMOS.
            В отличие от традиционных сенсоров, где фотодиоды и пиксельные
            транзисторы размещаются на одной подложке, инженеры впервые
            разделили эти компоненты по разным слоям', '2020-10-10' ,
        'Sony представила многослойную технологию компоновки датчиков CMOS', 10,  1, 2);
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    values(2, 1, 'ACCEPTED',
        'В результате удалось удвоить уровень насыщения каждого пикселя
            и расширить динамический диапазон — эти показатели играют
            важную роль в формировании изображения в ограниченном по габаритам датчике', '2019-9-11' ,
        'В результате', 14,  4, 2);
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    values(3, 0, 'ACCEPTED',
        'Кроме того, поскольку пиксельные транзисторы, включая
            RST, SEL и AMP, теперь занимают отдельный слой, их
            размеры тоже удалось увеличить, тем самым снизив
            уровень шума при съёмке в условиях низкого освещения и в ночное время.', '2020-1-12' ,
        'Sony представила многослойную технологию компоновки датчиков CMOS', 10,  1, 2);
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    values(4, 0, 'ACCEPTED',
        'Стоит отметить, что Sony раскрыла саму технологию, но не
            продемонстрировала полноценное устройство, поэтому сроки
            внедрения остаются под вопросом. Датчики планируют задействовать
            не только в полнокадровых камерах, но и смартфонах. Напомним,
            Sony лидирует по поставкам сенсоров CMOS для мобильного рынка с долей в 42%.', '2020-12-12' ,
        'Стоит отметит', 15,  1, 4);
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    values(5, 1, 'DECLINED',
        'Ранее стало известно, что Canon разработала датчик изображения,
            который способен делать полноцветные снимки в темноте за
            «доли наносекунд». Массовое производство начнётся в 2022 году.', '2021-9-9' ,
        'способен делать ', 15,  4, 5);

insert into post_votes (id, time, value, post_id, user_id) values (1, '2021-1-1', 1, 1, 1);
insert into post_votes (id, time, value, post_id, user_id) values (2, '2021-1-1', 1, 1, 2);
insert into post_votes (id, time, value, post_id, user_id) values (3, '2021-1-1', 1, 1, 3);
insert into post_votes (id, time, value, post_id, user_id) values (4, '2021-1-1', 0, 1, 4);
insert into post_votes (id, time, value, post_id, user_id) values (5, '2021-1-1', 0, 1, 5);
insert into post_votes (id, time, value, post_id, user_id) values (6, '2021-1-1', 1, 2, 1);
insert into post_votes (id, time, value, post_id, user_id) values (7, '2021-1-1', 0, 2, 2);
insert into post_votes (id, time, value, post_id, user_id) values (8, '2021-1-1', 0, 2, 3);
insert into post_votes (id, time, value, post_id, user_id) values (9, '2021-1-1', 1, 2, 4);
insert into post_votes (id, time, value, post_id, user_id) values (10, '2021-1-1', 1, 2, 5);
insert into post_votes (id, time, value, post_id, user_id) values (11, '2021-1-1', 0, 3, 1);
insert into post_votes (id, time, value, post_id, user_id) values (12, '2021-1-1', 0, 3, 2);
insert into post_votes (id, time, value, post_id, user_id) values (13, '2021-1-1', 0, 3, 3);
insert into post_votes (id, time, value, post_id, user_id) values (14, '2021-1-1', 1, 3, 4);
insert into post_votes (id, time, value, post_id, user_id) values (15, '2021-1-1', 0, 3, 5);
insert into post_votes (id, time, value, post_id, user_id) values (16, '2021-1-1', 0, 4, 1);
insert into post_votes (id, time, value, post_id, user_id) values (17, '2021-1-1', 1, 4, 2);
insert into post_votes (id, time, value, post_id, user_id) values (18, '2021-1-1', 1, 4, 3);
insert into post_votes (id, time, value, post_id, user_id) values (19, '2021-1-1', 1, 4, 4);
insert into post_votes (id, time, value, post_id, user_id) values (20, '2021-1-1', 1, 4, 5);
insert into post_votes (id, time, value, post_id, user_id) values (21, '2021-1-1', 1, 5, 1);
insert into post_votes (id, time, value, post_id, user_id) values (22, '2021-1-1', 1, 5, 2);
insert into post_votes (id, time, value, post_id, user_id) values (23, '2021-1-1', 1, 5, 3);
insert into post_votes (id, time, value, post_id, user_id) values (24, '2021-1-1', 1, 5, 4);
insert into post_votes (id, time, value, post_id, user_id) values (25, '2021-1-1', 1, 5, 5);

insert into post_comments (id, text, time, parent_id, post_id, user_id) values (1, 'jfff', '2021-1-1', null, 1, 1);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (2, 'раз', '2021-2-1', null, 1, 1);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (3, 'два', '2021-3-1', null, 2, 2);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (4, 'три', '2021-4-1', null, 2, 3);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (5, 'четыре', '2021-5-1', null, 1, 4);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (6, 'пять', '2021-6-1', null, 4, 4);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (7, 'ыфыа', '2021-7-1', null, 3, 2);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (8, 'йцук', '2021-8-1', null, 5, 2);
insert into post_comments (id, text, time, parent_id, post_id, user_id) values (9, 'нннн', '2021-9-1', null, 1, 3);

insert into tags (id, name) values (1,'one');
insert into tags (id, name) values (2,'two');
insert into tags (id, name) values (3,'three');

insert into tag2post(id, post_id, tag_id) values (1, 1, 1);
insert into tag2post(id, post_id, tag_id) values (2, 2, 2);
insert into tag2post(id, post_id, tag_id) values (3, 3, 1);
insert into tag2post(id, post_id, tag_id) values (4, 4, 3);
insert into tag2post(id, post_id, tag_id) values (5, 5, 3);
