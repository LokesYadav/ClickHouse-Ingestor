# ClickHouse File Ingestor

## Project Overview
ClickHouse File Ingestor is a data ingestion tool designed to facilitate the ingestion of flat files into ClickHouse, a fast open-source column-oriented database management system. This project consists of a backend service built with Java (Spring Boot) and a frontend web application built with React.

## Backend
The backend is implemented in Java using the Spring Boot framework. It exposes RESTful APIs to handle ingestion requests, process flat files, and interact with ClickHouse for data storage.

### Backend Structure
- `backend/src/main/java/com/ingestiontool/` - Contains the main application, controllers, and service classes.
- `backend/pom.xml` - Maven project file managing dependencies and build lifecycle.
- `backend/target/` - Compiled classes and build artifacts.

### Building and Running Backend
Make sure you have Java and Maven installed.

To build the backend:
```bash
cd backend
mvn clean install
```

To run the backend:
```bash
mvn spring-boot:run
```

The backend service will start and listen for ingestion requests.

## Frontend
The frontend is a React application that provides a user interface to interact with the ingestion tool. It allows users to configure ingestion parameters and monitor ingestion status.

### Frontend Structure
- `frontend/src/` - React source code including components and styles.
- `frontend/public/` - Static files including the main HTML file.
- `frontend/package.json` - Node.js project file managing dependencies and scripts.

### Running Frontend
Make sure you have Node.js and npm installed.

To install dependencies and start the frontend development server:
```bash
cd frontend
npm install
npm start
```

The frontend will be available at `http://localhost:3000` by default.

## Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- Node.js 12 or higher
- npm 6 or higher
- ClickHouse database instance

## Usage
1. Start the ClickHouse database.
2. Run the backend service.
3. Run the frontend application.
4. Use the frontend UI to configure and initiate file ingestion.

## License
This project is licensed under the MIT License.

## Contact
For questions or support, please contact the project maintainers.
