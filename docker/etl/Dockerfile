FROM python:3.11-slim

# Install full cron
RUN apt-get update && apt-get install -y cron nano

WORKDIR /app

# Copy ETL code
COPY ../../etl /app/etl

# Install dependencies
RUN pip install --no-cache-dir -r /app/etl/requirements.txt

# Copy cronjob file
COPY docker/etl/cronjob /etc/cron.d/etl-cron

# Set correct permissions
RUN chmod 0644 /etc/cron.d/etl-cron

# Start cron
CMD ["cron", "-f"]
