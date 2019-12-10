package phonebook.utilities;

import phonebook.models.Person;
import phonebook.models.PhoneNumber;
import phonebook.models.PhoneType;
import phonebook.models.Reference;

import java.util.ArrayList;
import java.util.List;

public class ReferenceCreator {

    public Reference create(Person person, PhoneNumber number, PhoneType type){

        Reference reference = new Reference();

        if (type == null) {
            DefaultTypeList defaultTypeList = DefaultTypeList.getInstance();
            type = defaultTypeList.getDefaultTypeList().get(1);
        }

        reference.setPersReference(person);
        person.setRefPerson(reference);
        reference.setPhoneNumReference(number);
        number.setRefPhoneNumber(reference);
        number.setPhoneType(type);

        if (isUniqueNumber(number, type)){
            type.getPhoneNumbers().add(number);
        }

        return reference;
    }

    public List<Reference> createDefaultRefList(List<Person> persons,
                                                List<PhoneNumber> numbers,
                                                List<PhoneType> types){

        List<Reference> references = new ArrayList<>();

        for (int i = 0; i < persons.size(); i++){
            Person personIter = persons.get(i);
            PhoneNumber numberIter = numbers.get(i);
            PhoneType typeIter = types.get((int)(Math.random() * 3));
            Reference referenceIter = create(personIter, numberIter, typeIter);

            references.add(referenceIter);
        }
        return references;
    }

    private boolean isUniqueNumber(PhoneNumber number, PhoneType type){

        for (PhoneNumber element : type.getPhoneNumbers()){
            if (element.getPhoneNumber().equals(number.getPhoneNumber())){
                return false;
            }
        }
        return true;
    }
}
