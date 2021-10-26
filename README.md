# Schedule Web Service

## Description of project

#### Web Service allows you to communicate with database (Postgres) by HTTP requests (get, write, delete information). 
#### Database contains schedule: information about employees, their responsibilities and the time interval when they are responsible for it.

There is a file with database migration in `/src/main/resources/` to restore database with right structure.

## Employee
### GET
#### employees
`curl -L "http://localhost:9090/employee?ordering=id" `

#### employee by id
`curl -L "http://localhost:9090/employee/id" `

### POST
`curl -L -X POST -H "Content-Type: application/json" -d "{ \"name\" : \"employee name\" }" "http://localhost:9090/employee/" `

### DELETE

#### employee by id
`curl -L -X DELETE "http://localhost:9090/employee/id" `

#### all employees
`curl -L -X DELETE "http://localhost:9090/employee/clear" `

### UPDATE
`curl -L -X PATCH -H "Content-Type: application/json" -d "{ \"name\" : \"employee name\" }"
"http://localhost:9090/employee/id" `


## Responsibility
### GET
#### responsibilities
`curl -L "http://localhost:9090/responsibility?ordering=id" `

#### responsibility by id
`curl -L "http://localhost:9090/responsibility/id" `

### POST
`curl -L -X POST -H "Content-Type: application/json" -d "{ \"name\" : \"responsibility name\",
\"daysNumber\" : days_number }" "http://localhost:9090/responsibility/" `

### DELETE
#### responsibility by id
`curl -L -X DELETE "http://localhost:9090/responsibility/id" `

#### all responsibilities
`curl -L -X DELETE "http://localhost:9090/responsibility/clear" `

### UPDATE
`curl -L -X PATCH -H "Content-Type: application/json" -d "{ \"name\": \"responsibility name\",
\"daysNumber\": days_number }" "http://localhost:9090/responsibility/id"`


## Schedule
### GET
#### schedules
`curl -L "http://localhost:9090/schedule?ordering=id"`

#### schedule by id
`curl -L "http://localhost:9090/schedule/id"`

#### schedule by employee id
`curl -L "http://localhost:9090/schedule/emp/id"`

#### schedule by responsibility id
`curl -L "http://localhost:9090/schedule/resp/id"`

#### schedule by date
`curl -L "http://localhost:9090/schedule/date/yyyy-mm-dd"`

#### schedule by date and responsibility id
`curl -L "http://localhost:9090/schedule/date/yyyy-mm-dd/resp/id"`

### POST
`curl -L -X POST -H "Content-Type: application/json" -d "{ \"employeeId\": id, \"responsibilityId\": id,
\"startDate\": \"yyyy-mm-dd\" }" "http://localhost:9090/schedule/" `

### DELETE
#### schedule by id
`curl -L -X DELETE "http://localhost:9090/schedule/id"`

#### old from schedule
`curl -L -X DELETE "http://localhost:9090/schedule/delete-old"`

#### all schedules
`curl -L -X DELETE "http://localhost:9090/schedule/clear"`

### UPDATE
`curl -L -X PATCH -H "Content-Type: application/json" -d "{
\"employeeId\": id,
\"responsibilityId\": id,
\"startDate\": \"yyyy-mm-dd\" }" "http://localhost:9090/schedule/id"`
