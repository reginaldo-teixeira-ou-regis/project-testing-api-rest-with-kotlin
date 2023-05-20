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

### Customer

**Rota: POST `/api/customers`**  /  "http://localhost:8080/api/customers"

```bash
Body: {
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

**Rota: GET `/api/customers/{id}`**  /  "http://localhost:8080/api/customers/1"

#

**Rota: DELETE `/api/customers/{id}`**  /  "http://localhost:8080/api/customers/1"

#

**Rota: PATCH `/api/customers?customerId={id}`**  /   "http://localhost:8080/api/customers?customerId=1"

```bash
Body: {
  "firstName": "John",
  "lastName": "Abert Doe",
  "income": 5000.0,
  "zipCode": "59104025",
  "street": "John Albert Doe Street"
}
```

---

### Credits

**Rota: POST `/api/credits`**  /  "http://localhost:8080/api/credits"

```bash
Body: {
  "customerId": 1,
  "creditValue": 300.0,
  "dayFirstOfInstallment": "2023-07-01",
  "numberOfInstallments": 5
}
```
#

**Rota: GET `/api/credits?customerId={customerId}`**  /  "http://localhost:8080/api/credits?customerId=1"

#

**Rota: GET `/api/credits/{creditCode}?customerId={customerId}`**  /  "http://localhost:8080/api/credits/30d540c4-2e7f-44f7-95f0-0cbbc980365f?customerId=1"

