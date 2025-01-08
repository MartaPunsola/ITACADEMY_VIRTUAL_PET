package cat.itacademy.s05.t02.VirtualPet.dto.request;

import cat.itacademy.s05.t02.VirtualPet.model.enums.PetAccessory;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetInteraction;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import lombok.Data;

import java.util.Set;

@Data
public class UpdatePetRequest {

    //calen els dos primers? o es pot barrejar amb FindPetRequest??
   // private Long userId;
    private String name;
    private PetInteraction petInteraction;
    private PetAccessory accessory;
    private PetLocation location;
}
