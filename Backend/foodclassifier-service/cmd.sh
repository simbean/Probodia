docker-compose down

docker rmi nginx:test 
docker rmi app:test

y | docker image prune -a
cd /root/foodclassifier-service
docker build -t app:test .
cd /root/foodclassifier-service/nginx
docker build -t nginx:test .
cd /root/foodclassifier-service

docker-compose up -d
