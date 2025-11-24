package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Chef> chefs = new ArrayList<>();
    public static List<Dish> dishes = new ArrayList<>();

    @PostConstruct
    public void init(){
        chefs.add(new Chef("Gordon","Ramsay","gr",new ArrayList<>()));
        chefs.add(new Chef("Jamie","Oliver","gr",new ArrayList<>()));
        chefs.add(new Chef("Thomas","Keller","gr",new ArrayList<>()));
        chefs.add(new Chef("David","Chang","gr",new ArrayList<>()));
        chefs.add(new Chef("Mark","Mark","gr",new ArrayList<>()));

        dishes.add(new Dish("steak","Steak","American",7));
        dishes.add(new Dish("pizza","Pizza","Italian",15));
        dishes.add(new Dish("pasta","Pasta Carbonara","Italian",10));
        dishes.add(new Dish("burger","Burger","American",10));
        dishes.add(new Dish("dessert","Chocolate Cake","American",20));

    }
}
