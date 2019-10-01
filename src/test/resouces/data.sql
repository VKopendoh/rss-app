delete from user_role;
delete from usr;
delete from rss_link;

insert into usr(id, username, password, active) values
(1, 'User', 'pass', true),
(2, 'User2', 'pass2', true);

insert into user_role(user_id, roles) values
(1, 'USER'),
(2, 'USER');

insert into rss_link(url,pub_time) values
('https://testguild.com/feed/', TO_DATE('17/12/2015', 'DD/MM/YYYY'));
