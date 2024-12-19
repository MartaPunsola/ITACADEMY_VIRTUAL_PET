package cat.itacademy.s05.t02.VirtualPet.dto.response;

import cat.itacademy.s05.t02.VirtualPet.model.enums.PetAccessory;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetColor;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    private String username;
    private String name;
    private PetColor color;
    private PetType type;
    private int happiness;
    private int energy_level;
    private int hunger;
    private boolean isAsleep;
    private PetLocation location;
    private Set<PetAccessory> accessories;


}
