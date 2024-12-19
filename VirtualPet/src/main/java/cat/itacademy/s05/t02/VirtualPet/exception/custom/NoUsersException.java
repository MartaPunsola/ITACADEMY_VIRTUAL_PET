package cat.itacademy.s05.t02.VirtualPet.exception.custom;

public class NoUsersException extends RuntimeException{

    public NoUsersException(String message) {
        super(message);
    }
}
