package phonebook.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String phoneNumber;
    @OneToMany(mappedBy = "number")
    private List<Reference> references;
    @ManyToOne
    @JoinColumn
    private PhoneType phoneType;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public List<Reference> getReferences() {

        return references;
    }

    public void setReferences(List<Reference> references) {

        this.references = references;
    }

    public PhoneType getPhoneType() {

        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {

        this.phoneType = phoneType;
    }

    @Override
    public String toString() {

        return phoneNumber + " " + phoneType.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return  Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(phoneNumber);
    }
}
