# Luxembourg Job Stats Platform

# Project Structure

## LuxJobStats-API (Java Web API)
![Java](https://img.shields.io/badge/Java-21-yellow)
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

### Sample Endpoints
| Endpoint | Description |
|----------|-------------|
| `GET /api/sectors` | List all sectors |
| `GET /api/salaries/sector` | Salaries by sector |
| `GET /api/nationalities` | Nationalities dimension |
| `GET /api/salaries/nationality/details` | Detailed nationality salary breakdown |

---

## ETL Module (Python Data Pipeline)
![Python](https://img.shields.io/badge/Python-3.11-blue)
![Pandas](https://img.shields.io/badge/Pandas-2.3.3-orange)
![NumPy](https://img.shields.io/badge/NumPy-2.3.4-yellow)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-Enabled-informational)


The **ETL module** is responsible for collecting, transforming, and loading the official Luxembourg job-market datasets into PostgreSQL. This data is then consumed by the Java API.

The ETL runs inside its own Docker container using a cron-based schedule.

### Extract
- Reads the official `.xlsx` datasets (salaries, sectors, nationalities, residence, etc.).
- Uses **pandas** and **openpyxl** to load data from the local dataset directory.
- Performs validation and schema checks before transformation.

### Transform

- Cleans and standardizes fields (dates, numbers, naming).
- Builds dimension tables (country, nationality, sector, gender, status…).
- Automatically translates French labels into English using the **DeepL API**.
Only missing translations are processed (*_en IS NULL_) to avoid unnecessary API calls.

### Load

- Inserts/updates data into a **PostgreSQL** database.

### Infrastructure

- Runs as a Dockerized service with its own environment.
- Runs _**daily**_ dataset source check.
- Logs all operations to `/var/log/etl.log`.
