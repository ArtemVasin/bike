package com.artemvasin.bike.controllers;

import com.artemvasin.bike.model.Bike;
import com.artemvasin.bike.model.Human;
import com.artemvasin.bike.services.BikeService;
import com.artemvasin.bike.services.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/bikes")
public class BikesController {

    private  BikeService bikeService;
    private HumanService humanService;

    @Autowired
    public BikesController(BikeService bikeService, HumanService humanService) {
        this.bikeService = bikeService;
        this.humanService = humanService;
    }

    @GetMapping
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "bikes_pre_page", required = false) Integer bikesPerPage,
                        @RequestParam(value = "sort_by_year_of_creation", required = false) boolean sortByYearOfCreation, Model model) {
        if (page == null || bikesPerPage == null)
            model.addAttribute("bikes", bikeService.findAll(sortByYearOfCreation));
        else model.addAttribute("bikes", bikeService.findWithPagination(page, bikesPerPage, sortByYearOfCreation));
        return "bikes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("human") Human human) {
        model.addAttribute("bike", bikeService.finById(id));
        Human bikeClient = bikeService.getBikeHuman(id);

        if (bikeClient != null)
            model.addAttribute("renter", bikeClient);
        else
            model.addAttribute("people", humanService.findAll());
        return "bikes/show";
    }

    @GetMapping("/new")
    public String newBike(@ModelAttribute("bike") Bike bike) {
        return "bikes/new";
    }

    @PostMapping
    public String create(@ModelAttribute("bike") @Valid Bike bike, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "bikes/new";

        bikeService.saveBike(bike);
        return "redirect: /bikes";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("bike", bikeService.finById(id));
        return "bikes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("bike") @Valid Bike bike, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "bikes/edit";

        bikeService.updateBike(id, bike);
        return "redirect: /bikes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bikeService.deleteBike(id);
        return "redirect: /bikes";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bikeService.release(id);
        return "redirect: /bikes" + id;
    }

    @PatchMapping("/{id}/asign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("human") Human selectHuman) {
        bikeService.assign(id, selectHuman);
        return "redirect: /bikes" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "bikes/search";
    }

    @PostMapping("/search")
    public String makeSearch(@RequestParam("query") String query, Model model) {
        model.addAttribute("bikes", bikeService.searchByTitle(query));
        return "bikes/search";
    }
}
