Get 10 flights with status starts with 's' letter

query {
flights (take: 10, filter: { status: { starts: "s" } }) {
id
aircraft_code
status
}
}

Get airports filtered with latin/cyrillic prefixes

query {
airports_data (filter: { city: { children: { en: { contains: "kal"}, ru: { contains: "луг"}}}}) {
id
airport_name
city
}
}

Get ten tickets with aggregation

query {
ticket_flights (take: 10) {
amount
flight_id
ticket_no
}
tickets_agg {
count
}
}