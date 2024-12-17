package cat.itacademy.s05.t02.VirtualPet.model;

import cat.itacademy.s05.t02.VirtualPet.model.enums.PetColor;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pets")
public class Pet {

    private Long id;
    private String name;
    private PetColor color;
    private PetType type;
    //happiness, energy_level, hunger, anxiety…
}
