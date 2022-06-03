INSERT INTO STORE_MST (id, name, open_time, close_time, channel_id) VALUES (1, 'テストストア', '06:00:00', '22:00:00', '1') ON CONFLICT ON CONSTRAINT store_mst_pkey DO NOTHING;
