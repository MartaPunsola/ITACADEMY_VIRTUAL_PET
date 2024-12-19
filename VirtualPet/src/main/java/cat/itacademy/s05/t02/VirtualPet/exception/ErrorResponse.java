package cat.itacademy.s05.t02.VirtualPet.exception;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private long timestamp;
    private Map<String, String> fieldErrors;

    {
        this.fieldErrors = new HashMap<>();
    }

    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
