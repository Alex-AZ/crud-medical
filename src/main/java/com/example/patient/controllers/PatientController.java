package com.example.patient.controllers;

import com.example.patient.fr.entities.PatientEntity;
import com.example.patient.fr.entities.VilleEntity;
import com.example.patient.fr.repositories.PatientRepository;
import com.example.patient.fr.repositories.VilleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository pr;
    private final VilleRepository vr;

    public PatientController(PatientRepository pr, VilleRepository vr) {
        this.pr = pr;
        this.vr = vr;
    }

    // Liste des Patients
    @GetMapping("/list")
    public String listPatient(Model model) {
        //List<PatientEntity> lp = (List<PatientEntity>) pr.findAll();
        model.addAttribute("lp", pr.findAll());
        return "patient/list";
    }

    // Ajout d'un patient (create)
    @GetMapping("/add")
    public String addGet( Model model ) {
        model.addAttribute("action", "ajout");
        List<VilleEntity> lv = (List<VilleEntity>) vr.findAll();
        model.addAttribute( "lv" , lv );

        model.addAttribute("p", new PatientEntity());

        return "patient/add_edit";
    }

    @PostMapping("/add")
    public String addPost( HttpServletRequest request ) {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("mail");
        String ville = request.getParameter("ville");

        try{
            PatientEntity p = new PatientEntity();
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setEmail(email);
            p.setTelephone(telephone);

            VilleEntity villeP = new VilleEntity();
            villeP.setId( Integer.parseInt(  ville ) );
            p.setVille( villeP );
            //p.setVille( Integer.parseInt( ville ) );

            pr.save( p );

        }catch( Exception e ) {

        }
        return "redirect:/patient/list";
    }

    // Edition d'un patient (update)
    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable String id, Model model) {
        List<VilleEntity> lv = (List<VilleEntity>) vr.findAll();
        model.addAttribute( "lv" , lv );
        model.addAttribute( "action" , "mise à jour" );

        Optional<PatientEntity> p;
        p = pr.findById(Integer.parseInt(id));

        model.addAttribute("p", p.get());

        return "patient/add_edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request) {
        List<VilleEntity> lv = (List<VilleEntity>) vr.findAll();

        PatientEntity p;

        p = pr.findById(Integer.parseInt(id)).get();

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("mail");
        String ville = request.getParameter("ville");

        try{
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setEmail(email);
            p.setTelephone(telephone);

            VilleEntity villeP = new VilleEntity();
            villeP.setId( Integer.parseInt(  ville ) );
            p.setVille( villeP );
            //p.setVille( Integer.parseInt( ville ) );

            pr.save( p );

        }catch( Exception e ) {

        }

        return "redirect:/patient/list";
    }

    // Suppression d'un patient (delete)
    @GetMapping("/delete/{id}")
    public String deleteGet(@PathVariable int id) {
        try {
            PatientEntity p = pr.findById(id).orElse(null);
            pr.delete(p);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans la suppression du patient" + id);
        }
        return "redirect:/patient/list";
    }
}