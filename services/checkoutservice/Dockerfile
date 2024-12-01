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

# Skaffold passes in debug-oriented compiler flags
ARG SKAFFOLD_GO_GCFLAGS
RUN go build -o /checkoutservice .

FROM alpine

WORKDIR /src
COPY --from=builder /checkoutservice /src/checkoutservice

#  cleaner logs
ENV GOTRACEBACK=single

EXPOSE 5050
ENTRYPOINT ["/src/checkoutservice"]
