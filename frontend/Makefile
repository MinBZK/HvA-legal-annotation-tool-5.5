CURRENT_DIR=$(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))
DOCKER_COMPOSE=docker-compose
DOCKER_NAME=vite_docker

.PHONY: build install dev up start first stop restart clear

build:
	$(DOCKER_COMPOSE) up --build --no-recreate -d

install:
	docker exec -it $(DOCKER_NAME) npm install

dev:
	docker exec -it $(DOCKER_NAME) npm run dev

up: build

start: up dev

first: build install dev

stop:
	$(DOCKER_COMPOSE) kill || true
	$(DOCKER_COMPOSE) rm --force || true

restart: stop start dev

clear: stop
	$(DOCKER_COMPOSE) down -v --remove-orphans || true
