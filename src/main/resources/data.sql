INSERT INTO roles (id, name) VALUES (1, 'ROLE_AGENT'), (2, 'ROLE_SELLER'), (3, 'ROLE_CUSTOMER');

INSERT INTO users(id, username, email, password) VALUES
    (1, 'seller1', 'seller1@mercadolibre.com', '123456'),
    (2, 'seller2', 'seller2@mercadolibre.com', '123456'),
    (3, 'agent1', 'agent1@mercadolibre.com', '123456'),
    (4, 'agent2', 'agent2@mercadolibre.com', '123456'),
    (5, 'customer1', 'customer1@mercadolibre.com', '123456'),
    (6, 'customer2', 'customer2@mercadolibre.com', '123456');

INSERT INTO sellers VALUES (1), (2);
INSERT INTO agents VALUES (3), (4);
INSERT INTO customers(id, cpf) VALUES (5, '111.111.111-11'), (6, '222.222.222-22');

INSERT INTO users_roles(user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1),
    (4, 1),
    (5, 3),
    (6, 3);

INSERT INTO warehouses(id, address, name) VALUES
    (1, '11111-000', 'SP'),
    (2, '22222-000', 'RJ'),
    (3, '33333-000', 'SC');

INSERT INTO sections(id, capacity, category, description, warehouse_id) VALUES
    (1, 500, 'FRESCO', 'sessao SP 1', 1),
    (2, 500, 'CONGELADO', 'sessao SP 2', 1),
    (3, 500, 'REFRIGERADO', 'sessao SP 3', 1),

    (4, 500, 'FRESCO', 'sessao RJ 1', 2),
    (5, 500, 'CONGELADO', 'sessao RJ 2', 2),
    (6, 500, 'REFRIGERADO', 'sessao RJ 3', 2),

    (7, 500, 'FRESCO', 'sessao SC 1', 3),
    (8, 500, 'CONGELADO', 'sessao SC 2', 3),
    (9, 500, 'REFRIGERADO', 'sessao SC 3', 3);

INSERT INTO products(id, category, name, price, volume, seller_id) VALUES
    (1, 'FRESCO', 'PEIXE 1', 10, 5, 1),
    (2, 'FRESCO', 'PEIXE 2', 10, 5, 2),
    (3, 'FRESCO', 'PEIXE 3', 10, 5, 1),

    (4, 'CONGELADO', 'CARNE 1', 10, 10, 1),
    (5, 'CONGELADO', 'CARNE 2', 10, 8, 2),
    (6, 'CONGELADO', 'CARNE 3', 10, 6, 1),

    (7, 'REFRIGERADO', 'UVA', 10, 2, 2),
    (8, 'REFRIGERADO', 'MACA', 10, 3, 1),
    (9, 'REFRIGERADO', 'PERA', 10, 3, 2);

INSERT INTO inbound_orders(id, order_date, section_id) VALUES
    (1, '2022-04-25', 1),
    (2, '2022-04-25', 1),
    (3, '2022-04-25', 1),
    (4, '2022-04-25', 2),
    (5, '2022-04-25', 2);

INSERT INTO batch_stocks (id, current_quantity, current_temperature, due_date, initial_quantity, manufacturing_date_time, minimum_temperature, inbound_order_id, product_id) VALUES
    (1, 10, 10, DATEADD(DAY, 25, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 1, 1),
    (2, 10, 10, DATEADD(DAY, 10, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 1, 2),
    (3, 10, 10, DATEADD(DAY, 5, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 1, 3),

    (4, 10, 10, DATEADD(DAY, 8, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 2, 1),
    (5, 10, 10, DATEADD(DAY, 10, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 2, 2),
    (6, 10, 10, DATEADD(DAY, 16, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 2, 3),

    (7, 10, 10, DATEADD(DAY, 20, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 3, 1),
    (8, 10, 10, DATEADD(DAY, 30, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 3, 2),
    (9, 10, 10, DATEADD(DAY, 15, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 3, 3),

    (10, 10, 10, DATEADD(DAY, 20, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 4, 4),
    (11, 10, 10, DATEADD(DAY, 30, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 4, 5),
    (12, 10, 10, DATEADD(DAY, 15, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 4, 6),

    (13, 10, 10, DATEADD(DAY, 20, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 5, 4),
    (14, 10, 10, DATEADD(DAY, 30, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 5, 5),
    (15, 10, 10, DATEADD(DAY, 15, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 5, 6);


