package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp.lab.model.enums.Cuisine;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String dishId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;
    private int preparationTime;

    @ManyToMany(mappedBy = "dishes")
    private List<Chef> chefs = new ArrayList<>();

    public Dish(String name, Cuisine cuisine, int preparationTime) {
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }
}
