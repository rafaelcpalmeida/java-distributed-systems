# <3
# https://news.ycombinator.com/item?id=11939200
.PHONY: help
help:	### this screen. Keep it first target to be default
ifeq ($(UNAME), Linux)
	@grep -P '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'
else
	@# this is not tested, but prepared in advance for you, Mac drivers
	@awk -F ':.*###' '$$0 ~ FS {printf "%15s%s\n", $$1 ":", $$2}' \
		$(MAKEFILE_LIST) | grep -v '@awk' | sort
endif

.PHONY: setup-environment
setup-environment:	### Creates required docker network
	$(info Going to create distributed_systems_docker_network docker network...)
	@docker network create distributed_systems_docker_network

.PHONY: build-all
build-all: build-server	build-client	### Builds RMI Server and Client images

.PHONY: build-server
build-server:	### Build RMI Server image
	@docker build server/ -t rmi-server

.PHONY: build-client
build-client:	### Build RMI Client image
	@docker build client/ -t rmi-client

.PHONY: run-web-server
run-web-server: ### Runs Python 3 http.server on port 8000
	@docker run -it --rm -p 8000:8000 --name=rmi_python_server --network=distributed_systems_docker_network \
	--workdir="/app/bin" -v "$(PWD)/bin:/app/bin" \
	python:3.8 bash -c "python -m http.server 8000"

JAR_LOCATION = "empty"
JAR_NAME = "empty"
.PHONY: run-server
run-server:	### Runs RMI server
ifndef PACKAGE_NAME
	$(error Missing PACKAGE_NAME variable. Usage: make run-server JAR_LOCATION (optional) JAR_NAME (optional) PACKAGE_NAME SERVICE_NAME)
endif
ifndef SERVICE_NAME
	$(error Missing SERVICE_NAME variable. Usage: make run-server JAR_LOCATION (optional) JAR_NAME (optional) PACKAGE_NAME SERVICE_NAME)
endif

	@docker run -it --rm -p 1099:1099 --name=rmi_run_server --network=distributed_systems_docker_network \
        -v "$(PWD)/bin:/app/bin" \
        -v "$(PWD)/security-policies:/app/security-policies" \
        -v "$(PWD)/server:/app" \
        rmi-server bash -c "./run-server.sh $(JAR_LOCATION) $(JAR_NAME) $(PACKAGE_NAME) $(SERVICE_NAME)"

JAR_LOCATION = "empty"
JAR_NAME = "empty"
.PHONY: run-client
run-client:	### Runs RMI client
ifndef PACKAGE_NAME
	$(error Missing PACKAGE_NAME variable. Usage: make run-server JAR_LOCATION (optional) JAR_NAME (optional) PACKAGE_NAME SERVICE_NAME)
endif
ifndef SERVICE_NAME
	$(error Missing SERVICE_NAME variable. Usage: make run-server JAR_LOCATION (optional) JAR_NAME (optional) PACKAGE_NAME SERVICE_NAME)
endif

	@docker run -it --rm --name=rmi_run_client --network=distributed_systems_docker_network \
	-v "$(PWD)/bin:/app/bin" \
	-v "$(PWD)/security-policies:/app/security-policies" \
	-v "$(PWD)/client:/app" \
	rmi-client bash -c "./run-client.sh $(JAR_LOCATION) $(JAR_NAME) $(PACKAGE_NAME) $(SERVICE_NAME)"

.PHONY: update-files
update-files: ### Copies builded files to proper location
	cp -R /Users/rafaelalmeida/projects/java-distributed-systems/SD/out/production/SD/edu /Users/rafaelalmeida/projects/java-distributed-systems/bin/classes