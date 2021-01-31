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
    release   DATETIME     DEFAULT NULL,
    foreign key (author_id) references authors (id)
);

CREATE TABLE rating_books
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    books_id int    NOT NULL,
    rat      int    DEFAULT NULL,
    foreign key (books_id) references books (id)
);