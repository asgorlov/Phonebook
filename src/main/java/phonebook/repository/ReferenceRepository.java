package phonebook.repository;

import phonebook.models.Person;
import phonebook.models.PhoneNumber;
import phonebook.models.Reference;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReferenceRepository extends CrudRepository<Reference, Long> {

    List<Reference> findByPersReferenceAndPhoneNumReference(Person person, PhoneNumber number);

    List<Reference> findByPersReference_SurnameAndPersReference_NameAndPhoneNumReference_PhoneNumber
            (String persReference_surname, String persReference_name, String phoneNumReference_phoneNumber);

    List<Reference> findByPersReference_SurnameAndPersReference_Name
            (String persReference_surname, String persReference_name);

    List<Reference> findByPersReference_SurnameAndPhoneNumReference_PhoneNumber
            (String persReference_surname, String phoneNumReference_phoneNumber);

    List<Reference> findByPersReference_NameAndPhoneNumReference_PhoneNumber
            (String persReference_name, String phoneNumReference_phoneNumber);

    List<Reference> findByPersReference_Surname(String persReference_surname);

    List<Reference> findByPersReference_Name(String persReference_name);

    List<Reference> findByPhoneNumReference_PhoneNumber(String phoneNumReference_phoneNumber);
}
