# Invoice Management API

The **Invoice Management API** is a RESTful web 
service built using Spring Boot that provides an 
easy-to-use interface for creating, managing, and 
processing invoices and payments.


## Features

- **Create Invoices**: Generate new invoices with specified amounts and due dates.
- **View All Invoices**: Retrieve a list of all invoices with details such as amount, due date, paid amount, and status.
- **Invoice Payments**: Make partial or full payments towards an invoice. The invoice's status will be updated accordingly (paid, pending, or void).
- **Process Overdue Invoices**: Automatically process overdue invoices, applying a late fee and either voiding or marking them as paid.

## API Endpoints

### 1. **Create an Invoice**
- **Endpoint**: `POST /invoices`
- **Request Body**:
    ```json
    {
        "amount": 199.99,
        "due_date": "2024-10-19"
    }
    ```
- **Response**: `201 Created`
    ```json
    {
        "id": "1234"
    }
    ```

### 2. **Get All Invoices**
- **Endpoint**: `GET /invoices`
- **Response**: `200 OK`
    ```json
    [
        {
            "id": "1234",
            "amount": 199.99,
            "paid_amount": 0,
            "due_date": "2024-10-19",
            "status": "pending"
        }
    ]
    ```

### 3. **Make a Payment**
- **Endpoint**: `POST /invoices/{id}/payments`
- **Request Body**:
    ```json
    {
        "amount": 159.99
    }
    ```
- **Response**: `201 Created`
    ```json
    {
        "id": "paymentId",
        "amount": 159.99,
        "invoiceId": 1234
    }
    ```

### 4. **Process Overdue Invoices**
- **Endpoint**: `POST /invoices/process-overdue`
- **Request Body**:
    ```json
    {
        "late_fee": 10.5,
        "overdue_days": 10
    }
    ```
- **Response**: `200 OK`

## Technologies Used

- **Java (Spring Boot)**: Backend framework for building the RESTful API.
- **Docker**: Containerization for deploying the API in a consistent environment.
- **Docker Compose**: Used to manage and run the application in a containerized environment.
- **JUnit 5**: Testing framework for unit and integration tests.
- **Maven**: Project build and dependency management.

## Running the API Locally

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [Maven](https://maven.apache.org/install.html) (if you want to run locally without Docker)

### Running the API Using Docker

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/your-repository/invoice-management-api.git
    cd invoice-management-api
    ```

2. **Build the Application**:
   Package the application into a JAR file:
    ```bash
    mvn clean package
    ```

3. **Build and Run Using Docker Compose**:
   Use Docker Compose to build the Docker image and start the application:
    ```bash
    docker-compose up --build
    ```

4. **Access the API**:
   The API will be accessible at `http://localhost:8080`.

### Running the API Without Docker

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/your-repository/invoice-management-api.git
    cd invoice-management-api
    ```

2. **Build the Application**:
   Package the application into a JAR file:
    ```bash
    mvn clean package
    ```

3. **Run the Application**:
   Run the Spring Boot application:
    ```bash
    java -jar target/invoice-api.jar
    ```

4. **Access the API**:
   The API will be accessible at `http://localhost:8080`.

## Project Structure


- **controller/**: Contains the REST API controllers.
- **model/**: Contains the data models (Invoice, Payment, etc.).
- **service/**: Contains the business logic for invoice and payment processing.
- **Dockerfile**: Defines the steps to build the Docker image.
- **docker-compose.yml**: Defines how to run the API in Docker.

## Testing the API

Unit and integration tests are included in the project and can be run using Maven:

```bash
mvn test
