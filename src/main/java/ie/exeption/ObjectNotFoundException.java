package ie.exeption;

public class ObjectNotFoundException extends CustomException {
    public static final String defaultMessage = "ObjectNotFound";
    public ObjectNotFoundException() {
        super(defaultMessage);
    }
    public ObjectNotFoundException(String message) {
        super(message);
    }
}