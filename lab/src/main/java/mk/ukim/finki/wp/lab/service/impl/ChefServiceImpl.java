package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.jpa.ChefRepository;
import mk.ukim.finki.wp.lab.repository.jpa.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {

    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public ChefServiceImpl(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Chef> listChefs(){
        return chefRepository.findAll();
    }

    @Override
    public Chef findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("id is null or empty");
        }
        return chefRepository.findById(id).orElse(null);
    }

    @Override
    public Chef addDishToChef(Long chefId, Long dishId){
        if(chefId == null || chefId <= 0 || dishId == null || dishId <= 0) {
            throw new IllegalArgumentException("chefId and dishId is null or empty");
        }
        Chef chef = chefRepository.findById(chefId).orElse(null);
        if(chef == null) {
            throw new IllegalArgumentException("chefId not found");
        }
        Dish dish = dishRepository.findById(dishId).orElse(null);
        if(dish == null) {
            throw new IllegalArgumentException("dish not found");
        }
        dish.setChef(chef);
        chef.getDishes().add(dish);

        dishRepository.save(dish);
        return chefRepository.save(chef);
    }

    @Override
    public Chef create (String firstName, String lastName, String bio){
        if (firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || bio == null || bio.isEmpty()) {
            throw new IllegalArgumentException("invalid chef data");
        }

        Chef chef = new Chef(firstName,lastName,bio,new ArrayList<>());

        return chefRepository.save(chef);
    }

    @Override
    public Chef update(Long id, String firstName, String lastName, String bio){
        Chef chef = chefRepository.findById(id).orElse(null);
        if (chef == null) {
            throw new IllegalArgumentException("chef not found");
        }

        chef.setFirstName(firstName);
        chef.setLastName(lastName);
        chef.setBio(bio);

        return chefRepository.save(chef);
    }

    @Override
    public void delete(Long id){
        chefRepository.deleteById(id);
    }
}
