package phonebook.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Objects;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne
    @JoinColumn
    private Person person;
    @ManyToOne
    @JoinColumn
    private PhoneNumber number;

    public Reference() {
    }

    public Reference(Person person, PhoneNumber number) {

        this.person = person;
        this.number = number;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Person getPerson() {

        return person;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

    public PhoneNumber getNumber() {

        return number;
    }

    public void setNumber(PhoneNumber number) {

        this.number = number;
    }

    @Override
    public String toString() {

        return person.toString() + " " + number.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return Objects.equals(person, reference.person) &&
                Objects.equals(number, reference.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(person, number);
    }
}
