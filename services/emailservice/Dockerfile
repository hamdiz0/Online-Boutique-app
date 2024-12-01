FROM python:3.12.7-alpine

# install g++ (c++ compiler)
RUN apk update \
    && apk add --no-cache wget g++ \
    && rm -rf /var/cache/apk/*

# get packages
COPY requirements.txt .
RUN pip install -r requirements.txt

# enable unbuffered logging
ENV PYTHONUNBUFFERED=1

# enable Profiler
ENV ENABLE_PROFILER=1

WORKDIR /email_server

# Add the application
COPY . .

EXPOSE 8080
ENTRYPOINT [ "python", "email_server.py" ]
