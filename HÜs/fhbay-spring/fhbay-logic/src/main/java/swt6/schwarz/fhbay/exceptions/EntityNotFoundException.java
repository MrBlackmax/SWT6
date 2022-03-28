package swt6.schwarz.fhbay.exceptions;

public class EntityNotFoundException extends RuntimeException{
    private Long entityId;
    private String entityName;

    public EntityNotFoundException(Long entityId, Class entityType) {
        super();
        this.entityId = entityId;
        this.entityName = entityType.getSimpleName();
    }

    @Override
    public String getMessage() {
        return entityName + " with id " + entityId + " could not be found";
    }
}
