package cat.itacademy.s05.t02.VirtualPet.dto.request;

import cat.itacademy.s05.t02.VirtualPet.model.enums.PetColor;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePetRequest {

    //només per provar
    private Long user_id;
    @NotBlank
    private String name;
    private PetType type;
    private PetColor color;
}
