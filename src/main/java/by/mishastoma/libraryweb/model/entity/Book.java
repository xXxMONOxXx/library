package by.mishastoma.libraryweb.model.entity;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

public class Book extends AbstractEntity {
    private String name;
    private String info = null;
    private LocalDate releaseDate;
    private List<Genre> genres;
    private List<Author> authors;
    private Integer ageLimitation = null;
    private InputStream coverPhoto;
    private int freeQuantity;

    public Book(long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public InputStream getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(InputStream coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public int getQuantity() {
        return freeQuantity;
    }

    public void setQuantity(int freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Integer getAgeLimitation() {
        return ageLimitation;
    }

    public void setAgeLimitation(Integer ageLimitation) {
        this.ageLimitation = ageLimitation;
    }

    public boolean hasAuthorsId(long id) {
        for (Author author : authors) {
            if (author.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean hasGenresId(long id) {
        for (Genre genre : genres) {
            if (genre.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public String getPhotoCoverAsBase64() {
        try {
            if (coverPhoto != null) {
                byte[] bytes = IOUtils.toByteArray(coverPhoto);
                return Base64.encodeBase64String(bytes);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public static class Builder {

        private Book newBook;

        public Builder(long id) {
            newBook = new Book(id);
        }

        public Book.Builder withName(String name) {
            newBook.name = name;
            return this;
        }

        public Book.Builder withInfo(String info) {
            newBook.info = info;
            return this;
        }

        public Book.Builder withReleaseDate(LocalDate releaseDate) {
            newBook.releaseDate = releaseDate;
            return this;
        }

        public Book.Builder withGenres(List<Genre> genres) {
            newBook.genres = genres;
            return this;
        }

        public Book.Builder withAuthors(List<Author> authors) {
            newBook.authors = authors;
            return this;
        }

        public Book.Builder withAgeLimitations(Integer ageLimitations) {
            newBook.ageLimitation = ageLimitations;
            return this;
        }

        public Book.Builder withCoverPhoto(InputStream coverPhoto) {
            newBook.coverPhoto = coverPhoto;
            return this;
        }

        public Book.Builder withQuantity(int freeQuantity) {
            newBook.freeQuantity = freeQuantity;
            return this;
        }

        public Book build() {
            return newBook;
        }
    }
}
