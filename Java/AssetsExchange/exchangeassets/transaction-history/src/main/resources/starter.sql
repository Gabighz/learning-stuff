CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE DATABASE transactionhistory;

\c transactionhistory;

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    fulfiller_id UUID NOT NULL,
    total_filled_contracts INTEGER NOT NULL,
    total_average_execution_price INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    transaction_id UUID REFERENCES transactions (id)
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

GRANT INSERT ON transactions, orders TO transaction_history_service;