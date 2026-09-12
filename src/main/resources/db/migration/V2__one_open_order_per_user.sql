CREATE UNIQUE INDEX unique_open_order_per_user
    ON orders(user_id)
    WHERE status = 'OPEN';