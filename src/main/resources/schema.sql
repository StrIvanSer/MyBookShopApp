CREATE TABLE authors
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName  VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    author_id int          NOT NULL,
    title     VARCHAR(250) NOT NULL,
    priceOld  VARCHAR(250) DEFAULT NULL,
    price     VARCHAR(250) DEFAULT NULL,
    foreign key (author_id) references authors (id)
);