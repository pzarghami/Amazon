package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentRepo extends Repo<Comment,Integer> {
    public static Integer lastCommentId = 0;
    private static CommentRepo instance = null;

    public static CommentRepo getInstance() {
        if (instance == null) {
            instance = new CommentRepo();
        }
        return instance;
    }

    @Override
    protected String getGetElementByIdStatement() {
        return null;
    }

    @Override
    public void addElement(Comment newObject) throws CustomException {
        if (newObject.setId(lastCommentId + 1)) {
            objectMap.put((++lastCommentId).toString(), newObject);
        } else {
            throw new CustomException("InvalidComment");
        }
    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, Integer id) throws SQLException {

    }

    @Override
    protected String getAddElementStatement() {
        return null;
    }

    @Override
    protected String getGetAllElementsStatement() {
        return null;
    }

    @Override
    protected Comment convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected ArrayList<Comment> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        return null;
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
