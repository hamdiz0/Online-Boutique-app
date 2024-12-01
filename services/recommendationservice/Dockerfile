FROM python:3.12.6-slim

# install g++
RUN apt-get -qq update \
    && apt-get install -y --no-install-recommends \
        wget g++ \
    && rm -rf /var/lib/apt/lists/*

# get packages
COPY requirements.txt .
RUN pip install -r requirements.txt

# Enable unbuffered logging
ENV PYTHONUNBUFFERED=1

# get packages
WORKDIR /recommendationservice

# Add the application
COPY . .

# set listen port
ENV PORT "8080"
EXPOSE 8080

ENTRYPOINT ["python", "recommendation_server.py"]
