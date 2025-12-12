package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.ui.Model;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }

    @GetMapping
    public String getDishesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        model.addAttribute("dishes",dishService.listDishes());
        return "listDishes";
    }

    @GetMapping("/dish-form")
    public String getAddDishPage(Model model) {
        model.addAttribute("dish", null);
        return "dish-form";
    }

    @PostMapping("/add")
    public String saveDish(
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime){

        dishService.create(name,cuisine,preparationTime);

        return "redirect:/dishes";
    }

    @GetMapping("/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);
        if (dish == null) {
            return "redirect:/dishes?error=DishNotFound";
        }
        model.addAttribute("dish", dish);
        return "dish-form";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime) {

        dishService.update(id, name, cuisine, preparationTime);

        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);

        return "redirect:/dishes";
    }

    @GetMapping("/add-to-chef")
    public String showAddDishToChef(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId);
        if (chef == null) return "redirect:/dishes?error=ChefNotFound";
        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishService.listDishes());
        return "dishesList";
    }

    @PostMapping("/add-to-chef")
    public String addDishToChef(@RequestParam Long chefId,
                                @RequestParam Long dishId) {
        chefService.addDishToChef(chefId, dishId);
        return "redirect:/dishes/chefDetails?chefId=" + chefId;
    }

    @GetMapping("/chefDetails")
    public String getChefDetails(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId);
        if (chef == null) return "redirect:/dishes?error=ChefNotFound";
        model.addAttribute("chef", chef);
        model.addAttribute("chefDishes", chef.getDishes());
        return "chefDetails";
    }
}
