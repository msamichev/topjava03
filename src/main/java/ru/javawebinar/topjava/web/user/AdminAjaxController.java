package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * User: javawebinar.topjava
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestParam("item_id") int id,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password) {

        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (id == 0) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }

    @RequestMapping(value = "/{id}/{active}", method = RequestMethod.PUT)
    public void changeStatus(@PathVariable("id") int id, @PathVariable("active") boolean active) {
        User user = super.get(id);
        user.setEnabled(active);
        super.update(user, id);
    }

}
