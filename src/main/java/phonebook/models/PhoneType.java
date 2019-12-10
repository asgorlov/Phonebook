package phonebook.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class PhoneType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String type;
    @OneToMany(mappedBy = "phoneType")
    private List<PhoneNumber> phoneNumbers;

    public PhoneType() {
    }

    public PhoneType(String type) {

        this.type = type;
    }

    public PhoneType(String type, List<PhoneNumber> phoneNumbers) {

        this.type = type;
        this.phoneNumbers = phoneNumbers;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public List<PhoneNumber> getPhoneNumbers() {

        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumber) {

        this.phoneNumbers = phoneNumber;
    }

    @Override
    public String toString() {

        return type;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneType phoneType = (PhoneType) o;
        return Objects.equals(type, phoneType.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }
}
