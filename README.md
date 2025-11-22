# Luxembourg Job Stats Platform

# 📂 Project Structure

## LuxJobStats-API (Java Web API)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-orange)
![Docker](https://img.shields.io/badge/Docker-Enabled-informational)

The **Java API** is the backend service of the *LuxJobStats* platform.  
It exposes REST endpoints for structured labour-market data in Luxembourg.  
All data used here is pre-processed and loaded into PostgreSQL by the ETL service.

The goal of this service is simple:  
**provide clean, reliable, query-ready information for dashboards, analytics, and external clients.**

- ### Controllers
Receive requests → call services → return JSON.

- ### Services
Contain the logic: filtering, DTO mapping.

- ### Repositories
Spring Data JPA interfaces that query the PostgreSQL tables created by the ETL.

- ### Profiles & Configuration
    - `application.properties` for local  
    - `application-docker.properties` for Docker  

### 📊 Sample Endpoints
| Endpoint | Description |
|----------|-------------|
| `GET /api/sectors` | List all sectors |
| `GET /api/salaries/sector` | Salaries by sector |
| `GET /api/nationalities` | Nationalities dimension |
| `GET /api/salaries/nationality/details` | Detailed nationality salary breakdown |

## ETL Module

This project includes an ETL pipeline that automatically processes the Luxembourg job-market datasets used throughout the platform. The ETL runs inside a dedicated Docker container and performs the following steps:

### Extract

* Reads the official `.xlsx` datasets (employment, salaries, sectors, nationalities, etc.).

* Uses pandas to load and validate data.

### Transform

* Cleans and standardizes fields (dates, numbers, naming).

* Builds dimension tables (country, nationality, sector, gender, status…).

* Automatically translates French labels into English using the **DeepL API**.
Only missing translations are processed (*_en IS NULL_) to avoid unnecessary API calls.

### Load

* Inserts/updates data into a **PostgreSQL** database.

### Infrastructure

* Runs as a Dockerized service with its own environment.
* Runs _**daily**_ dataset source check.
* Logs all operations to `/var/log/etl.log`.