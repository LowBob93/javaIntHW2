package HomeWorkFour.Model;

import java.util.Objects;

public class Movie {
    private Long id;
    private String name;
    private int duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Movie movie = (Movie) o;
        return id.equals(movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder{
        private Movie movie;

        public Builder() {
            this.movie = new Movie();
        }

        public Builder id(final Long id) {
            movie.id = id;
            return this;
        }

        public Builder name(final String name) {
            movie.name = name;
            return this;
        }

        public Builder duration(final int duration) {
            movie.duration = duration;
            return this;
        }

        public Movie build(){
            return movie;
        }

    }
}