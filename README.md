# Mini Procurement & Accounts Payable Module API

A RESTful API built with Java, Spring Boot, and MySQL to simulate core Procure-to-Pay workflows. This project demonstrates enterprise-level backend development, including relational data modeling, state-machine business logic, native SQL reporting, and centralized error handling.

## 🚀 Tech Stack
* **Backend:** Java 25, Spring Boot 4.x, Spring Web, Spring Data JPA
* **Database:** MySQL (XAMPP)
* **Tools:** Maven, Postman, Git

## 🧠 Core Features
* **Supplier Management:** CRUD operations for suppliers.
* **Purchase Order Lifecycle:** Create, approve, and receive purchase orders. Prevents illegal status transitions (e.g., cannot receive a DRAFT order).
* **Invoice Matching:** Create invoices against received POs. Validates that the invoice amount does not exceed the PO total.
* **Ageing Report:** Utilizes a Native SQL query to generate a 30/60/90-day accounts payable ageing report.
* **Global Exception Handling:** Custom `@RestControllerAdvice` to return clean, JSON-formatted errors (preventing raw stack traces from leaking).

## 🛠️ How to Run Locally

1. Clone the repository.
2. Create a MySQL database named `procurement_db`.
3. Update `src/main/resources/application.properties` with your database credentials.
4. Run the application:
   ```bash
   mvn spring-boot:run