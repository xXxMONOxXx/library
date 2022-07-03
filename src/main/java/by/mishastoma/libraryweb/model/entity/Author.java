package by.mishastoma.libraryweb.model.entity;

public class Author extends AbstractEntity {
    private String firstname;
    private String lastname;
    private String biography;

    public Author(long id, String firstname, String lastname, String biography) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.biography = biography;
    }

    public Author(long id) {
        super(id);}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
