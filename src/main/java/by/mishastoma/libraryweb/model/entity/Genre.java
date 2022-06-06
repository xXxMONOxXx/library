package by.mishastoma.libraryweb.model.entity;

public class Genre extends AbstractEntity{
    private String genre;

    public Genre(long id, String genre) {
        super(id);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
