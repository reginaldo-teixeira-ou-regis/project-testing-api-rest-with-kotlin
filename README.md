project-testing-api-rest-with-kotlin
## API Rest for a Credit Application Analysis System

### Technologies Used:
![Kotlin](https://img.shields.io/badge/kotlin-%230095D5.svg?style=plastic&logo=kotlin&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=plastic&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%236DB33F.svg?style=plastic&logo=spring-boot)
![Gradle](https://img.shields.io/badge/gradle-%2302303A.svg?style=plastic&logo=gradle&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2%20Database-gray?style=plastic&logo=h2&logoColor=white)
[![Swagger UI](https://img.shields.io/badge/Swagger%20UI-%2385EA2D.svg?style=plastic&logo=swagger&logoColor=white)](https://link-para-o-seu-swagger-ui)
![JUnit](https://img.shields.io/badge/JUnit-%23525DCB.svg?style=plastic&logo=junit5&logoColor=white)

#

#### SWAGGER URL
```bash
http://localhost:8080/swagger-ui.html
```
#

### Api Documentation

* #### Create Customer

##### Rota: POST http://localhost:8080/api/customers
```bash
{
  "firstName": "John",
  "lastName": "Doe",
  "cpf": "75984921901",
  "income": 3000.0,
  "email": "john_doe@example.com",
  "password": "123456",
  "zipCode": "02469060",
  "street": "John Doe Street"
}
```

#

* #### Get Customer By id

##### Rota: GET http://localhost:8080/api/customers/1

#

* #### Delete Customer By id

##### Rota: DELETE http://localhost:8080/api/customers/1

#

* #### Update Customer

##### Rota: PATCH http://localhost:8080/api/customers?customerId=1
```bash
{
  "firstName": "John",
  "lastName": "Abert Doe",
  "income": 5000.0,
  "zipCode": "59104025",
  "street": "John Albert Doe Street"
}
```

---

* #### Request Credit

##### Rota: POST http://localhost:8080/api/credits
```bash
{
  "customerId": 1,
  "creditValue": 1000.0,
  "dayFirstOfInstallment": "2023-06-25",
  "numberOfInstallments": 20
}
```

#

* #### Get All Customer Credits by Id

##### Rota: GET http://localhost:8080/api/credits?customerId=1

#

* #### GET Credit By Credit Code

##### Rota: GET http://localhost:8080/api/credits/30d540c4-2e7f-44f7-95f0-0cbbc980365f?customerId=1

