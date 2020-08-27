package ch.zotteljedi.onlineshop.firststep.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name = "Hello.get", query = "select h from HelloEntity h")
public class HelloEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    private String name;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HelloEntity)) return false;
        HelloEntity that = (HelloEntity) o;
        return id == that.id &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "HelloEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
