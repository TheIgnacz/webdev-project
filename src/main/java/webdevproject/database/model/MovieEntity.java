package webdevproject.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class MovieEntity {

    @Id
    private String name;

    private String genre;
    private int playtime;

    @OneToMany(mappedBy = "movie")
    private List<ScreeningEntity> screeningEntities;

    public MovieEntity(String name, String genre, int playtime) {
        this.name = name;
        this.genre = genre;
        this.playtime = playtime;
    }

    public MovieEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String type) {
        this.genre = type;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    @Override
    public String toString() {
        return name + " (" + genre + ", " + playtime + " minutes)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieEntity)) {
            return false;
        }
        MovieEntity that = (MovieEntity) o;
        return getPlaytime() == that.getPlaytime()
                && getName().equals(that.getName())
                && getGenre().equals(that.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGenre(), getPlaytime());
    }
}
