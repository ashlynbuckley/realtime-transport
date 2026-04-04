# Real-time Public Transport Analytics

This code repository is for my CT413 Final Year Project.

Sensitive credentials will be omitted from this repository so if you wish to run the code yourself you will need to get a [developer
licence](https://developer.nationaltransport.ie/) from the NTA and use the keys provided to poll the API endpoints.

---

## Overview

This repository is broken down into three modules: transport-core, spring-app and flink.

- **transport-core:** Holds the Avro schemas and generated classes for serialisation/deserialisation. This is used by both spring-app and flink, thus it is in the shared 
code module.
- **spring-app:** This is the producer service. It's in charge of polling the API endpoints at regular intervals, parsing the JSON responses to POJOs, filtering the POJOs, serialising
the POJOs, and finally publishing them to the respective Apache Kafka topic.
- **flink:** This is the Apache Flink service. It ingests events from the Apache Kafka topics and turns them into data streams. Flink then operates on these streams to produce aggregated metrics
over different windows of time. The metrics themselves pertain to delays and each route's punctuality over time.

---

### Note

The Apache Superset/dashboard component of this project is unfinished. The Superset configurations do not work for MySQL. 

