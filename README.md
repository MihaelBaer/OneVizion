# OneVizion Book Management System

The OneVizion Book Management System is a Spring Boot application designed to manage books, authors, and related data. It provides functionalities such as listing books, adding new books, and retrieving books grouped by authors or sorted by titles.

## Features

- List all books sorted by title in descending order.
- Add new books with details like title, author, and description.
- Retrieve books grouped by author.
- Get top authors based on character occurrences in book titles.

## Technical Stack

- **Spring Boot**: Framework for building standalone, production-grade Spring-based Applications.
- **Spring Data JPA**: Module for database access, enhancing JDBC with repository support.
- **Spring Validation**: Support for validation of data models.
- **PostgreSQL**: Database for persistent storage.
- **Liquibase**: Database migration tool.
- **MapStruct**: Code generator for converting between Java bean types.
- **Lombok**: Library to minimize boilerplate code.
- **OpenAPI**: Documentation of REST APIs.
- **Infinispan**: Distributed cache for Hibernate (queries and states).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 17
- Gradle 7+
- PostgreSQL 15+

### Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/MihaelBaer/OneVizion

2  **Provide environment variables of your db via IDE or application.properties file**

3  **Run app**
