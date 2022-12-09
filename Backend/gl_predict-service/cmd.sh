docker stop app_gl
docker rm app_gl
docker rmi app_gl:0.1

y | docker image prune -a
cd /home/ubuntu/app
docker build -t app_gl:0.1 .
docker-compose up -d