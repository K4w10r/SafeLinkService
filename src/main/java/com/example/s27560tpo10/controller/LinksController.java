package com.example.s27560tpo10.controller;

import com.example.s27560tpo10.model.LinkDTO;
import com.example.s27560tpo10.model.NewLinkDTO;
import com.example.s27560tpo10.services.LinksService;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

@Controller
public class LinksController {
    private final LinksService linksService;

    public LinksController(LinksService linksService){this.linksService = linksService;}

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("link", new NewLinkDTO());
        model.addAttribute("search", "");
        model.addAttribute("language", "");
        return "index";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("link") NewLinkDTO newLinkDTO, BindingResult bindingResult){
        if(newLinkDTO.getPassword().isEmpty()){newLinkDTO.setPassword(null);}
        if(bindingResult.hasErrors())return "index";
        String id = linksService.saveLink(newLinkDTO).getId();
        //ligika do dodawania
        return "redirect:/link/" + id;
    }

    @GetMapping("/link/{id}")
    public String success(@PathVariable String id, Model model){
        linksService.getRedirectionById(id);
        linksService.getLinkById(id).ifPresent(link -> model.addAttribute("link", link));
        model.addAttribute("edit", new NewLinkDTO());
        return "link";

    }

    @GetMapping("/link")
    public String findLink(@RequestParam("search") String id){
        return "redirect:/link/" + id;
    }

    @PatchMapping("/modify")
    public String modify(@RequestParam String id, @Valid @ModelAttribute("edit") NewLinkDTO newLinkDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "link/" + id;

        //logika do modyfikowania
        LinkDTO linkDTO = linksService.getLinkById(id).orElseThrow();
        if(!Objects.equals(newLinkDTO.getPassword(), linkDTO.getPassword())){
            return "incorrectPassword.html";
        }
        if(!newLinkDTO.getName().equals("DefaultName"))linkDTO.setName(newLinkDTO.getName());
        if(!newLinkDTO.getName().equals("https://d/"))linkDTO.setName(newLinkDTO.getName());
        linksService.updateLink(linkDTO);
        return "redirect:/link/" + id;
    }
    @DeleteMapping("/delete")
    public String delete(@RequestParam String id){
        linksService.deleteLink(id);
        return "redirect:/link/" + id;
    }

}
