package by.mishastoma.libraryweb.entity;

public class AbstractEntity {
    private long id;
    public AbstractEntity(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
