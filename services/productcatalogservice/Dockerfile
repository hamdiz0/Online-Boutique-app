# builder image
FROM golang:1.23.2-alpine AS builder

# set go environment variables
ENV CGO_ENABLED=0 
ENV GOOS=linux 

WORKDIR /src
# restore dependencies
COPY go.mod go.sum ./
RUN go mod download
COPY . .

# build the app
RUN go build -o /productcatalogservice .

# base image
FROM alpine

WORKDIR /src
COPY --from=builder /productcatalogservice ./server
COPY --from=builder /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
COPY products.json .

# cleaner logs
ENV GOTRACEBACK=single

EXPOSE 3550
ENTRYPOINT ["/src/server"]
