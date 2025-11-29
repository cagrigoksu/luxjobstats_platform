CREATE TABLE IF NOT EXISTS dim_residence_on_nationality (
    id SERIAL PRIMARY KEY,
    residence_fr TEXT UNIQUE,
    residence_en TEXT
);

CREATE TABLE IF NOT EXISTS dim_residence_on_characteristics (
    id SERIAL PRIMARY KEY,
    residence_fr TEXT UNIQUE,
    residence_en TEXT
);

CREATE TABLE IF NOT EXISTS dim_continent (
    id SERIAL PRIMARY KEY,
    continent_fr TEXT UNIQUE,
    continent_en TEXT
);

CREATE TABLE IF NOT EXISTS dim_nationality (
    id SERIAL PRIMARY KEY,
    nationality_fr TEXT UNIQUE,
    nationality_en TEXT
);

CREATE TABLE IF NOT EXISTS dim_sector (
    id SERIAL PRIMARY KEY,
    sector_fr TEXT UNIQUE,
    sector_en TEXT
);

CREATE TABLE IF NOT EXISTS dim_gender (
    id SERIAL PRIMARY KEY,
    gender_fr TEXT UNIQUE,
    gender_en TEXT
);

CREATE TABLE IF NOT EXISTS dim_status (
    id SERIAL PRIMARY KEY,
    status_fr TEXT UNIQUE,
    status_en TEXT
);


CREATE TABLE IF NOT EXISTS dim_age (
    id SERIAL PRIMARY KEY,
    age_label_fr TEXT UNIQUE,
    age_label_en TEXT
);


CREATE TABLE IF NOT EXISTS fact_data_by_nationality (
    id SERIAL PRIMARY KEY,
    reference_date DATE,
    residence_id INT REFERENCES dim_residence_on_nationality(id),
    continent_id INT REFERENCES dim_continent(id),
    nationality_id INT REFERENCES dim_nationality(id),
    sector_id INT REFERENCES dim_sector(id),
    number_of_employee INT
);

CREATE TABLE IF NOT EXISTS fact_data_by_characteristics (
    id SERIAL PRIMARY KEY,
    reference_date DATE,
    gender_id INT REFERENCES dim_gender(id),
    residence_id INT REFERENCES dim_residence_on_characteristics(id),
    age_id INT REFERENCES dim_age(id),
    sector_id INT REFERENCES dim_sector(id),
    status_id INT REFERENCES dim_status(id),
    number_of_employee INT
);
