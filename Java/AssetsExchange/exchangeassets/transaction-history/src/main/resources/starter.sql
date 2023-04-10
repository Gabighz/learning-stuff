CREATE DATABASE transactionhistory;

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

DO $$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'transaction_history_service') THEN
      CREATE ROLE transaction_history_service WITH LOGIN PASSWORD 'hunter2';
   END IF;
END
$$;

GRANT SELECT, INSERT, UPDATE, DELETE ON transactions, orders TO transaction_history_service;