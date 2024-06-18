--liquibase formatted sql

--changeset system:init
CREATE TABLE IF NOT EXISTS patients_management.patients (
    id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(6) NOT NULL,
    birth_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS patients_created_at_id_idx
    ON patients_management.patients (created_at, id);
