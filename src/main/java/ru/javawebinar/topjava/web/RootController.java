package ru.javawebinar.topjava.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.DateTimeFilter;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.validation.Valid;

/**
 * GKislin
 * 09.08.2015.
 */

@Controller
public class RootController extends AbstractUserController  {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    public String userList() {
        return "userList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
        model.addAttribute("filter", new DateTimeFilter());
        return "mealList";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {

        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            return "profile";
        } else {
            status.setComplete();
            service.update(LoggedUser.get().update(userTo));
            return "redirect:meals";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                super.create(UserUtil.createFromTo(userTo));
                status.setComplete();
                return "redirect:login?message=app.registered";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "error.user", "User with this email already present in application.");
            }
        }
        status.setComplete();
        model.addAttribute("register", true);
        return "profile";
    }
}
