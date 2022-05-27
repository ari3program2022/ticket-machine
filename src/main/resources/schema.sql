CREATE TABLE IF NOT EXISTS STORE_MST (
    id serial NOT NULL,
    name character varying(100) unique,
    open_time time,
    close_time time,
    channel_id character varying(100) unique,
    created_by character varying(100) DEFAULT 'SYSTEM' NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(100) DEFAULT 'SYSTEM' NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
);

COMMENT ON TABLE STORE_MST IS '店舗マスタ';
COMMENT ON COLUMN STORE_MST.name IS '店舗名';
COMMENT ON COLUMN STORE_MST.open_time IS '営業開始時間';
COMMENT ON COLUMN STORE_MST.close_time IS '営業終了時間';
COMMENT ON COLUMN STORE_MST.channel_id IS 'チャネルID';
COMMENT ON COLUMN STORE_MST.created_by IS '作成者';
COMMENT ON COLUMN STORE_MST.created_at IS '作成日時';
COMMENT ON COLUMN STORE_MST.updated_by IS '最終更新者';
COMMENT ON COLUMN STORE_MST.updated_at IS '最終更新日時';

CREATE TABLE IF NOT EXISTS USER_MST (
    id serial NOT NULL,
    name character varying(100),
    email character varying(100),
    password character varying(100),
    authority character varying(100),
    store_id integer,
    created_by character varying(100) DEFAULT 'SYSTEM' NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(100) DEFAULT 'SYSTEM' NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(name),
    foreign key (store_id) references STORE_MST(id)
);

COMMENT ON TABLE USER_MST IS 'ユーザーマスタ';
COMMENT ON COLUMN USER_MST.name IS 'ユーザー名';
COMMENT ON COLUMN USER_MST.email IS 'EMail';
COMMENT ON COLUMN USER_MST.password IS 'パスワード';
COMMENT ON COLUMN USER_MST.authority IS '権限';
COMMENT ON COLUMN USER_MST.store_id IS '店舗ID';
COMMENT ON COLUMN USER_MST.created_by IS '作成者';
COMMENT ON COLUMN USER_MST.created_at IS '作成日時';
COMMENT ON COLUMN USER_MST.updated_by IS '最終更新者';
COMMENT ON COLUMN USER_MST.updated_at IS '最終更新日時';

CREATE TABLE IF NOT EXISTS WAIT_LIST (
    id serial NOT NULL,
    store_id integer,
    reserve_date date DEFAULT CURRENT_TIMESTAMP,
    reserve_no integer,
    amount integer,
    status character varying(50),
    customer_id character varying(100),
    created_by character varying(100) DEFAULT 'SYSTEM' NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(100) DEFAULT 'SYSTEM' NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    foreign key (store_id) references STORE_MST(id),
    unique(store_id,reserve_date,customer_id)
);

COMMENT ON TABLE WAIT_LIST IS '順番待ちリスト';
COMMENT ON COLUMN WAIT_LIST.store_id IS '店舗ID';
COMMENT ON COLUMN WAIT_LIST.reserve_date IS '予約日';
COMMENT ON COLUMN WAIT_LIST.reserve_no IS '予約番号';
COMMENT ON COLUMN WAIT_LIST.amount IS '人数';
COMMENT ON COLUMN WAIT_LIST.status IS '呼び出し状況';
COMMENT ON COLUMN WAIT_LIST.customer_id IS '顧客ID(※LINE上のuserIdと同一)';
COMMENT ON COLUMN WAIT_LIST.created_by IS '作成者';
COMMENT ON COLUMN WAIT_LIST.created_at IS '作成日時';
COMMENT ON COLUMN WAIT_LIST.updated_by IS '最終更新者';
COMMENT ON COLUMN WAIT_LIST.updated_at IS '最終更新日時';

--最終更新日付の自動更新設定(下記スクリプトは、手動実行が必要)
--CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$
--BEGIN
--    IF (TG_OP = 'UPDATE') THEN
--        NEW.updated_at := now();
--        return NEW;
--    END IF;
--END;
--$$ LANGUAGE plpgsql;
--
--CREATE OR REPLACE TRIGGER trg_store_updated_at BEFORE UPDATE ON STORE_MST FOR EACH ROW EXECUTE PROCEDURE set_updated_at();
--CREATE OR REPLACE TRIGGER trg_user_updated_at BEFORE UPDATE ON USER_MST FOR EACH ROW EXECUTE PROCEDURE set_updated_at();
--CREATE OR REPLACE TRIGGER trg_wait_updated_at BEFORE UPDATE ON WAIT_LIST FOR EACH ROW EXECUTE PROCEDURE set_updated_at();
