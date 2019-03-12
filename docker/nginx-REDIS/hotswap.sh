#!/bin/bash
VAR= awk '/:6379/{print NR}' /etc/nginx/nginx.conf
if grep -q "$@:6379" /etc/nginx/nginx.conf
then
    echo "The IP address is already being used."
else
    sed -i "$VARs/.*/    server $@:6379;/" /etc/nginx/nginx.conf
    /usr/sbin/nginx -s reload
fi
