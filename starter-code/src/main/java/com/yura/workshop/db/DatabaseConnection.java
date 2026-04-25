package com.yura.workshop.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConnection {

    private static HikariDataSource dataSource;

    private DatabaseConnection() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(resolveUrl());
            config.setUsername(resolveUser());
            config.setPassword(resolvePass());
            config.setMaximumPoolSize(5);
            config.setConnectionTimeout(10_000);
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    private static String resolveUrl() {
        String env = System.getenv("DB_URL");
        if (env != null) {
            return env;
        }
        String prop = System.getProperty("db.url");
        return prop != null ? prop : "jdbc:postgresql://localhost:5432/workshop_db";
    }

    private static String resolveUser() {
        String env = System.getenv("DB_USER");
        if (env != null) {
            return env;
        }
        String prop = System.getProperty("db.user");
        return prop != null ? prop : "workshop_user";
    }

    private static String resolvePass() {
        String env = System.getenv("DB_PASS");
        if (env != null) {
            return env;
        }
        String prop = System.getProperty("db.pass");
        return prop != null ? prop : "workshop_pass";
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
