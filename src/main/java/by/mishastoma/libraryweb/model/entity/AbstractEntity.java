package by.mishastoma.libraryweb.model.entity;

public abstract class AbstractEntity {
    protected long id;

    protected AbstractEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
