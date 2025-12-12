package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.jpa.DishRepository;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
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
    public Dish create(String name, String cuisine, int preparationTime){
        Dish dish = new Dish(name, cuisine, preparationTime);
        return dishRepository.save(dish);
    }

    @Override
    public  Dish update(Long id, String name, String cuisine, int preparationTime){
        Dish existing = findById(id);
        if(existing == null) return null;

        existing.setName(name);
        existing.setCuisine(cuisine);
        existing.setPreparationTime(preparationTime);

        return dishRepository.save(existing);
    }

    @Override
    public void delete(Long id){
        dishRepository.deleteById(id);
    }

}
