package phonebook.repository;

import phonebook.models.Person;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findBySurnameAndNameAndFamily(String surname, String name, String family);
}
