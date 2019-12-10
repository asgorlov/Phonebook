package phonebook.repository;

import phonebook.models.PhoneType;
import org.springframework.data.repository.CrudRepository;

public interface PhoneTypeRepository extends CrudRepository<PhoneType, Long> {
}
