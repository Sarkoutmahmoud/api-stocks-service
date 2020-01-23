insert into prod.stock(id,  name_stock)
VALUES (1, 'Unilever N.V'),
        (2, 'Vopak N.V'),
        (3, 'Randstad N.V');

insert into prod.stock_entry(id, stock_id, current_price, time_of_entry)
VALUES (1, 1, 3, '2000-01-01 01:00:00 +01:00'),
        (2, 2, 500, current_timestamp ),
        (3, 2, 540, current_timestamp ),
        (4, 3,  200, current_timestamp);