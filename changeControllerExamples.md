Payload for inserting, updating, deleting rows of aircrafts_data table

{
"idKeys": [
"id"
],
"data": {
"model": {"en": "New aircraft", "ru": "Новый самолет"},
"range": 5500,
"id": "NEW"
},
"tableName": "aircrafts_data"
}

Open http://localhost:8384/swagger-ui/#/change-controller/
Use /change POST, PUT, DELETE to insert, change or delete data 