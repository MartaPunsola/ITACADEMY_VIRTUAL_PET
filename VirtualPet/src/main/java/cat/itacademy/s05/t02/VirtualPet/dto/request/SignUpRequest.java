package cat.itacademy.s05.t02.VirtualPet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    private String username;

    @NotBlank(message = "Email is required.")
    @Size(max = 50) //cal?? treure de user tb
    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 120, message = "Password must be at least 8 characters long.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Password must contain at least one letter and one number.")
    private String password;

}
