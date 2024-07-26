CREATE TABLE user_profile (
    id              UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name      VARCHAR(64)        NOT NULL,
    last_name       VARCHAR(64)        NOT NULL,
    date_birth      VARCHAR(10)        NOT NULL,
    inn             VARCHAR(12)        NOT NULL,
    snils           VARCHAR(14)        NOT NULL,
    number_passport VARCHAR(6)         NOT NULL,
    login           VARCHAR(64) UNIQUE NOT NULL,
    password        VARCHAR(512)       NOT NULL,
    role            VARCHAR(6)         NOT NULL
)