INSERT INTO roles (id, name) VALUES (1, 'ROLE_CUSTOMER'), (2, 'ROLE_SELLER'), (3, 'ROLE_AGENT'), (4, 'ROLE_ADMIN');

INSERT INTO users(id, username, email, password) VALUES
    (1, 'seller1', 'seller1@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (2, 'seller2', 'seller2@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (3, 'agent1', 'agent1@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (4, 'agent2', 'agent2@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (5, 'customer1', 'customer1@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (6, 'customer2', 'customer2@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (7, 'agent3', 'agent3@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW'),
    (8, 'admin', 'admin@mercadolibre.com', '$2a$10$lKLT3/ATyYt79pC53F3vgOqBDWwykqDYdr1R7xYRB3kSff0JWmxxW');

INSERT INTO customers(id, cpf) VALUES
    (1, '860.607.870-02'),
    (2, '049.849.720-84'),
    (3, '559.808.720-00'),
    (4, '277.493.700-05'),
    (5, '657.044.840-42'),
    (6, '133.262.740-41'),
    (7, '252.852.660-17'),
    (8, '041.278.675-31');

INSERT INTO warehouses(id, address, name) VALUES
    (1, '11111-000', 'SP'),
    (2, '22222-000', 'RJ'),
    (3, '33333-000', 'SC');

INSERT INTO sellers VALUES (1), (2);
-- Na linha seguinte foram adicionadas as relações entre agents e warehouses
INSERT INTO agents(id, warehouse_id) VALUES (3, 1), (4, 2), (7, 3);

INSERT INTO users_roles(user_id, role_id) VALUES
-- ADMIN
    (8, 4),
-- CUSTOMERS
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
-- SELLERS
    (1, 2),
    (2, 2),
--AGENT
    (3, 3),
    (4, 3),
    (7, 3);

INSERT INTO sections(id, capacity, category, description, warehouse_id) VALUES
    (1, 500, 'FRESCO', 'sessao SP 1', 1),
    (2, 500, 'CONGELADO', 'sessao SP 2', 1),
    (3, 500, 'REFRIGERADO', 'sessao SP 3', 1),

    (4, 500, 'FRESCO', 'sessao RJ 1', 2),
    (5, 500, 'CONGELADO', 'sessao RJ 2', 2),
    (6, 500, 'REFRIGERADO', 'sessao RJ 3', 2),


  -- Dados de seção utilizados nos teste do requisito 3
    (7, 500, 'FRESCO', 'sessao SC 1', 3),
    (8, 500, 'CONGELADO', 'sessao SC 2', 3),
    (9, 500, 'REFRIGERADO', 'sessao SC 3', 3);
  -- Final dos dados de seção de testes de requisito 3

INSERT INTO inbound_orders(id, order_date, section_id) VALUES
    (1, '2022-04-25', 1),
    (2, '2022-04-25', 1),
    (3, '2022-04-25', 1),
    (4, '2022-04-25', 2),
    (5, '2022-04-25', 2),

  -- Dados de inbound para teste do requisito 3
    (6, '2022-04-25', 7),
    (7, '2022-04-25', 8),
    (8, '2022-04-25', 9);
  -- Final dos dados de teste de inbound do requisito3


INSERT INTO products(id, category, name, price, volume, seller_id) VALUES
    (1, 'FRESCO', 'UVA', 10, 5, 1),
    (2, 'FRESCO', 'MACA', 10, 5, 2),
    (3, 'FRESCO', 'PERA', 10, 5, 1),

    (4, 'CONGELADO', 'CARNE 1', 10, 10, 1),
    (5, 'CONGELADO', 'CARNE 2', 10, 8, 2),
    (6, 'CONGELADO', 'CARNE 3', 10, 6, 1),

    (7, 'REFRIGERADO', 'PEIXE 1', 10, 2, 2),
    (8, 'REFRIGERADO', 'PEIXE 2', 10, 3, 1),
    (9, 'REFRIGERADO', 'PEIXE 3', 10, 3, 2);

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
    (15, 10, 10, DATEADD(DAY, 15, CURRENT_DATE), 10, '2022-01-01 00:00:00', 5, 5, 6),


  -- Dados de Batch para teste da requisito 3, FindProducts
    (16, 20, 20, DATEADD(DAY, 60, CURRENT_DATE), 20, '2022-01-01 00:00:00', 15, 6, 1),
    (17, 10, 20, DATEADD(DAY, 45, CURRENT_DATE), 20, '2022-01-01 00:00:00', 15, 6, 1),
    (18, 15, 20, DATEADD(DAY, 30, CURRENT_DATE), 20, '2022-01-01 00:00:00', 15, 6, 1),

    (19, 15, -5, DATEADD(DAY, 30, CURRENT_DATE), 20, '2022-01-01 00:00:00', -15, 7, 3),
    (20, 20, -5, DATEADD(DAY, 15, CURRENT_DATE), 20, '2022-01-01 00:00:00', -15, 7, 3),
    (21, 10, -5, DATEADD(DAY, 45, CURRENT_DATE), 20, '2022-01-01 00:00:00', -15, 7, 3),

    (22, 10, 10, DATEADD(DAY, 30, CURRENT_DATE), 20, '2022-01-01 00:00:00', 3, 8, 8),
    (23, 15, 10, DATEADD(DAY, 60, CURRENT_DATE), 20, '2022-01-01 00:00:00', 3, 8, 8),
    (24, 20, 10, DATEADD(DAY, 45, CURRENT_DATE), 20, '2022-01-01 00:00:00', 3, 8, 8),

    (25, 10, 10, DATEADD(DAY, 15, CURRENT_DATE), 20, '2022-01-01 00:00:00', 3, 8, 4),
    (26, 15, 10, DATEADD(DAY, 10, CURRENT_DATE), 20, '2022-01-01 00:00:00', 3, 8, 4),
    (27, 20, 10, DATEADD(DAY, 10, CURRENT_DATE), 20, '2022-01-01 00:00:00', 3, 8, 4);
  -- final de dados de Batch pra teste do requisito 3


INSERT INTO purchase_order(id, created_date, updated_date, order_status, customer_id) VALUES
(1, '2022-05-01 00:00:00', '2022-05-01 00:00:00', 'OPENED', 5),
(2, '2022-05-01 00:00:00', '2022-05-01 00:00:00', 'OPENED', 6);

INSERT INTO purchase_items(id, quantity, product_id, purchase_order_id) VALUES
(1, 3, 1, 1),
(2, 5, 2, 1),

(3, 2, 3, 2),
(4, 7, 4, 2);

