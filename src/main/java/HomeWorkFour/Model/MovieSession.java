package HomeWorkFour.Model;

import java.time.LocalTime;

public class MovieSession {
    private Long id;
    private int cost;
    private Movie movie;
    private LocalTime startTime;
    private int ticketCount;

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(final int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setFilm(final Movie movie) {
        this.movie = movie;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private MovieSession movieSession;

        public Builder() {
            this.movieSession = new MovieSession();
        }

        public Builder id(final Long id) {
            movieSession.id = id;
            return this;
        }

        public Builder startTime(final LocalTime startTime) {
            movieSession.startTime = startTime;
            return this;
        }

        public Builder cost(final int cost) {
            movieSession.cost = cost;
            return this;
        }

        public Builder movie(final Movie movie) {
            movieSession.movie = movie;
            return this;
        }

        public Builder ticketCount(final int ticketCount) {
            movieSession.ticketCount = ticketCount;
            return this;
        }

        public MovieSession build(){
            return movieSession;
        }
    }
}