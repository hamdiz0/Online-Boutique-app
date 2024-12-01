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
RUN go build -o /go/bin/shippingservice .

FROM alpine

WORKDIR /src
COPY --from=builder /go/bin/shippingservice /src/shippingservice
ENV APP_PORT=50051

# Definition of this variable is used by 'skaffold debug' to identify a golang binary.
# Default behavior - a failure prints a stack trace for the current goroutine.
# See https://golang.org/pkg/runtime/
ENV GOTRACEBACK=single

EXPOSE 50051
ENTRYPOINT ["/src/shippingservice"]
