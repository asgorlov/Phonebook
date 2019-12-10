package phonebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phonebook.utilities.GeneralUtility;
import java.util.Map;

@Controller
public class RequestController {

    private GeneralUtility utility;

    @Autowired
    public RequestController(GeneralUtility utility) {

        this.utility = utility;
    }

    @GetMapping("/")
    public String readAll(Map<String,Object> model) {

        utility.readAll(model);
        return "main";
    }

    @PostMapping("add")
    public String add(
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam String family,
            @RequestParam String number,
            @RequestParam String type,
            Map<String,Object> model) {

        utility.add(surname, name, family, number, type);
        utility.readAll(model);
        return "main";
    }

    @PostMapping("search")
    public String search(
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam String number,
            Map<String,Object> model) {

        utility.search(surname, name, number, model);
        return "main";
    }

    @PostMapping("update")
    public String update(
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam String family,
            @RequestParam String number,
            @RequestParam String type,
            @RequestParam Long id,
            Map<String,Object> model) {

        utility.update(surname, name, family, number, type, id);
        utility.readAll(model);
        return "main";
    }

    @PostMapping("delete")
    public String delete(
            @RequestParam Long id,
            Map<String,Object> model) {

        utility.delete(id);
        utility.readAll(model);
        return "main";
    }
}
