package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentRepo extends Repo<Comment,Integer> {
    private static final String COMMENT_TABLE ="Comment" ;
    private static final String VOTE_MAP_TABLE="VoteMap";
    public static Integer lastCommentId = 0;
    private static CommentRepo instance = null;

    public CommentRepo(){
        this.initCommentTable();
        this.initVoteTable();


    }

    private void initVoteTable() {
        initTable(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s(\n" +
                                "userId VARCHAR(255),\n" +
                                "commentId INTEGER,\n" +
                                "vote INTEGER,\n" +
                                "PRIMARY KEY(userId, commentId), \n" +
                                "FOREIGN KEY (userId) REFERENCES " + UserRepo.USER_TABLE + "(username),\n" +
                                "FOREIGN KEY (commentId) REFERENCES " + COMMENT_TABLE + "(id)\n" +
                                ");", VOTE_MAP_TABLE
                )
        );

    }

    private void initCommentTable() {
        this.initTable(String.format(
                "CREATE  TABLE IF NOT EXISTS %s (\n" +
                "    id INTEGER NOT NULL AUTO_INCREMENT,\n" +
                "    userId VARCHAR(255),\n" +
                "    commodityId INTEGER,\n" +
                "    text VARCHAR(255),\n" +
                "    createDate VARCHAR(255),\n" +
                "    PRIMARY KEY (id),\n" +
                "    FOREIGN KEY (userId) REFERENCES %s (username),\n" +
                "    FOREIGN KEY (commodityId) REFERENCES %s (id)\n" +
                ");",COMMENT_TABLE,UserRepo.USER_TABLE,CommodityRepo.COMMODITY_TABLE));

    }

    public static CommentRepo getInstance() {
        if (instance == null) {
            instance = new CommentRepo();
        }
        return instance;
    }

    @Override
    protected String getGetElementByIdStatement() {
        return String.format("SELECT * FROM %s a WHERE a.id = ?;", COMMENT_TABLE);
    }

    @Override
    public void addElement(Comment newObject) throws CustomException, SQLException {
        var tupleMap = newObject.getDBTuple();
        executeUpdate(getAddElementStatement(), List.of(
                tupleMap.get("text"),
                tupleMap.get("userId"),
                tupleMap.get("commodityId"),
                tupleMap.get("createDate")
        ));
    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, Integer id) throws SQLException {
        st.setString(1, id.toString());
    }

    @Override
    protected String getAddElementStatement() {
        return String.format("INSERT INTO %s(text, userId, commodityId, createDate)\n" +
                "VALUES (?, ?, ?, ?);", COMMENT_TABLE);
    }

    @Override
    protected String getGetAllElementsStatement() {
        return String.format("SELECT * FROM %s;", COMMENT_TABLE);
    }

    @Override
    protected Comment convertResultSetToDomainModel(ResultSet rs) throws SQLException, CustomException {
        return new Comment(
                rs.getString("id"),
                rs.getString("text"),
                rs.getString("createDate"),
                getUserVoteMap(rs.getInt("id")),
                UserRepo.getInstance().getElementById(rs.getString("userId")),
                CommodityRepo.getInstance().getElementById(rs.getInt("commodityId"))
        );

    }
    private HashMap<String, Short> getUserVoteMap(Integer commentId) throws SQLException {
        var userVoteMap = new HashMap<String, Short>();
        String sql = String.format("SELECT userId, vote FROM %s WHERE commentId=?;", VOTE_MAP_TABLE);
        var dbOutput = executeQuery(sql, List.of(commentId.toString()));
        var res = dbOutput.getFirst();
        while (res.next()) {
            userVoteMap.put(res.getString("userId"), res.getShort("vote"));
        }
        finishWithResultSet(dbOutput.getSecond());
        return userVoteMap;
    }
    @Override
    protected ArrayList<Comment> convertResultSetToDomainModelList(ResultSet rs) throws SQLException, CustomException {
        ArrayList<Comment> comments = new ArrayList<>();
        while (rs.next()) {
            comments.add(this.convertResultSetToDomainModel(rs));
        }
        return comments;
    }

//    @Override
//    public void updateElement(Comment newObject) throws CustomException {
//        var objectId = String.valueOf(newObject.getId());
//        if (!isIdValid(objectId)) {
//            throw new CustomException("Object Not found");
//        }
//        objectMap.put(objectId, newObject);
//    }

    public void updateCommentVotes(String commentId, String username, int vote) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s (userId, commentId, vote)\n" +
                        "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE\n" +
                        "vote=?;", VOTE_MAP_TABLE);
        executeUpdate(sql, List.of(username, commentId, String.valueOf(vote), String.valueOf(vote)));
    }
    public ArrayList<Comment> getCommentsForCommodity(Integer commodityId) throws SQLException, CustomException {
        var comments = new ArrayList<Comment>();
        String sql = String.format(
                "SELECT *\n" +
                        "FROM %s c\n" +
                        "WHERE c.commodityId=?;", COMMENT_TABLE);
        var dbOutput = executeQuery(sql, List.of(commodityId.toString()));
        var res = convertResultSetToDomainModelList(dbOutput.getFirst());
        finishWithResultSet(dbOutput.getSecond());
        return res;
    }
}
