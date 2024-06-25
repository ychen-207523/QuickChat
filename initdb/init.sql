-- Drop existing tables
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
    );

-- Create authorities table
CREATE TABLE IF NOT EXISTS authorities (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_username FOREIGN KEY(username) REFERENCES users(username)
    );

-- Insert a sample user
INSERT INTO users (username, email, password, enabled) VALUES ('user', 'user@example.com', 'password', true)
    ON CONFLICT (username) DO NOTHING;

-- Insert a sample authority
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER')
    ON CONFLICT (username, authority) DO NOTHING;
