package phonebook.utilities;

import phonebook.models.PhoneNumber;
import java.util.ArrayList;

public class DefaultNumberList {

    private ArrayList<PhoneNumber> defaultNumberList;

    public DefaultNumberList() {

        defaultNumberList = new ArrayList<>();
        defaultNumberList.add(new PhoneNumber("+7-920-456-12-32"));
        defaultNumberList.add(new PhoneNumber("+7-999-139-39-63"));
        defaultNumberList.add(new PhoneNumber("+7-831-422-15-53"));
        defaultNumberList.add(new PhoneNumber("+7-952-789-45-21"));
        defaultNumberList.add(new PhoneNumber("+7-903-963-85-74"));
        defaultNumberList.add(new PhoneNumber("+7-831-654-21-36"));
        defaultNumberList.add(new PhoneNumber("+7-922-654-89-78"));
        defaultNumberList.add(new PhoneNumber("+7-921-321-65-89"));
        defaultNumberList.add(new PhoneNumber("+7-831-831-31-31"));
        defaultNumberList.add(new PhoneNumber("+7-950-528-65-32"));
    }

    public ArrayList<PhoneNumber> getDefaultNumberList() {

        return defaultNumberList;
    }
}
