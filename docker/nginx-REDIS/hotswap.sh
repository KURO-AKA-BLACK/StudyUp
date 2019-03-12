#!/bin/bash
VAR=$(sed -n '/:6379/=' /etc/nginx/nginx.conf)
if grep -q "$@:6379" /etc/nginx/nginx.conf
then
    echo "The IP address $@:6379 is already being used."
else
    sed -i "$VARs/.*/    server $@:6379;/" /etc/nginx/nginx.conf
    /usr/sbin/nginx -s reload
fi
