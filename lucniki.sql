/*DROP TABLE IF EXISTS purchase_history;
DROP TABLE IF EXISTS request;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS season_tickets;
DROP TABLE IF EXISTS day;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS profile_status;
DROP TABLE IF EXISTS request_status;
DROP TABLE IF EXISTS ranks;

CREATE TABLE profile_status
(
    status VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE request_status
(
    status VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE ranks
(
    rank_name VARCHAR(50) NOT NULL PRIMARY KEY,
    color     VARCHAR(10) UNIQUE
);

CREATE TABLE day
(
    date DATE NOT NULL PRIMARY KEY,
    time_start TIME NOT NULL,
    time_end TIME NOT NULL
);

CREATE TABLE events
(
    event_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    date      DATE,
    html_text VARCHAR(100) DEFAULT NULL
);

CREATE TABLE season_tickets
(
    ticket_type       VARCHAR(50) NOT NULL PRIMARY KEY,
    cost              DOUBLE NOT NULL,
    number_of_classes INTEGER NOT NULL, #число доступных занятий (например, 8); если абонемент безлимитный, значение равно значению в поле days_duration
    time_duration     TIME NOT NULL, #доступное время в день (например, 2 часа 30 минут); если абонемент безлимитный, значение - 24 часа
    days_duration INTEGER NOT NULL, #число доступных календарных дней, например, 30 дней
    is_for_sale TINYINT(1) NOT NULL
);

CREATE TABLE students
(
    student_id       INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name       VARCHAR(50),
    last_name        VARCHAR(50),
    phone_number     VARCHAR(16),
    email            VARCHAR(70),
    birth_date       DATE,
    rank_name        VARCHAR(60) NOT NULL,
    attended_classes INTEGER,
    benefits         BOOLEAN,
    password_hash    VARCHAR(50),
    profile_status   VARCHAR(50) NOT NULL,
    has_paid TINYINT(1),
    token VARCHAR(50) NOT NULL,
    token_date DATE NOT NULL,
    FOREIGN KEY (rank_name) REFERENCES ranks (rank_name) ON UPDATE CASCADE,
    FOREIGN KEY (profile_status) REFERENCES profile_status (status) ON UPDATE CASCADE
);

CREATE TABLE request
(
    request_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id  INTEGER NOT NULL,
    date DATE NOT NULL,
    time_start TIME NOT NULL,
    time_end TIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students (student_id) ON UPDATE CASCADE,
    FOREIGN KEY (date) REFERENCES day (date) ON UPDATE CASCADE,
    FOREIGN KEY (status) REFERENCES request_status (status) ON UPDATE CASCADE
);

CREATE TABLE purchase_history
(
    purchase_id       INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    start_date        DATE NOT NULL,
    student_id        INTEGER NOT NULL,
    ticket_type       VARCHAR(50) NOT NULL,
    available_classes INTEGER,
    FOREIGN KEY (student_id) REFERENCES students (student_id) ON UPDATE CASCADE,
    FOREIGN KEY (ticket_type) REFERENCES season_tickets (ticket_type) ON UPDATE CASCADE
);


INSERT INTO ranks VALUES ('juniors', 'green'), ('middles', 'yellow'), ('seniors', 'red');


SELECT * FROM ranks;*/