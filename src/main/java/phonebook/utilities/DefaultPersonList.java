package phonebook.utilities;

import phonebook.models.Person;
import java.util.ArrayList;

public class DefaultPersonList {

    private ArrayList<Person> defaultPersonList;

    public DefaultPersonList() {

        defaultPersonList = new ArrayList<>();
        defaultPersonList.add(new Person("Абрамов","Евгений","Петрович"));
        defaultPersonList.add(new Person("Борзенков","Александр","Васильевич"));
        defaultPersonList.add(new Person("Ваганов","Александр","Олегович"));
        defaultPersonList.add(new Person("Горохов","Вадим","Анатольевич"));
        defaultPersonList.add(new Person("Даниленков","Петр","Афанасьевич"));
        defaultPersonList.add(new Person("Елагин","Сергей","Владимирович"));
        defaultPersonList.add(new Person("Жилов","Максим","Андреевич"));
        defaultPersonList.add(new Person("Зыкин","Станислав","Михайлович"));
        defaultPersonList.add(new Person("Крылов","Борис","Борисович"));
        defaultPersonList.add(new Person("Лапин","Василий","Геннадьевич"));
    }

    public ArrayList<Person> getDefaultPersonList() {

        return defaultPersonList;
    }
}
