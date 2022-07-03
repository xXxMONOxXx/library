package by.mishastoma.libraryweb.model.entity;

public class Genre extends AbstractEntity{
    private String name;

    public Genre(long id, String genre) {
        super(id);
        this.name = genre;
    }

    public Genre(long id) {
        super(id);
        this.name = null;
    }

    public String getName() {
        return name;
    }

    public void setGenre(String genre) {
        this.name = genre;
    }
}
