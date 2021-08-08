Task

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring Boot) without frontend.

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and curl commands to get data for voting and vote.

##REST API for Restaurants

####Get by ID (without menus)
Access: ADMIN

GET /restaurants/[restaurant id]

curl -s http://localhost:8080/voting/restaurants/100005 -u admin1@gmail.com:admin1

####Delete by ID
Access: ADMIN
Not allowed if restaurant has at least one menu.

DELETE /restaurants/[restaurant id]

curl -s -X DELETE http://localhost:8080/voting/restaurants/100010 -u admin1@gmail.com:admin1

####Create
Access: ADMIN

POST /restaurants

Body: {"name":"[restaurant name]"}

curl -s -X POST -d '{"name":"French"}' -H 'Content-Type:application/json' http://localhost:8080/voting/restaurants -u admin1@gmail.com:admin1

####Update
Access: ADMIN

PUT /restaurants/[restaurant id]

Body: {"name":"[restaurant name]"}

curl -s -X PUT -d '{"name":"Turkish"}' -H 'Content-Type:application/json' http://localhost:8080/voting/restaurants/100009 -u admin1@gmail.com:admin1