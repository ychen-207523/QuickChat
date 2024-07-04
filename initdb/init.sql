DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);
