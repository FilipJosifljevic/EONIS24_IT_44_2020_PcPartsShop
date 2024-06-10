INSERT INTO "users"(user_id, password, email, role)
VALUES('fd96ec5f-518b-4791-bc9f-d11ff0af9c2f', '$2a$10$ztAUps1royz/4meuqMPPkOr2FXlJn7Q2jT.Cb/uxYoHaWyEuPrZ5K', 'john.doe@example.com', 0);
INSERT INTO "customers"(customer_id, first_name, last_name, adress, zip_code)
VALUES ('fd96ec5f-518b-4791-bc9f-d11ff0af9c2f', 'John', 'Doe', '123 Main St', '12345');
INSERT INTO "users"(user_id, password, email, role)
VALUES('f751d9cc-3285-47c4-9f84-4c56e79a5f32', '$2a$10$cUf4KmV/rkHxdmyOV3HdaOTKft89jo.7NfZu5pH5s2eQ0KivY8XVS', 'jane.smith@example.com', 0);
INSERT INTO "customers"(customer_id, first_name, last_name, adress, zip_code)
VALUES ('f751d9cc-3285-47c4-9f84-4c56e79a5f32', 'Jane', 'Smith', '456 Second St', '54232');
INSERT INTO "users"(user_id, password, email, role)
VALUES('c1bb4e93-513e-4ddd-bd3c-12844977d481', '$2a$10$dJviPqW.s1g1yLFWYzpkrumPg3xgB796WX5vYPFNHkBTlvzahYxnW', 'admin.user@example.com', 1);
INSERT INTO "admins"(admin_id)
VALUES('c1bb4e93-513e-4ddd-bd3c-12844977d481');
INSERT INTO "users"(user_id,  password, email, role)
VALUES('021683ec-eb5a-447a-aefd-b29f34e466f2', '$2a$10$VFR3JuXV/6Qw1Mw5NLL.jul.CW1NxUW0Z3GnM0HFBUokCRizhVb8C', 'provider1.user@example.com', 2);
INSERT INTO "providers"(provider_id, provider_name, city_name, contact_number)
VALUES('021683ec-eb5a-447a-aefd-b29f34e466f2', 'Gigatron', 'Belgrade', 0114362231);
INSERT INTO "users"(user_id, password, email, role)
VALUES('99e97c24-dd0b-4cf0-a72b-98697936fb20', '$2a$10$Lqk1/uXTsCIr3aacLMvQGuSVrbIXBH5C5tuUGbFUJdO2U.ZSIib4e', 'provider2.user@example.com', 2);
INSERT INTO "providers"(provider_id, provider_name, city_name, contact_number)
VALUES('99e97c24-dd0b-4cf0-a72b-98697936fb20', 'Samsung', 'Nis', 0423221321);


INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('531faa5b-8b06-4efb-b4a6-4d523f10a7a1', 'Nvidia GeForce RTX 3080', 'GPU', 79999.99, '021683ec-eb5a-447a-aefd-b29f34e466f2', 25, 'https://www.dotmarket.rs/image/catalog/feed_240/asus-nvidia-geforce-rtx-3080-ti-12gb-320bit-rog-strix-rtx3080ti-o12g-gaming-50202.jpg');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('4b81c209-5c35-45a5-8484-1f65f25b42c9', 'AMD Ryzen 9 5900X', 'CPU', 54449.99, '99e97c24-dd0b-4cf0-a72b-98697936fb20', 27, 'https://api.tehnoteka.rs/uploads/cache/product_4x3/uploads/2023/09/amd-ryzen-9-5900x-12-core-3-70-ghz-4-80-ghz-procesor-650d74a689251.jpeg');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('e5c23180-5e32-4b5e-90c6-8f032cc282c2', 'Samsung 970 EVO Plus', 'SSD', 12229.99, '99e97c24-dd0b-4cf0-a72b-98697936fb20', 15, 'https://www.tehnomedia.rs/image/73234.jpg?tip=webp&tip_slike=4');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('2d5c671f-3b68-4e5f-8e8c-7a54596d5479', 'Corsair RM850x', 'PSU', 13339.99, '021683ec-eb5a-447a-aefd-b29f34e466f2',30, 'https://api.tehnoteka.rs/uploads/cache/product_large/uploads/2023/10/corsair-napajanje-rm850x-850w-6537aa9cc6b2d.png');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('ba5a9728-6a3d-4f8f-8d42-0fe58a0978a0', 'AMD Radeon RX 6800 XT', 'GPU', 66449.99, '021683ec-eb5a-447a-aefd-b29f34e466f2',22, 'https://www.exceed.rs/images/products/big/69795.jpg');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('fcb4ce23-4744-4a5f-98f0-6843142d982f', 'Intel Core i9-11900K', 'CPU', 52229.99, '99e97c24-dd0b-4cf0-a72b-98697936fb20', 17, 'https://api.tehnoteka.rs/uploads/cache/product_large/uploads/2023/09/intel-core-i9-11900k-8-core-3-50-ghz-5-30-ghz-procesor-650d72374c9e2.png');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('19dbec1f-1f1b-4b68-81f6-3c3ab739e313', 'Crucial MX500 1TB', 'SSD', 9999.99, '021683ec-eb5a-447a-aefd-b29f34e466f2', 31, 'https://c.cdnmp.net/160631482/p/l/9/ssd-sata3-1tb-crucial-mx500-3d-nand-560-510mb-s-ct1000mx500ssd1~1239.jpg');
INSERT INTO "products" (product_id, product_name, product_category, product_price, provider_id, quantity_in_stock, image_url)
VALUES ('39e3190c-8c8c-4cb3-8d69-5265bfa399e7', 'EVGA SuperNOVA 750 G5', 'PSU', 11119.99, '99e97c24-dd0b-4cf0-a72b-98697936fb20', 38, 'https://m.media-amazon.com/images/I/51mj8uHII5L._AC_.jpg');

INSERT INTO "orders" (order_id, order_date, order_price, order_status, discount, promo_code, customer_id)
VALUES ('f6c23e5f-587d-4a47-9264-16cf3262e2f3', '2024-03-18', 79999.99, 'Pending', 0.0, NULL, 'FD96EC5F-518B-4791-BC9F-D11FF0AF9C2F');
INSERT INTO "product_orders" (product_order_id, quantity, order_id, product_id)
VALUES ('d7c38e3f-8e8d-49b8-98a5-0b30f7d77c24', 1, 'f6c23e5f-587d-4a47-9264-16cf3262e2f3', '531faa5b-8b06-4efb-b4a6-4d523f10a7a1');
INSERT INTO "orders" (order_id, order_date, order_price, order_status, discount, promo_code, customer_id)
VALUES ('2ff56b07-57c5-41a0-ae76-25a48455a27f', '2024-03-18', 66449.99, 'Pending', 0.0, NULL, 'F751D9CC-3285-47C4-9F84-4C56E79A5F32');
INSERT INTO "product_orders" (product_order_id, quantity, order_id, product_id)
VALUES ('99755c0f-fc59-4a64-88b7-bd823e1e711b', 1, '2ff56b07-57c5-41a0-ae76-25a48455a27f', 'ba5a9728-6a3d-4f8f-8d42-0fe58a0978a0');
INSERT INTO "orders" (order_id, order_date, order_price, order_status, discount, promo_code, customer_id)
VALUES ('3d986ea1-6fa2-478b-a091-839af71e8c32', '2024-03-18', 13339.99, 'Pending', 0.0, NULL, 'F751D9CC-3285-47C4-9F84-4C56E79A5F32');
INSERT INTO "product_orders" (product_order_id, quantity, order_id, product_id)
VALUES ('7a57cb5c-0532-4954-a86f-1483d92ff371', 2, '3d986ea1-6fa2-478b-a091-839af71e8c32', '2d5c671f-3b68-4e5f-8e8c-7a54596d5479');
INSERT INTO "product_orders" (product_order_id, quantity, order_id, product_id)
VALUES ('b1d8282e-d189-4f79-9e2d-5891ec5f9d51', 1, '3d986ea1-6fa2-478b-a091-839af71e8c32', '39e3190c-8c8c-4cb3-8d69-5265bfa399e7');
INSERT INTO "product_orders" (product_order_id, quantity, order_id, product_id)
VALUES ('631a194d-0497-4b7b-aa64-89f4b6c5d54e', 1, '3d986ea1-6fa2-478b-a091-839af71e8c32', 'ba5a9728-6a3d-4f8f-8d42-0fe58a0978a0');