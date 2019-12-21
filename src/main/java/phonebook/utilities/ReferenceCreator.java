package phonebook.utilities;

import phonebook.models.Person;
import phonebook.models.PhoneNumber;
import phonebook.models.PhoneType;
import phonebook.models.Reference;
import java.util.ArrayList;
import java.util.List;

class ReferenceCreator {

    Reference create(Person person, PhoneNumber number, PhoneType type){

        //link Reference with Number & Person
        Reference reference = new Reference(person,number);

        //link Type with Number
        List<PhoneNumber> numbersOfType = type.getPhoneNumbers();
        if (numbersOfType == null){
            numbersOfType = new ArrayList<>();
        }
        if (!numbersOfType.contains(number)){
            numbersOfType.add(number);
            type.setPhoneNumbers(numbersOfType);
        }

        //link Number with Type
        number.setPhoneType(type);

        //link Number & Person with Reference
        List<Reference> referencesOfNumber = number.getReferences();
        List<Reference> referencesOfPerson = person.getReferences();
        if (referencesOfNumber == null){
            referencesOfNumber = new ArrayList<>();
        }
        if (referencesOfPerson == null){
            referencesOfPerson = new ArrayList<>();
        }
        if (!referencesOfPerson.contains(reference)){
            referencesOfNumber.add(reference);
            referencesOfPerson.add(reference);
            number.setReferences(referencesOfNumber);
            person.setReferences(referencesOfPerson);
        }

        return reference;
    }

    List<Reference> createDefaultList(List<Person> persons,
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
}
