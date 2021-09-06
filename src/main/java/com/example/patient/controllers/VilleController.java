package com.example.patient.controllers;

import com.example.patient.fr.entities.PatientEntity;
import com.example.patient.fr.entities.VilleEntity;
import com.example.patient.fr.repositories.VilleRepository;
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
@RequestMapping("/ville")
public class VilleController {

    private final VilleRepository vr;

    public VilleController(VilleRepository vr) {
        this.vr = vr;
    }

    // Liste des Villes
    @GetMapping("/list")
    public String listVille(Model model) {
        // List<VilleEntity> lv = (List<VilleEntity>) vr.findAll();
        model.addAttribute("lv", vr.findAll());
        return "ville/list";
    }

    // Ajout d'une ville (create)
    @GetMapping("/add")
    public String addVilleGet( Model model ) {
        model.addAttribute("action", "ajout");

        return "ville/add_edit";
    }

    @PostMapping("/add")
    public String addVillePost( HttpServletRequest request ) {
        try{
            VilleEntity v = new VilleEntity();
            v.setNom(request.getParameter("nom"));
            v.setCodePostal(Integer.parseInt(request.getParameter("cp")));

            vr.save(v);

        }catch( Exception e ) {
            System.out.println(e);
        }
        return "redirect:/ville/list";
    }

    // Edition d'une ville (update)
    @GetMapping("/edit/{id}")
    public String editVilleGet(@PathVariable String id, Model model) {
        model.addAttribute( "action" , "mise à jour" );

        Optional<VilleEntity> v;
        v = vr.findById(Integer.parseInt(id));

        model.addAttribute("v", v.get());

        return "ville/add_edit";
    }

    @PostMapping("/edit/{id}")
    public String editVillePost(@PathVariable String id, HttpServletRequest request) {
        VilleEntity v;

        v = vr.findById(Integer.parseInt(id)).get();

        String nom = request.getParameter("nom");
        String codePostal = request.getParameter("cp");


        try{
            v.setNom(nom);
            v.setCodePostal(Integer.parseInt(codePostal));

            vr.save(v);

        }catch( Exception e ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition de la ville "+id);
        }

        return "redirect:/ville/list";
    }

    // Suppression d'une ville (delete)
    @GetMapping("/delete/{id}")
    public String deleteVilleGet(@PathVariable int id) {
        try {
            VilleEntity v = vr.findById(id).orElse(null);
            vr.delete(v);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans la suppression de la ville" + id);
        }
        return "redirect:/ville/list";
    }
}
