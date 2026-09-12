CREATE TABLE users (
                       id uuid PRIMARY KEY,
                       name varchar(255) NOT NULL,
                       email varchar(255) NOT NULL UNIQUE
);

CREATE TABLE products (
                          id uuid PRIMARY KEY,
                          name varchar(255),
                          description varchar(255),
                          created_at bigint
);

CREATE TABLE orders (
                        id uuid PRIMARY KEY,
                        user_id uuid NOT NULL,
                        status varchar(255),
                        idempotency_key varchar(255) NOT NULL UNIQUE,
                        created_at bigint,

                        CONSTRAINT fk_order_user
                            FOREIGN KEY (user_id)
                                REFERENCES users(id)
);

CREATE TABLE order_items (
                             id uuid PRIMARY KEY,
                             order_id uuid,
                             product_id uuid,
                             count integer,
                             item_price numeric(10,2),
                             created_at bigint,

                             CONSTRAINT fk_order_item_order
                                 FOREIGN KEY (order_id)
                                     REFERENCES orders(id),

                             CONSTRAINT fk_order_item_product
                                 FOREIGN KEY (product_id)
                                     REFERENCES products(id)
);