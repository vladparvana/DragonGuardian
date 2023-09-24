package PaooGame.Exceptions;

public class BowNotAvailable extends RuntimeException{
    public BowNotAvailable() {
        super("Arcul nu e inca disponibil.");
    }

}
