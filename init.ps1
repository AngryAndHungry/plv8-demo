$tmp = "dump-tmp.zip"
curl -v https://bit.ly/demo-medium-20170815 -o $tmp
Expand-Archive -Path $tmp -DestinationPath .\db\node\dump\tmp -Force
$tmp | Remove-Item

docker-compose build --no-cache

docker-compose up -d