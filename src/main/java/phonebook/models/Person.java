package phonebook.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String surname;
    @Column
    private String name;
    @Column
    private String family;
    @OneToMany(mappedBy = "person")
    private List<Reference> references;

    public Person() {
    }

    public Person(String surname, String name, String family) {

        this.surname = surname;
        this.name = name;
        this.family = family;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getSurname() {

        return surname;
    }

    public void setSurname(String surname) {

        this.surname = surname;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getFamily() {

        return family;
    }

    public void setFamily(String family) {

        this.family = family;
    }

    public List<Reference> getReferences() {

        return references;
    }

    public void setReferences(List<Reference> references) {

        this.references = references;
    }

    @Override
    public String toString() {

        return surname + " " + name + " " + family;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(surname, person.surname) &&
                Objects.equals(name, person.name) &&
                Objects.equals(family, person.family);
    }

    @Override
    public int hashCode() {

        return Objects.hash(surname, name, family);
    }
}
