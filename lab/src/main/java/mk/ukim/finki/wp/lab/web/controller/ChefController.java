package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chefs")
public class ChefController {
    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping
    public String getChefsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        model.addAttribute("chefs",chefService.listChefs());
        return "listChefs";
    }

    @GetMapping("/chef-form")
    public String getAddChefPage(Model model) {
        model.addAttribute("chef", null);
        return "chef-form";
    }

    @PostMapping("/add")
    public String saveChef(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String bio) {

        chefService.create(firstName, lastName, bio);
        return "redirect:/chefs";
    }

    @GetMapping("/chef-form/{id}")
    public String getEditChefForm(@PathVariable Long id, Model model) {

        Chef chef = chefService.findById(id);
        if (chef == null) {
            return "redirect:/chefs?error=ChefNotFound";
        }

        model.addAttribute("chef", chef);
        return "chef-form";
    }

    @PostMapping("/edit/{id}")
    public String editChef(@PathVariable Long id,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String bio) {

        chefService.update(id, firstName, lastName, bio);
        return "redirect:/chefs";
    }

    @PostMapping("/delete/{id}")
    public String deleteChef(@PathVariable Long id) {

        chefService.delete(id);
        return "redirect:/chefs";
    }

    @GetMapping("/select-chef")
    public String selectChefForDish(@RequestParam Long chefId) {
        return "redirect:/dishes/add-to-chef?chefId=" + chefId;
    }

    @GetMapping("/view-details")
    public String viewChefDetails(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId);
        if (chef == null) {
            return "redirect:/chefs?error=ChefNotFound";
        }
        model.addAttribute("chef", chef);
        model.addAttribute("chefDishes", chef.getDishes());
        return "chefDetails";
    }

}
