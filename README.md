# Payment processor

## Requirements

- JDK 21+

## How to run the application

Clone the repository to your computer. Open the terminal inside the `payment-processor` directory, and execute the command `./mvnw spring-boot:run` to run the Spring Boot app.

You can access the database by going to `http://localhost:8080/h2-console`, after starting the backend. In the `JDBC URL:` field, type `jdbc:h2:mem:payment_processor`. `User name:` is `sa`, password field is empty.

## Examples

### Creating a payment

Request:

`POST /api/payments`

```JSON
{
  "type": "TYPE1",
  "amount": 100.50,
  "currency": "EUR",
  "debtorIban": "LT012345978901234567",
  "creditorIban": "LT481254874512355648",
  "details": "Skola"
}
```

Response:

```JSON
{
  "id": 1,
  "type": "TYPE1",
  "amount": 100.50,
  "currency": "EUR",
  "debtorIban": "LT012345978901234567",
  "creditorIban": "LT481254874512355648",
  "details": "Skola",
  "creditorBic": null,
  "createdAt": "2025-06-15T23:06:58",
  "cancelledAt": null,
  "cancellationFee": null,
  "cancelled": false
}
```

### Cancelling a payment

Request:

`PUT /api/payments/1/cancel`

- No request body needed

Response:

```JSON
{
  "id": 1,
  "type": "TYPE1",
  "amount": 100.50,
  "currency": "EUR",
  "debtorIban": "LT012345978901234567",
  "creditorIban": "LT481254874512355648",
  "details": "Skola",
  "creditorBic": null,
  "createdAt": "2025-06-15T23:06:58",
  "cancelledAt": "2025-06-15T23:09:55",
  "cancellationFee": 0.00,
  "cancelled": true
}
```
