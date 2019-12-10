package phonebook.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phonebook.models.Person;
import phonebook.models.PhoneNumber;
import phonebook.models.PhoneType;
import phonebook.models.Reference;
import phonebook.repository.PersonRepository;
import phonebook.repository.PhoneNumberRepository;
import phonebook.repository.PhoneTypeRepository;
import phonebook.repository.ReferenceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GeneralUtility {

    private final ReferenceRepository referenceRepo;
    private final PersonRepository personRepo;
    private final PhoneNumberRepository numberRepo;
    private final PhoneTypeRepository typeRepo;
    private ReferenceCreator refCreator;
    private ArrayList<Reference> references;

    @Autowired
    public GeneralUtility(ReferenceRepository referenceRepo,
                          PersonRepository personRepo,
                          PhoneNumberRepository numberRepo,
                          PhoneTypeRepository typeRepo) {

        this.referenceRepo = referenceRepo;
        this.personRepo = personRepo;
        this.numberRepo = numberRepo;
        this.typeRepo = typeRepo;
        this.refCreator = new ReferenceCreator();

        addDefaultReferenceList();
    }

    public void add(String surname, String name, String family, String number, String type){

        Person person;
        Reference reference;
        PhoneNumber phoneNumber;
        PhoneType phoneType;

        List<Person> persons = personRepo.findBySurnameAndNameAndFamily(surname, name, family);
        List<PhoneNumber> numbers = numberRepo.findByPhoneNumberAndAndPhoneType_Type(number, type);
        List<PhoneType> types = DefaultTypeList.getInstance().getDefaultTypeList();

        if (persons.isEmpty()){
            person = new Person(surname, name, family);
        }
        else {
            person = persons.get(0);
        }
        if (numbers.isEmpty()){
            phoneNumber = new PhoneNumber(number);
        }
        else {
            phoneNumber = numbers.get(0);
        }
        int typeIndex = Integer.parseInt(type);
        phoneType = types.get(typeIndex);

        reference = refCreator.create(person, phoneNumber, phoneType);

        if (references == null){
            references = (ArrayList<Reference>) readAllReference();
        }

        boolean isUnique = true;
        for (Reference element : references){
            if (reference.equals(element)){
                isUnique = false;
                break;
            }
        }

        if (isUnique){
            referenceRepo.save(reference);
        }
    }

    public void search(String surname, String name, String number, Map<String,Object> model){

        List<Reference> referenceList;

        if (surname == null || surname.isEmpty()){
            if (name == null || name.isEmpty()){
                if (number == null || number.isEmpty()){
                    referenceList = new ArrayList<>();
                }
                else {
                    referenceList = referenceRepo.findByPhoneNumReference_PhoneNumber(number);
                }
            }
            else if (number == null || number.isEmpty()){
                referenceList = referenceRepo.findByPersReference_Name(name);
            }
            else {
                referenceList = referenceRepo
                        .findByPersReference_NameAndPhoneNumReference_PhoneNumber(name,number);
            }
        }
        else if (name == null || name.isEmpty()){
            if (number == null || number.isEmpty()){
                referenceList = referenceRepo.findByPersReference_Surname(surname);
            }
            else {
                referenceList = referenceRepo
                        .findByPersReference_SurnameAndPhoneNumReference_PhoneNumber(surname,number);
            }
        }
        else if (number == null || number.isEmpty()){
            referenceList = referenceRepo.findByPersReference_SurnameAndPersReference_Name(surname, name);
        }
        else {
            referenceList = referenceRepo
                    .findByPersReference_SurnameAndPersReference_NameAndPhoneNumReference_PhoneNumber
                            (surname, name, number);
        }

        read(referenceList, model);
    }

    public void update(String surname, String name, String family, String number, String type, Long id){

//        if (referenceRepo.findById(id).isPresent()){
//            Reference reference = referenceRepo.findById(id).get();
//            Person person = reference.getPersReference();
//            PhoneNumber phoneNumber = reference.getPhoneNumReference();
//            List<Person> persons = personRepo.findBySurnameAndNameAndFamily(surname, name, family);
//            List<PhoneNumber> numbers = numberRepo.findByPhoneNumberAndAndPhoneType_Type(number, type);
//
//            if (!persons.isEmpty()){
//                reference.setPersReference(persons.get(0));
//                person.setRefPerson(reference);
//            }
//            if (!numbers.isEmpty()){
//                reference.setPhoneNumReference(numbers.get(0));
//                phoneNumber.setRefPhoneNumber(reference);
//            }
//        }
    }

    public void delete(Long id){

        if (referenceRepo.findById(id).isPresent()){
            Reference reference = referenceRepo.findById(id).get();

            List<PhoneNumber> numbers = reference.getPhoneNumReference().getPhoneType().getPhoneNumbers();
            if (numbers != null && !numbers.isEmpty()){
                numbers.remove(reference.getPhoneNumReference());
            }
            referenceRepo.deleteById(id);
        }
    }

    public void read(Iterable<Reference> referenceList, Map<String,Object> model){

        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<PhoneNumber> numbers = new ArrayList<>();
        ArrayList<PhoneType> types = new ArrayList<>();


        for (Reference element : referenceList){
            persons.add(element.getPersReference());
            numbers.add(element.getPhoneNumReference());
        }
        for (PhoneNumber element : numbers) {
            types.add(element.getPhoneType());
        }

        model.put("references", referenceList);
        model.put("numbers", numbers);
        model.put("persons", persons);
        model.put("types", types);
    }

    public void readAll(Map<String,Object> model){

        references = (ArrayList<Reference>) readAllReference();

        read(references, model);
    }

    private Iterable<Reference> readAllReference(){

        return referenceRepo.findAll();
    }

    private void addDefaultReferenceList(){

        DefaultPersonList defaultPersons = new DefaultPersonList();
        DefaultNumberList defaultNumbers = new DefaultNumberList();
        DefaultTypeList defaultTypes = DefaultTypeList.getInstance();

        List<Reference> referenceList = refCreator.createDefaultRefList(defaultPersons.getDefaultPersonList(),
                                                              defaultNumbers.getDefaultNumberList(),
                                                              defaultTypes.getDefaultTypeList());
        typeRepo.saveAll(defaultTypes.getDefaultTypeList());
        referenceRepo.saveAll(referenceList);
    }
}