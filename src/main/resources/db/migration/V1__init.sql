create table captcha_codes (id integer not null AUTO_INCREMENT, code TINYTEXT not null, secret_code TINYTEXT not null, time DATETIME not null, primary key (id));
create table global_settings (id integer not null AUTO_INCREMENT, code VARCHAR(255) not null, name VARCHAR(255) not null, value VARCHAR(255) not null, primary key (id));
create table post_comments (id integer not null AUTO_INCREMENT, text varchar(255) not null, time DATETIME not null, parent_id integer, post_id integer not null, user_id integer not null, primary key (id));
create table post_votes (id integer not null AUTO_INCREMENT, time DATETIME not null, value TINYINT not null, post_id integer not null, user_id integer not null, primary key (id));
create table posts (id integer not null AUTO_INCREMENT, is_active tinyint not null, moderation_status enum('NEW', 'ACCEPTED', 'DECLINED') not null, text TEXT not null, time datetime not null, title VARCHAR(255) not null, view_count integer not null, moderator_id integer, user_id integer, primary key (id));
create table tag2post (id integer not null AUTO_INCREMENT, post_id integer not null, tag_id integer not null, primary key (id));
create table tags (id integer not null AUTO_INCREMENT, name VARCHAR(255), primary key (id));
create table users (id integer not null AUTO_INCREMENT, code VARCHAR(255), email VARCHAR(255) not null, is_moderator TINYINT not null, name VARCHAR(255) not null, password VARCHAR(255) not null, photo TEXT, reg_time DATETIME not null, primary key (id));

ALTER TABLE `posts`
ADD FULLTEXT INDEX `fulltext_search_index` (`title`, `text`) VISIBLE;