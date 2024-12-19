package cat.itacademy.s05.t02.VirtualPet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @NotBlank(message = "Email is required.")
    private String email;
    @NotBlank(message = "Password is required.")
    private String password;
}
