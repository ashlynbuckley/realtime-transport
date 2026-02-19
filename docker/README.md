# Docker Instructions

## Overview
Currently, Kafka and MySQL run in docker containers and Flink can be run through Intellij using flink-clients.

---
## Step 1:
Enter the docker folder. 

In the terminal, run: `docker compose up -d`

## Step 2:
Confirm both the Kafka and the MySQL containers are running using: `docker ps -a`

## Step 3:
Each time you start the containers, you will have to configure some things. Firstly: a Kafka topic.

To enter the Kafka terminal, do:
`docker exec -it -w /opt/kafka/bin broker sh`

You should see `/opt/kafka/bin $`

Next, create the topic, type:

```
./kafka-topics.sh \
  --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

Then, add some messages:

`./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test-topic`

These messages will remain until you stop the container - even if Flink ingests them.

## Step 4:
Now, let's set up the MySQL side.

`docker exec -it mysql bash`

`mysql -u flink -p`

Once in the mysql> cmd line:

`USE flink_test;`

```
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
```
INSERT INTO messages (content)
VALUES ("test message");
```

## Step 5:
Run FlinkJob.

You can see if the code has ran successfully using: `SELECT * FROM messages;` 

This should return what you inputted into Kafka.

You should also see in the Run output the Kafka topics being ingested and printed there.

---

## Testing Spring With Kafka Container

You can watch a kafka topic fill up with messages using:
```
./kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic test-topic-sb \
    --from-beginning
```

## Additional Helpful Cmds

`docker compose logs [container name]`

`./kafka-topics.sh  --list --bootstrap-server localhost:9092`

`./kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic test-topic`

`docker compose down`

