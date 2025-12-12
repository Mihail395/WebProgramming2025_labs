package mk.ukim.finki.wp.lab.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.enums.Cuisine;
import mk.ukim.finki.wp.lab.repository.jpa.ChefRepository;
import mk.ukim.finki.wp.lab.repository.jpa.DishRepository;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final ChefRepository chefRepository;

    public DishServiceImpl(DishRepository dishRepository, ChefRepository chefRepository) {
        this.dishRepository = dishRepository;
        this.chefRepository = chefRepository;
    }

    @Override
    public List<Dish> listDishes(){
        return dishRepository.findAll();
    }

//    @Override
//    public Dish findByDishId(String dishId){
//        return dishRepository.findById(dishId);
//    }

    @Override
    public Dish findById(Long id){
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(String name, Cuisine cuisine, int preparationTime){
        Dish dish = new Dish(name, cuisine, preparationTime);
        return dishRepository.save(dish);
    }

    @Override
    public  Dish update(Long id, String name, Cuisine cuisine, int preparationTime){
        Dish existing = findById(id);
        if(existing == null) return null;

        existing.setName(name);
        existing.setCuisine(cuisine);
        existing.setPreparationTime(preparationTime);

        return dishRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElse(null);

        if (dish == null) return;

        chefRepository.findAll().forEach(chef -> {
            chef.getDishes().remove(dish);
            chefRepository.save(chef);
        });

        dishRepository.delete(dish);
    }
}
