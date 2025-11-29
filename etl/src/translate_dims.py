import time
import requests
from sqlalchemy import text
from database import get_engine
from config import DEEPL_API_KEY

DEEPL_URL = "https://api-free.deepl.com/v2/translate"

def deepl_batch(texts):
    if not texts:
        return []

    payload = {
        "auth_key": DEEPL_API_KEY,
        "target_lang": "EN",
    }

    for t in texts:
        payload.setdefault("text", []).append(t)

    for attempt in range(5):
        try:
            r = requests.post(DEEPL_URL, data=payload, timeout=10)
            if r.status_code == 429:
                print("rate-limit, wait a bit...")
                time.sleep(5 * (attempt + 1))  # every 5 sec
                continue

            r.raise_for_status()
            data = r.json()
            outs = [item["text"] for item in data["translations"]]
            return outs

        except Exception as ex:
            print("err, retry...", ex)
            time.sleep(3)

    # write fr if cant translate
    print("failed after retries. return fr")
    return texts


def translate_dim(engine, table, col_fr, col_en):
    print(f"\n--- translate: {table} ---")

    with engine.begin() as conn:
        rows = conn.execute(
            text(f"""
                SELECT id, {col_fr}, {col_en}
                FROM {table}
                ORDER BY id;
        """)).fetchall()

    # only null rows
    to_translate = [(rid, fr) for rid, fr, en in rows if not en or str(en).strip() == ""]
    if not to_translate:
        print("nothing to translate")
        return

    ids = [x[0] for x in to_translate]
    fr_vals = [x[1] for x in to_translate]

    print(f"{len(fr_vals)} items need translation")

    # translate in batches of 40
    BATCH = 40
    en_results = {}

    for i in range(0, len(fr_vals), BATCH):
        batch = fr_vals[i:i+BATCH]
        print(f" translate batch {i} -> {i+len(batch)}")

        en_batch = deepl_batch(batch)
        # delay for free api 
        time.sleep(1.0)

        for fr, en in zip(batch, en_batch):
            en_results[fr] = en

    with engine.begin() as conn:
        for rid, fr in to_translate:
            en = en_results.get(fr, fr)
            conn.execute(
                text(f"UPDATE {table} SET {col_en} = :en WHERE id = :id"),
                {"en": en, "id": rid}
            )


def run():
    engine = get_engine()

    dims = [
        ("dim_country", "country_fr", "country_en"),
        ("dim_continent", "continent_fr", "continent_en"),
        ("dim_nationality", "nationality_fr", "nationality_en"),
        ("dim_sector", "sector_fr", "sector_en"),
        ("dim_gender", "gender_fr", "gender_en"),
        ("dim_status", "status_fr", "status_en"),
        ("dim_residence_on_characteristics", "residence_fr", "residence_en"),
        ("dim_age", "age_label_fr", "age_label_en"),
    ]

    for table, col_fr, col_en in dims:
        translate_dim(engine, table, col_fr, col_en)


if __name__ == "__main__":
    run()
