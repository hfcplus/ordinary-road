修改nginx.conf后需要重新加载nginx：`service nginx restart` 	ubuntu: `nginx -s reload`

测试nginx配置文件是否正确 `nginx -t -c /path/to/nginx.conf` 

ngnix开启默认使用80端口

# 反向代理与SSL

```nginx
user nginx;
worker_processes auto;
error_log /var/log/nginx/error.log;
pid /run/nginx.pid;

include /usr/share/nginx/modules/*.conf;

events {
    worker_connections 1024;
}

http {
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile            on;
    tcp_nopush          on;
    tcp_nodelay         on;
    keepalive_timeout   65;
    types_hash_max_size 2048;

    include             /etc/nginx/mime.types;
    default_type        application/octet-stream;

    include /etc/nginx/conf.d/*.conf;
    include /etc/nginx/vhost/*.conf;

	#监听80端口，重定向到 443
    server {
        listen       80;
        server_name  hf.plus www.hf.plus www.hfcplus.com hfcplus.com;
        rewrite ^/(.*)$ https://hf.plus:443/$1 permanent;
    }


	// 监听443，配置ssl，重定向到8080
    server{
        #监听443端口
        listen 443;
        server_name hfcplus.com;
        ssl on;
        ssl_certificate /etc/ssl/hfcplus.com_chain.crt;
        ssl_certificate_key /etc/ssl/hfcplus.com_key.key;
        ssl_session_timeout 5m;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:HIGH:!aNULL:!MD5:!RC4:!DHE;
        ssl_prefer_server_ciphers on;
        location / {
 			proxy_pass http://localhost:8080;
        }
    }

	
}


```

