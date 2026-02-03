
package com.transport.flink.sink;

import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLSinkFactory {
    //Note: It's on port 3307 b/c (apparently) there's already a mysql db on 3306
    private static final String URL = "jdbc:mysql://localhost:3307/flink_test";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "flink";
    private static final String PASSWORD = "flink";

    public static SinkFunction<String> configureSink() {
        String sql = "INSERT INTO messages (content) VALUES (?)";

        JdbcExecutionOptions options = JdbcExecutionOptions.builder()
                .withBatchSize(1)
                .build();

        return JdbcSink.sink(
                sql,
                new JdbcStatementBuilder<String>() {
                    @Override
                    public void accept(PreparedStatement ps, String value) throws SQLException {
                        ps.setString(1, value);
                    }
                },
                options,
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl(URL)
                        .withDriverName(DRIVER_NAME)
                        .withUsername(USER)
                        .withPassword(PASSWORD)
                        .build()
        );
    }
}
