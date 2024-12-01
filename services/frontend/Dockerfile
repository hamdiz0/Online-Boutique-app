# builder image
FROM golang:1.23.2-alpine AS builder
WORKDIR /src

# set go environment variables
ENV CGO_ENABLED=0 
ENV GOOS=linux 

# restore dependencies
COPY go.mod go.sum ./
RUN go mod download
COPY . .

# build the app
RUN go build -o /go/bin/frontend .

# base image
FROM alpine

WORKDIR /src
COPY --from=builder /go/bin/frontend /src/server
COPY ./templates ./templates
COPY ./static ./static

#  cleaner logs
ENV GOTRACEBACK=single

EXPOSE 8080
ENTRYPOINT ["/src/server"]
