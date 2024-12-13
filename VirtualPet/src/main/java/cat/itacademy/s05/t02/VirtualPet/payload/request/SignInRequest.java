package cat.itacademy.s05.t02.VirtualPet.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
