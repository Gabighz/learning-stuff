CREATE DATABASE IF NOT EXISTS transactionhistory;

\c transactionhistory;

CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    fulfiller_id BIGINT NOT NULL,
    total_filled_contracts INTEGER NOT NULL,
    total_average_execution_price INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    transaction_id INTEGER REFERENCES transactions (id)
);

CREATE USER IF NOT EXISTS transaction_history_service WITH PASSWORD 'hunter2';

GRANT SELECT, INSERT, UPDATE, DELETE ON transactions, orders TO transaction_history_service;