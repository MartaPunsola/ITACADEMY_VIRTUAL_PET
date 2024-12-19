package cat.itacademy.s05.t02.VirtualPet.dto.request;

import lombok.Data;

@Data
public class FindPetRequest {

    private Long user_id;
    private String name;
}
