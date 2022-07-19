package com.artemvasin.bike.controllers;

import com.artemvasin.bike.model.Human;
import com.artemvasin.bike.services.HumanService;
import com.artemvasin.bike.util.HumanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class HumanController {

    private HumanService humanService;
    private HumanValidator humanValidator;

    @Autowired
    public HumanController(HumanService humanService, HumanValidator humanValidator) {
        this.humanService = humanService;
        this.humanValidator = humanValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", humanService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("human", humanService.findById(id));
        model.addAttribute("bikes", humanService.getBikeByHumanId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newHuman(@ModelAttribute("human") Human human) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("human") @Valid Human human, BindingResult bindingResult) {
        humanValidator.validate(human, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        humanService.saveHuman(human);
        return "redirect: /people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("human", humanService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("human") @Valid Human upHuman, BindingResult bindingResult, @PathVariable("id") int id) {
        humanValidator.validate(upHuman, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        humanService.updateHuman(id, upHuman);
        return "redirect: /people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        humanService.deleteHuman(id);
        return "redirect: /people";
    }
}
