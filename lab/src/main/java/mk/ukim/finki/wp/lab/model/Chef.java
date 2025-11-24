package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Chef {
    private static long counter = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String bio;
    private List<Dish> dishes;

    public Chef(String firstName, String lastName, String bio, List<Dish> dishes) {
        this.id = counter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dishes = dishes;
    }
}
