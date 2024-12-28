#!/bin/bash
set -e

# Create the databases
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE "Authenticator";
    CREATE DATABASE "AuthenticatorTest";
EOSQL

# Apply the SQL initialization script to both databases
for DB in Authenticator AuthenticatorTest; do
    echo "Initializing database: $DB"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname="$DB" -f /docker-entrypoint-initdb.d/init.sql
done
