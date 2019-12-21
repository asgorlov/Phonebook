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

        //createType
        phoneType = getPhoneType(type);

        //check unique Reference
        List<Reference> references = referenceRepo
                .findByPerson_SurnameAndPerson_NameAndPerson_FamilyAndNumber_PhoneNumberAndNumber_PhoneType
                        (surname,name,family,number,phoneType);
        if (references.isEmpty()){

            //createNumber
            List<PhoneNumber> numbers = numberRepo.findByPhoneNumberAndAndPhoneType_Type(number, phoneType.getType());
            if (numbers.isEmpty()){
                phoneNumber = new PhoneNumber(number);
            }
            else {
                phoneNumber = numbers.get(0);
            }

            //create Person
            List<Person> persons = personRepo.findBySurnameAndNameAndFamily(surname, name, family);
            if (persons.isEmpty()){
                person = new Person(surname, name, family);
            }
            else {
                person = persons.get(0);
            }

            //create Reference
            reference = refCreator.create(person, phoneNumber, phoneType);

            //add data into DB
            numberRepo.save(phoneNumber);
            personRepo.save(person);
            referenceRepo.save(reference);
        }
    }

    public void search(String surname, String name, String number, Map<String,Object> model){

        List<Reference> referenceList;

        //get event code
        StringBuilder eventCode = new StringBuilder();
        getEventCode(eventCode,surname);
        getEventCode(eventCode,name);
        getEventCode(eventCode,number);

        //find case for event code
        switch (eventCode.toString()){
            case ("000") :
                referenceList = new ArrayList<>();
                break;
            case ("111") :
                referenceList = referenceRepo
                                .findByPerson_SurnameAndPerson_NameAndNumber_PhoneNumber(surname, name, number);
                break;
            case ("110") :
                referenceList = referenceRepo.findByPerson_SurnameAndPerson_Name(surname,name);
                break;
            case ("100") :
                referenceList = referenceRepo.findByPerson_Surname(surname);
                break;
            case ("010") :
                referenceList = referenceRepo.findByPerson_Name(name);
                break;
            case ("001") :
                referenceList = referenceRepo.findByNumber_PhoneNumber(number);
                break;
            case ("101") :
                referenceList = referenceRepo.findByPerson_SurnameAndNumber_PhoneNumber(surname, number);
                break;
            case ("011") :
                referenceList = referenceRepo.findByPerson_NameAndNumber_PhoneNumber(name, number);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + eventCode.toString());
        }

        read(referenceList, model);
    }

    public void update(String surname, String name, String family, String number, String type, Long id){

        if (referenceRepo.findById(id).isPresent()){
            Reference reference = referenceRepo.findById(id).get();
            Person refPerson = reference.getPerson();
            PhoneNumber refNumber = reference.getNumber();
            PhoneType refType = refNumber.getPhoneType();

            //check unique new person
            List<Person> personList = personRepo.findBySurnameAndNameAndFamily(surname, name, family);
            if (personList.isEmpty()){
                refPerson.setSurname(surname);
                refPerson.setName(name);
                refPerson.setFamily(family);
            }
            //merge references new person & old person
            else if (!personList.get(0).getId().equals(refPerson.getId())){
                for (Reference element : personList.get(0).getReferences()){
                    if (!refPerson.getReferences().contains(element)){
                        refPerson.getReferences().add(element);
                        element.setPerson(refPerson);
                    }
                }
                personList.get(0).setReferences(null);
                personRepo.deleteById(personList.get(0).getId());
            }

            //check unique new phone
            List<PhoneNumber> numberList = numberRepo.findByPhoneNumberAndAndPhoneType_Type(number, type);
            refNumber.setPhoneNumber(number);
        }
    }

    public void delete(Long id){

        if (referenceRepo.findById(id).isPresent()){
            //find Reference
            Reference reference = referenceRepo.findById(id).get();
            Person person = reference.getPerson();
            PhoneNumber number = reference.getNumber();

            referenceRepo.delete(reference);

            number.getReferences().remove(reference);
            if (number.getReferences().isEmpty()){
                number.getPhoneType().getPhoneNumbers().remove(number);
                numberRepo.delete(number);
            }

            person.getReferences().remove(reference);
            if (person.getReferences().isEmpty()){
                personRepo.delete(person);
            }
        }
    }

    public void readAll(Map<String,Object> model){

        List<Reference> refList = (List<Reference>) referenceRepo.findAll();;

        read(refList, model);
    }

    private void read(Iterable<Reference> referenceList, Map<String,Object> model){

        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<PhoneNumber> numbers = new ArrayList<>();
        ArrayList<PhoneType> types = new ArrayList<>();


        for (Reference element : referenceList){
            persons.add(element.getPerson());
            numbers.add(element.getNumber());
        }
        for (PhoneNumber element : numbers) {
            types.add(element.getPhoneType());
        }

        model.put("references", referenceList);
        model.put("numbers", numbers);
        model.put("persons", persons);
        model.put("types", types);
    }

    private void addDefaultReferenceList(){

        DefaultPersonList defaultPersons = new DefaultPersonList();
        DefaultNumberList defaultNumbers = new DefaultNumberList();
        DefaultTypeList defaultTypes = DefaultTypeList.getInstance();
        List<Reference> referenceList = refCreator.createDefaultList(defaultPersons.getDefaultPersonList(),
                                                                     defaultNumbers.getDefaultNumberList(),
                                                                     defaultTypes.getDefaultTypeList());
        typeRepo.saveAll(defaultTypes.getDefaultTypeList());
        personRepo.saveAll(defaultPersons.getDefaultPersonList());
        referenceRepo.saveAll(referenceList);
    }

    private PhoneType getPhoneType(String type){

        List<PhoneType> typeList = DefaultTypeList.getInstance().getDefaultTypeList();
        PhoneType phoneType;

        int typeIndex = 0;

        try {
            typeIndex = Integer.parseInt(type);
        }catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }finally {
            phoneType = typeList.get(typeIndex);
        }

        return phoneType;
    }

    private void getEventCode(StringBuilder eventCode, String attribute){

        if (attribute == null || attribute.isEmpty()){
            eventCode.append(0);
        }else {
            eventCode.append(1);
        }
    }
}
