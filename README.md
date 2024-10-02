
# SpringAI PGVector RAG Store Demo

This is a demo project that uses **Spring Boot** and **PGVector** to implement a **Retrieval-Augmented Generation (RAG)** system that stores and retrieves vectorized documents in a PostgreSQL database with the PGVector extension.

## Overview

The application reads data from a file (`input.txt`), splits the text into smaller documents using a text splitter, vectorizes them, and stores the vectors in a PostgreSQL database using the **PGVector** extension. When the Spring Boot application starts, it checks if the database is empty and, if so, inserts the vectorized documents.

## Technologies Used

- **Java**: 21
- **Spring Boot**: 3.2.10
- **PGVector**: Extension for PostgreSQL to handle vectorized data.
- **PostgreSQL**: Database for storing vectorized data.
- **Spring AI**: For interacting with vectors and documents.

## Prerequisites

- **Docker** (to run PostgreSQL with the PGVector extension).
- **Java 21** installed.
- **Maven** (to build and run the Spring Boot project).

## Setup Instructions

### Step 1: Clone the Repository

```bash
git clone https://github.com/LegPro/springai-pgvector-rag-store-demo.git
cd springai-pgvector-rag-store-demo
```

### Step 2: Set Up PostgreSQL with PGVector

To set up PostgreSQL with the PGVector extension, use the following Docker command:

```bash
docker run -it --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres pgvector/pgvector:0.7.4-pg16
```

This command will:

- Set up a PostgreSQL instance with PGVector installed.
- Expose the database on port `5432` with username `postgres` and password `postgres`.

### Step 3: Configure the Database Connection

The application uses the default PostgreSQL connection parameters (`localhost:5432`, user `postgres`, and password `postgres`). These settings are stored in `src/main/resources/application.properties`.

If needed, update the connection settings in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Step 4: Prepare the Input Data

Place your text data in the `src/main/resources/input.txt` file. This file will be used to generate documents that will be vectorized and stored in the PostgreSQL database.

### Step 5: Build and Run the Application

You can build and run the Spring Boot application using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

### Step 6: Verify Vector Storage

When the application runs, it will check if there are any existing vectors in the database. If there are none, it will read from the `input.txt` file, split the text into smaller documents, vectorize them, and store them in the database.

You should see output similar to:

```bash
No of Records: 0
```

Once the documents are processed, they will be added to the vector store, and future runs will detect that records already exist in the database.

## Project Structure

```bash
springai-pgvector-rag-store-demo/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── pgvectorragdemo
│   │   │               └── PgVectorRagDemoApplication.java
│   └── resources
│       └── input.txt
│       └── application.properties
├── pom.xml
└── README.md
```

- **PgVectorRagDemoApplication.java**: The main application class where vectorized documents are added to the database if they don't already exist.
- **input.txt**: The text file that contains the input data to be vectorized and stored.
- **application.properties**: Configuration file for database connection and other Spring Boot settings.

## Key Components

- **VectorStore**: Used for storing and managing vectorized documents.
- **TextSplitter**: A component that splits text into smaller documents based on tokens.
- **JdbcClient**: Used to interact with the PostgreSQL database.

## Maven Dependencies

The project includes the following key dependencies:

```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Data JPA for PostgreSQL -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Spring AI Dependencies for working with documents and vectors -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai</artifactId>
        <version>1.0.0</version>
    </dependency>

    <!-- Spring Boot Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Additional Configuration

You can customize the following:

- **Text Splitting**: Modify how documents are split into smaller parts using the `TextSplitter` (currently uses `TokenTextSplitter`).
- **Vectorization**: Customize how the text is vectorized before storing it in the PostgreSQL database.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contributing

Feel free to open issues or submit pull requests if you find any bugs or have suggestions for improvements.
