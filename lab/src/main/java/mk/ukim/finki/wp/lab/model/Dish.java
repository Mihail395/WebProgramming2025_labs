package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Dish {
    private static long counter = 1L;

    private Long id;
    private String dishId;
    private String name;
    private String cuisine;
    private int preparationTime;

    public Dish(String dishId, String name, String cuisine, int preparationTime) {
        this.id = counter++;
        this.dishId = dishId;
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }
}
