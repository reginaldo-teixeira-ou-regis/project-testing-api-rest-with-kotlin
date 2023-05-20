<h1 align="center">project-testing-api-rest-with-kotlin</h1>
<p align="center">API Rest for a Credit Application Analysis System</p>
<p align="center">
     <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v17-blue.svg" />
    </a>
    <a alt="Kotlin">
        <img src="https://img.shields.io/badge/Kotlin-v1.7.22-purple.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v3.0.3-brightgreen.svg" />
    </a>
    <a alt="Gradle">
        <img src="https://img.shields.io/badge/Gradle-v7.6-lightgreen.svg" />
    </a>
    <a alt="H2">
        <img src="https://img.shields.io/badge/H2-v2.1.214-darkblue.svg" />
    </a>
    <a alt="Flyway">
        <img src="https://img.shields.io/badge/Flyway-v9.5.1-red.svg">
    </a>
</p>

#

### Customer

**Rota: POST `/api/customers`**

```bash
curl -s -X POST -H "Content-Type: application/json" -d '{
  "firstName": "John",
  "lastName": "Doe",
  "cpf": "980.860.250-98",
  "income": 1000.0,
  "email": "john@example.com",
  "password": "123456",
  "zipCode": "654321",
  "street": "John Street"
}' "http://localhost:8080/api/customers"
```

**Rota: GET `/api/customers/{id}`**

```bash
curl -s -X GET "http://localhost:8080/api/customers/1"
```

**Rota: DELETE `/api/customers/{id}`**

```bash
curl -s -X DELETE "http://localhost:8080/api/customers/1"
```

**Rota: PATCH `/api/customers?customerId={id}`**

```bash
curl -s -X PATCH -H "Content-Type: application/json" -d '{
  "firstName": "John",
  "lastName": "Smith",
  "income": 10500.0,
  "zipCode": "654321",
  "street": "John Street"
}' "http://localhost:8080/api/customers?customerId=1"
```

---

### Credits

**Rota: POST `/api/credits`**

```bash
curl -s -X POST -H "Content-Type: application/json" -d '{
  "customerId": 1,
  "creditValue": 300.0,
  "dayFirstOfInstallment": "2023-07-01",
  "numberOfInstallments": 5
}' "http://localhost:8080/api/credits"
```

**Rota: GET `/api/credits?customerId={customerId}`**

```bash
curl -s -X GET "http://localhost:8080/api/credits?customerId=1"
```

**Rota: GET `/api/credits/{creditCode}?customerId={customerId}`**

```bash
curl -s -X GET "http://localhost:8080/api/credits/30d540c4-2e7f-44f7-95f0-0cbbc980365f?customerId=1"
```


