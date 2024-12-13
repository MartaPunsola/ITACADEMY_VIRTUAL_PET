package cat.itacademy.s05.t02.VirtualPet.payload.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
}
