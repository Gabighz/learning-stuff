Instructions to use the starter script, assuming an WSL2 Ubuntu distribution with PostgreSQL 14 installed:

    sudo service postgresql start
    cp starter.sql /var/lib/postgresql/
    sudo -iu postgres psql
    \i starter.sql