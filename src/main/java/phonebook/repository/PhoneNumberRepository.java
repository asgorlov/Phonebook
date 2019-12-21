package phonebook.repository;

import phonebook.models.PhoneNumber;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {

    List<PhoneNumber> findByPhoneNumber(String number);

    List<PhoneNumber> findByPhoneNumberAndAndPhoneType_Type(String number, String type);
}
