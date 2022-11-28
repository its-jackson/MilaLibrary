
DROP TABLE book;
DROP TABLE status;
DROP TABLE customer;

CREATE TABLE customer
(
    id         char(36)     NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    address    VARCHAR(255) NOT NULL,
    phone      VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE book
(
    id                char(36)     NOT NULL,
    title             VARCHAR(255) NULL,
    author_first_name VARCHAR(255) NULL,
    author_last_name  VARCHAR(255) NULL,
    rating            INT          NOT NULL,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE status
(
    id          char(36) NOT NULL,
    customer_id char(36) NOT NULL,
    book_id     char(36) NOT NULL,
    date        datetime NULL,
    book_state  INT      NOT NULL,
    CONSTRAINT pk_status PRIMARY KEY (id)
);

ALTER TABLE status
    ADD CONSTRAINT FK_STATUS_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);

ALTER TABLE status
    ADD CONSTRAINT FK_STATUS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

INSERT INTO customer
VALUES ('66241aed-2891-4da5-8647-a75ed1723709',
        'Newmarket',
        'c.moore2@washington.edu',
        'Colin',
        'Moore',
        '822-532-6902'),
       ('46c1ad88-0e68-4008-9a55-b60ca557c399',
        'Barrie',
        'jacksonj@gmail.com',
        'Jackson',
        'Johnson',
        '973-759-9196'),
       ('1141c751-7083-42b4-b5d9-99d2f2079898',
        'Newmarket',
        'mila.zhurba.7@gmail.com',
        'Mila',
        'Zhurba',
        '426-553-5668'),
       ('4023c59c-78a9-4a0c-9055-a1eb01108678',
        'Toronto',
        'alim.kutchhi7@gmail.com',
        'Alim',
        'Kutchhi',
        '854-741-8990');