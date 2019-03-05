#!/bin/bash
ORIGIN= sed -n 11p /etc/nginx/nginx.conf
if [ "$ORIGIN" != "    server $@:6379;" ]
then
    sed -i '' "11s/.*/    server $@:6379;/" /etc/nginx/nginx.conf
    /usr/sbin/nginx -s reload
else
    echo "The IP address is already being used."
fi
