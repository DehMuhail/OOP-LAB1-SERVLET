-- Таблиця користувачів
CREATE TABLE users (
                       id bigserial PRIMARY KEY ,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);


CREATE TABLE product (
                         id bigserial PRIMARY KEY ,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         price float NOT NULL,
                         stock INT NOT NULL
);


CREATE TABLE orders (
                        user_id bigint references users(id),
                        product_id bigint references product(id)
);

