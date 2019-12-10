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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Person persReference;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PhoneNumber phoneNumReference;

    public Reference() {
    }

    public Reference(Person persReference, PhoneNumber phoneNumReference) {

        this.persReference = persReference;
        this.phoneNumReference = phoneNumReference;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Person getPersReference() {

        return persReference;
    }

    public void setPersReference(Person persReference) {

        this.persReference = persReference;
    }

    public PhoneNumber getPhoneNumReference() {

        return phoneNumReference;
    }

    public void setPhoneNumReference(PhoneNumber phoneNumReference) {

        this.phoneNumReference = phoneNumReference;
    }

    @Override
    public String toString() {

        return persReference.toString() + " " + phoneNumReference.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return Objects.equals(persReference, reference.persReference) &&
                Objects.equals(phoneNumReference.toString(),
                        reference.phoneNumReference.toString());
    }

    @Override
    public int hashCode() {

        return Objects.hash(persReference, phoneNumReference);
    }
}
