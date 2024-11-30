wget -O demo-medium-20170815.zip https://bit.ly/demo-medium-20170815

unzip ./demo-medium-20170815.zip -d ./db/node/dump/tmp

rm ./demo-medium-20170815.zip

docker-compose build --no-cache

docker-compose up -d