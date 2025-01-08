package cat.itacademy.s05.t02.VirtualPet.dto.response;

import cat.itacademy.s05.t02.VirtualPet.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String email;
    private Role role;


}
