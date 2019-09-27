DROP TABLE IF EXISTS rss;

CREATE TABLE rss (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  url VARCHAR(250) NOT NULL,
);

INSERT INTO rss (url) VALUES
  ('https://news.yandex.ru/auto.rss'),
   ('https://news.yandex.ru/politics.rss'),
    ('https://news.yandex.ru/music.rss');