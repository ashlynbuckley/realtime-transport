#!/bin/bash

superset fab create-admin --username "$ADMIN_USERNAME" --firstname Superset --lastname Admin --email "$ADMIN_EMAIL" --password "$ADMIN_PASSWORD"

superset db upgrade

superset init

#Start server
/bin/sh -c /usr/bin/run-server.sh