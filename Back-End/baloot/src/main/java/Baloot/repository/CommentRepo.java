package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Comment;

public class CommentRepo extends Repo<Comment> {
    private static CommentRepo instance = null;

    public static CommentRepo getInstance(){
        if(instance == null){
            instance = new CommentRepo();
        }
        return instance;
    }
    @Override
    public void addElement(Comment newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());

        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId,newObject);
    }

    @Override
    public void updateElement(Comment newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);
    }
}
