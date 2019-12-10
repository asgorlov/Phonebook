package phonebook.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Reference refPhoneNumber;
    @ManyToOne
    @JoinColumn
    private PhoneType phoneType;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber(String phoneNumber, PhoneType phoneType) {

        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
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

    public Reference getRefPhoneNumber() {

        return refPhoneNumber;
    }

    public void setRefPhoneNumber(Reference refPhoneNumber) {

        this.refPhoneNumber = refPhoneNumber;
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
        return Objects.equals(id, that.id) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(refPhoneNumber, that.refPhoneNumber) &&
                Objects.equals(phoneType, that.phoneType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, phoneNumber, refPhoneNumber, phoneType);
    }
}
