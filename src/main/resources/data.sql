DROP TABLE IF EXISTS flying_users;

CREATE TABLE flying_users (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    user_name VARCHAR(256) NOT NULL,
    birthdate DATE NOT NULL,
    country VARCHAR(128) NOT NULL,
    phone_number VARCHAR(16),
    genre VARCHAR(16)
);

INSERT INTO flying_users (user_name, birthdate, country) VALUES
    ('Laurent', '2000-10-10', 'FR'),
    ('Sophie', '2001-01-01', 'US'),
    ('Agathe', '1992-12-12', 'ES');