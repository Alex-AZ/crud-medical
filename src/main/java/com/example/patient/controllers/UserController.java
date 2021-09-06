package com.example.patient.controllers;

import com.example.patient.fr.entities.UserEntity;
import com.example.patient.fr.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository ur;

    public UserController(UserRepository ur) {
        this.ur = ur;
    }

    // Liste des utilisateurs
    @GetMapping("/list")
    public String listUser(Model model) {
        model.addAttribute("lu", ur.findAll());
        return "user/list";
    }

    // Ajout d'un utilisateur (create)
    @GetMapping("/add")
    public String addUserGet( Model model ) {
        model.addAttribute("action", "ajout");

        model.addAttribute("u", new UserEntity());

        return "user/add_edit";
    }

    @PostMapping("/add")
    public String addUserPost( HttpServletRequest request ) {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String photo = request.getParameter("photo");

        try{
            UserEntity u = new UserEntity();
            u.setName(nom);
            u.setEmail(email);
            u.setPassword(password);
            u.setPhotouser(photo);

            ur.save(u);

        }catch( Exception e ) {

        }
        return "redirect:/user/list";
    }

    // Edition d'un utilisateur (update)
    @GetMapping("/edit/{id}")
    public String editUserGet(@PathVariable String id, Model model) {
        /*List<VilleEntity> lv = (List<VilleEntity>) vr.findAll();
        model.addAttribute( "lv" , lv );*/
        model.addAttribute( "action" , "mise à jour" );

        Optional<UserEntity> u;
        u = ur.findById(Integer.parseInt(id));

        model.addAttribute("u", u.get());

        return "user/add_edit";
    }

    @PostMapping("/edit/{id}")
    public String editUserPost(@PathVariable String id, HttpServletRequest request) {
        // List<VilleEntity> lv = (List<VilleEntity>) vr.findAll();

        UserEntity u;

        u = ur.findById(Integer.parseInt(id)).get();

        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String photo = request.getParameter("photo");

        try{
            u.setName(nom);
            u.setEmail(email);
            u.setPassword(password);
            u.setPhotouser(photo);

            ur.save(u);

        }catch( Exception e ) {

        }
        return "redirect:/user/list";
    }

    // Suppression d'un utilisateur (delete)
    @GetMapping("/delete/{id}")
    public String deleteGet(@PathVariable int id) {
        try {
            UserEntity u = ur.findById(id).orElse(null);
            ur.delete(u);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans la suppression du patient" + id);
        }
        return "redirect:/user/list";
    }
}
