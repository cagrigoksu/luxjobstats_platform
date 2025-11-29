import pandas as pd
from sqlalchemy import text
from repository.insert_data import insert_dataframe

# dim updater, db is the source
def get_or_create_dim(engine, table_name, col_fr, col_en, values):
    values = [v for v in values if pd.notna(v)]
    uniq = sorted(set(values))
    if not uniq:
        return {}

    with engine.begin() as conn:
        # load existing dims
        rows = conn.execute(text(f"SELECT id, {col_fr} FROM {table_name}"))
        existing = {r[1]: r[0] for r in rows.fetchall()}

        # find new ones
        missing = [v for v in uniq if v not in existing]

        # insert new dims (en same as fr at start)
        for fr_val in missing:
            res = conn.execute(
                text(f"""
                    INSERT INTO {table_name} ({col_fr}, {col_en})
                    VALUES (:fr, :en)
                    RETURNING id
                """),
                {"fr": fr_val, "en": None},
            )
            existing[fr_val] = res.scalar()

    return existing


# dataset 1 loader
def process_dataset1_df(df, engine):
    if df.empty:
        print("[ETL] d1 empty")
        return

    country_map = get_or_create_dim(engine, "dim_country", "country_fr", "country_en", df["country_residence"])
    cont_map = get_or_create_dim(engine, "dim_continent", "continent_fr", "continent_en", df["continent"])
    nat_map = get_or_create_dim(engine, "dim_nationality", "nationality_fr", "nationality_en", df["nationality"])
    sect_map = get_or_create_dim(engine, "dim_sector", "sector_fr", "sector_en", df["sector"])

    fact = pd.DataFrame()
    fact["reference_date"] = df["reference_date"]
    fact["country_id"] = df["country_residence"].map(country_map)
    fact["continent_id"] = df["continent"].map(cont_map)
    fact["nationality_id"] = df["nationality"].map(nat_map)
    fact["sector_id"] = df["sector"].map(sect_map)
    fact["employee_count"] = df["employee_count"]

    _check_missing(df, fact, ["country_id","continent_id","nationality_id","sector_id"], "d1")

    insert_dataframe(fact, "fact_data_by_nationality", engine)


# dataset 2 loader
def process_dataset2_df(df, engine):
    if df.empty:
        print("[ETL] d2 empty")
        return

    gender_map = get_or_create_dim(engine, "dim_gender", "gender_fr", "gender_en", df["gender"])
    res_nat_map = get_or_create_dim(engine, "dim_residence_on_characteristics", "residence_fr", "residence_en", df["residence_nationality"])
    age_map = get_or_create_dim(engine, "dim_age", "age_label_fr", "age_label_en", df["age"])
    sect_map = get_or_create_dim(engine, "dim_sector", "sector_fr", "sector_en", df["sector"])
    status_map = get_or_create_dim(engine, "dim_status", "status_fr", "status_en", df["status"])

    fact = pd.DataFrame()
    fact["reference_date"] = df["reference_date"]
    fact["gender_id"] = df["gender"].map(gender_map)
    fact["residence_nationality_id"] = df["residence_nationality"].map(res_nat_map)
    fact["age_id"] = df["age"].map(age_map)
    fact["sector_id"] = df["sector"].map(sect_map)
    fact["status_id"] = df["status"].map(status_map)
    fact["employee_count"] = df["employee_count"]

    _check_missing(df, fact, ["gender_id","residence_nationality_id","age_id","sector_id","status_id"], "d2")

    insert_dataframe(fact, "fact_data_by_characteristics", engine)


# check unmapped vals
def _check_missing(df, fact_df, cols, name):
    for col in cols:
        if fact_df[col].isna().any():
            print(f"\n[WARN] missing ids in {name} for {col}")
            print(df[fact_df[col].isna()].head())
