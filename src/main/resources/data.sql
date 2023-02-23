DROP TABLE IF EXISTS flying_users;

CREATE TABLE flying_users (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    user_name VARCHAR(256) NOT NULL,
    birthdate DATE NOT NULL,
    country VARCHAR(128) NOT NULL,
    phone_number VARCHAR(16),
    genre VARCHAR(16)
);

INSERT INTO flying_users (user_name, birthdate, country, phone_number, genre) VALUES
    ('Laurent', '2000-10-10', 'FR', NULL, NULL),
    ('Sophie', '2001-01-01', 'FR', NULL, NULL),
    ('Agathe', '1992-12-12', 'FR', NULL, NULL),
    ('Joséphine', '1997-11-19', 'FR', '0123456789', NULL),
    ('Fabien', '2002-02-04', 'FR', NULL, 'M'),
    ('Alexandre', '1998-06-04', 'FR', '0423856577', 'F'),
    ('Sandrine', '1992-12-12', 'FR', '+33654230188', 'F');