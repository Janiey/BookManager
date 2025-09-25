📚 BookManager
BookManager is a Spring Boot application that uses PostgreSQL as its database.
This guide will help you set up PostgreSQL locally, configure the application, and test APIs using Postman.

🛠️ Requirements
Java 17+
Maven 3+
PostgreSQL 15+
Postman
⚙️ Database Setup
Install PostgreSQL

On Linux/Mac, you can use brew install postgresql or your package manager.
On Windows, download the installer from the official site.
Start PostgreSQL service

# On Linux/macOS
sudo service postgresql start
Login to Postgres shell

psql -U postgres
**Create database **

CREATE DATABASE bookmanager;
-- Ensure the postgres user has the right password ALTER USER postgres WITH PASSWORD 'root';

-- Grant all privileges on the new database to postgres GRANT ALL PRIVILEGES ON DATABASE bookmanager TO postgres;

Verify connection

psql -U postgres -d bookmanager
📂 Application Configuration
In src/main/resources/application.properties:

▶️ Running the Application
mvn clean install -DskipTests
The app will start at:
👉 http://localhost:8080

📬 Testing with Postman
Import the Postman collection (https://janiey-9439640.postman.co/workspace/J's-Workspace~73fe7033-7991-402d-8401-d451271b7e46/collection/48624854-bfd30d83-2619-4478-8f4a-70b2244532c4?action=share&creator=48624854) provided in this repo.
