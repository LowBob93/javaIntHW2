package HomeWorkFour.DB;

import HomeWorkFour.Model.Movie;
import HomeWorkFour.Model.MovieSession;

import java.sql.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbHandler {
    public static Connection conn;
    public static Statement stat;
    public static ResultSet resSet;


    public static void conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:./movie.db;MODE=PostgreSQL", "state", "111");
        stat = conn.createStatement();
        System.out.println("DataBase connected");
    }

    public static void createDB() throws SQLException {
        stat.execute("CREATE TABLE IF NOT EXISTS movies (\n" +
                "id BIGSERIAL NOT NULL UNIQUE,\n" +
                "name VARCHAR(255) NOT NULL,\n" +
                "duration INTEGER NOT NULL,\n" +
                "PRIMARY KEY(id));");
        stat.execute("CREATE TABLE IF NOT EXISTS sessions (\n" +
                "id BIGSERIAL NOT NULL UNIQUE,\n" +
                "movie_id INTEGER NOT NULL,\n" +
                "start_time TIME NOT NULL,\n" +
                "cost INTEGER NOT NULL,\n" +
                "FOREIGN KEY(film_id) REFERENCES movies(id),\n" +
                "PRIMARY KEY(id));");
        stat.execute("CREATE TABLE IF NOT EXISTS tickets (\n" +
                "id BIGSERIAL NOT NULL,\n" +
                "session_id INTEGER NOT NULL,\n" +
                "PRIMARY KEY(id),\n" +
                "FOREIGN KEY(session_id) REFERENCES sessions(id));");

        System.out.println("Table already created");
    }


    public static void writeDB() throws SQLException {
        stat.execute("INSERT INTO movies (name, duration) VALUES \n" +
                "('Movie43', 90)," +
                "('D Day', 90)," +
                "('Lord of the rings', 120)," +
                "('Borat', 60)," +
                "('History x', 120); ");

        LocalTime time = LocalTime.of(7, 30);

        stat.execute("INSERT INTO sessions (movie_id, start_time, cost) VALUES " +
                "(1, '" + time + "', 5)," +                                                                // 1
                "(3, '" + time.plusHours(1).plusMinutes(5) + "', 8)," +           // 2
                "(5, '" + time.plusHours(2).plusMinutes(25) + "', 6)," +          // 3
                "(1, '" + time.plusHours(4).plusMinutes(40) + "', 7)," +          // 4
                "(3, '" + time.plusHours(5).plusMinutes(50) + "', 12)," +         // 5
                "(2, '" + time.plusHours(8).plusMinutes(30) + "', 10)," +         // 6
                "(4, '" + time.plusHours(10).plusMinutes(40) + "', 15)," +        // 7
                "(5, '" + time.plusHours(14).plusMinutes(10) + "', 13);");        // 8

        stat.execute("INSERT INTO tickets (session_id) VALUES " +
                "(2)," +
                "(2)," +
                "(3)," +
                "(5)," +
                "(1)," +
                "(3)," +
                "(5)," +
                "(6)," +
                "(7)," +
                "(8)," +
                "(6)," +
                "(2)," +
                "(4)," +
                "(7)," +
                "(3)," +
                "(8)," +
                "(4)," +
                "(1)," +
                "(3)," +
                "(5)," +
                "(8)," +
                "(2)," +
                "(7)," +
                "(6)," +
                "(2)," +
                "(4)," );

    }

    // -------- Вывод таблицы--------
    public static void readDB() throws SQLException {
        final List<MovieSession> sessionData = getSessionData();
        if (sessionData.size() < 2) {
            return;
        }
        printErrorSchedule(sessionData);
        printLongBreak(sessionData);
        printFilmStatistic(sessionData);

    }

    private static void printErrorSchedule(final List<MovieSession> sessionData) {
        System.out.println("Schedule error");
        for (int i = 1; i < sessionData.size(); i++) {
            MovieSession current = sessionData.get(i);
            MovieSession previous = sessionData.get(i - 1);
            boolean errorSchedule = current.getStartTime()
                    .isBefore(
                            previous.getStartTime().plusMinutes(previous.getMovie().getDuration()));
            if (errorSchedule) {
                System.out.println(previous.getMovie().getName() + " | " + previous.getStartTime() + " | " + previous.getMovie().getDuration() + " | " +
                        current.getMovie().getName() + " | " + current.getStartTime() + " | " + current.getMovie().getDuration()
                );
            }
        }
    }

    private static void printLongBreak(final List<MovieSession> sessionData) {
        System.out.println("Long break");
        Map<Integer, List<MovieSession>> breaks = new HashMap<>();
        for (int i = 1; i < sessionData.size(); i++) {
            MovieSession current = sessionData.get(i);
            MovieSession previous = sessionData.get(i - 1);
            boolean longBreak = current.getStartTime()
                    .isAfter(
                            previous.getStartTime().plusMinutes(previous.getMovie().getDuration() + 30));
            if (longBreak) {
                final int between = (int) -ChronoUnit.MINUTES.between(current.getStartTime(), previous.getStartTime().plusMinutes(previous.getMovie().getDuration()));
                breaks.put(between, List.of(previous, current));
            }
        }
        final List<Integer> collect = breaks.keySet().stream()
                .sorted((o1, o2) -> o2 - o1)
                .collect(Collectors.toList());
        for (Integer i : collect) {
            final List<MovieSession> sessions = breaks.get(i);
            MovieSession previous = sessions.get(0);
            MovieSession current = sessions.get(1);
            System.out.println(previous.getMovie().getName() + " | " + previous.getStartTime() + " | " + previous.getMovie().getDuration() + " | " +
                    current.getStartTime() + " | " + i);
        }
    }

    private static void printFilmStatistic(final List<MovieSession> sessionData) {
        System.out.println("Statistic");
        final Map<Movie, List<MovieSession>> collect = sessionData.stream().collect(Collectors.groupingBy(MovieSession::getMovie, Collectors.toList()));
        for (Movie f : collect.keySet()) {
            int allCount = collect.get(f).stream()
                    .mapToInt(MovieSession::getTicketCount)
                    .sum();
            double avgCount = collect.get(f).stream()
                    .mapToInt(MovieSession::getTicketCount)
                    .average().getAsDouble();
            int sum = collect.get(f).stream()
                    .mapToInt(i -> i.getCost() * i.getTicketCount())
                    .sum();
            System.out.println(f.getName() + " | " + allCount + " | " + avgCount + " | " + sum);
        }
        int allCount = sessionData.stream()
                .mapToInt(MovieSession::getTicketCount)
                .sum();
        double avgCount = sessionData.stream()
                .mapToInt(MovieSession::getTicketCount)
                .average().getAsDouble();
        int sum = sessionData.stream()
                .mapToInt(i -> i.getCost() * i.getTicketCount())
                .sum();
        System.out.println("итого | " + allCount + " | " + avgCount + " | " + sum);
    }

    private static List<MovieSession> getSessionData() throws SQLException {
        String query = "select COUNT(t.ID), t.SESSION_ID, s.MOVIE_id, s.START_TIME, s.COST, f.NAME, f.DURATION FROM TICKETS t\n" +
                "join SESSIONS s ON t.SESSION_ID = s.ID \n" +
                "JOIN MOVIE f on s.MOVIE_ID = f.ID\n" +
                "group by t.SESSION_ID ORDER BY s.START_TIME";

        List<MovieSession> sessions = new ArrayList<>();

        resSet = stat.executeQuery(query);
        while (resSet.next()) {
            sessions.add(
                    MovieSession.builder()
                            .id(resSet.getLong(2))
                            .startTime(resSet.getObject(4, LocalTime.class))
                            .cost(resSet.getInt(5))
                            .movie(
                                    Movie.builder()
                                            .id(resSet.getLong(3))
                                            .name(resSet.getString(6))
                                            .duration(resSet.getInt(7))
                                            .build()
                            )
                            .ticketCount(resSet.getInt(1))
                            .build()
            );

        }
        return sessions;
    }

    // --------Закрытие--------
    public static void closeDB() throws SQLException {
        conn.close();
        System.out.println("Connection close");
    }
}