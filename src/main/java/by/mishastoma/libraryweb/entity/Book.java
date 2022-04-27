package by.mishastoma.libraryweb.entity;

import java.util.Date;

public class Book extends AbstractEntity{
    private String name;
    private String info = null;
    private int price;
    private Date releaseDate;
    private String[] genres;
    private Integer ageLimitation = null;

    public Book(long id) {
        super(id); //todo builder, getters and setters
    }

    public String getName() {
        return name;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public Integer getAgeLimitation() {
        return ageLimitation;
    }

    public void setAgeLimitation(Integer ageLimitation) {
        this.ageLimitation = ageLimitation;
    }
}
