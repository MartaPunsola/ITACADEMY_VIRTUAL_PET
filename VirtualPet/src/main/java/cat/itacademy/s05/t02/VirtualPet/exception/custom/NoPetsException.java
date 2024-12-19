package cat.itacademy.s05.t02.VirtualPet.exception.custom;

public class NoPetsException extends RuntimeException {

    public NoPetsException(String message) {
        super(message);
    }
}
