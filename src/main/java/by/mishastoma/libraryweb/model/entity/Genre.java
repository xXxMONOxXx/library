package by.mishastoma.libraryweb.model.entity;

public class Genre extends AbstractEntity{
    private String name;

    public Genre(long id, String genre) {
        super(id);
        this.name = genre;
    }

    public String getName() {
        return name;
    }

    public void setGenre(String genre) {
        this.name = genre;
    }
}
