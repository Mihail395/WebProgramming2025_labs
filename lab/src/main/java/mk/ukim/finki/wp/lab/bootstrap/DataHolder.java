package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.jpa.DishRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ChefRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Chef> chefs = new ArrayList<>();
    public static List<Dish> dishes = new ArrayList<>();

    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public DataHolder(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @PostConstruct
    public void init(){
        if (chefRepository.findAll().isEmpty()) {
            chefs.add(new Chef("Gordon", "Ramsay", "gr", new ArrayList<>()));
            chefs.add(new Chef("Jamie", "Oliver", "gr", new ArrayList<>()));
            chefs.add(new Chef("Thomas", "Keller", "gr", new ArrayList<>()));
            chefs.add(new Chef("David", "Chang", "gr", new ArrayList<>()));
            chefs.add(new Chef("Mark", "Mark", "gr", new ArrayList<>()));
            chefRepository.saveAll(chefs);
        }

        if(dishRepository.findAll().isEmpty()) {
            dishes.add(new Dish("Steak", "American", 7));
            dishes.add(new Dish("Pizza", "Italian", 15));
            dishes.add(new Dish("Pasta Carbonara", "Italian", 10));
            dishes.add(new Dish("Burger", "American", 10));
            dishes.add(new Dish("Chocolate Cake", "American", 20));
            dishRepository.saveAll(dishes);
        }
    }
}
