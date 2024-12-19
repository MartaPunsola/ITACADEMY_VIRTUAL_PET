package cat.itacademy.s05.t02.VirtualPet.exception.custom;

public class NameAlreadyExistsException extends RuntimeException {

    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
