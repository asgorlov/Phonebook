package phonebook.utilities;

import phonebook.models.PhoneNumber;
import phonebook.models.PhoneType;
import java.util.ArrayList;

class DefaultTypeList {

    private static DefaultTypeList instance;
    private ArrayList<PhoneType> defaultTypeList;

    private DefaultTypeList() {

        defaultTypeList = new ArrayList<>();
        ArrayList<PhoneNumber> cellList = new ArrayList<>();
        ArrayList<PhoneNumber> fixList = new ArrayList<>();
        ArrayList<PhoneNumber> homeList = new ArrayList<>();

        defaultTypeList.add( new PhoneType("Сотовый", cellList));
        defaultTypeList.add( new PhoneType("Рабочий", fixList));
        defaultTypeList.add( new PhoneType("Домашний", homeList));
    }

    static DefaultTypeList getInstance(){

        if (instance == null){
            instance = new DefaultTypeList();
        }
        return instance;
    }

    ArrayList<PhoneType> getDefaultTypeList() {

        return defaultTypeList;
    }
}
