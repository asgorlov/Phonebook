package phonebook.repository;

import phonebook.models.PhoneType;
import phonebook.models.Reference;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReferenceRepository extends CrudRepository<Reference, Long> {

    List<Reference> findByPerson_SurnameAndPerson_NameAndPerson_FamilyAndNumber_PhoneNumberAndNumber_PhoneType
            (String surname, String name, String family, String number, PhoneType type);

    List<Reference> findByPerson_SurnameAndPerson_NameAndNumber_PhoneNumber
            (String person_surname, String person_name, String number_phoneNumber);

    List<Reference> findByPerson_SurnameAndPerson_Name(String person_surname, String person_name);

    List<Reference> findByPerson_Surname(String person_surname);

    List<Reference> findByPerson_Name(String person_name);

    List<Reference> findByNumber_PhoneNumber(String number_phoneNumber);

    List<Reference> findByPerson_SurnameAndNumber_PhoneNumber(String person_surname, String number_phoneNumber);

    List<Reference> findByPerson_NameAndNumber_PhoneNumber(String person_name, String number_phoneNumber);
}
