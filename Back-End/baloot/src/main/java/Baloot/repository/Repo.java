package Baloot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Baloot.Exeption.CustomException;
import Baloot.model.User;
import kotlin.Pair;

public abstract class Repo<T, PK> {
    static User loggedInUser;
    protected Map<String, T> objectMap;
    protected CustomException notFoundException;
    public Repo() {
        objectMap = new HashMap<>();
    }
    abstract protected String getGetElementByIdStatement();
    public abstract void addElement(T newObject) throws CustomException, SQLException;
    abstract protected void fillGetElementByIdValues(PreparedStatement st, PK id) throws SQLException;
    abstract protected String getAddElementStatement();
    abstract protected String getGetAllElementsStatement();
    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;
    abstract protected ArrayList<T> convertResultSetToDomainModelList(ResultSet rs) throws SQLException;
    public abstract void updateElement(T newObject) throws CustomException;
    public T getElementById(PK id) throws SQLException,CustomException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getGetElementByIdStatement());
        try {
            fillGetElementByIdValues(st, id);
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                st.close();
                con.close();
                throw new CustomException("notFoundException");
            }
            T result = convertResultSetToDomainModel(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException | CustomException e) {
            st.close();
            con.close();
            System.out.println("error in Repository.find query.");
            e.printStackTrace();
            throw e;
        }
    }
    public List<T> getAllElements() throws SQLException {
        List<T> result = new ArrayList<>();
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getGetAllElementsStatement());
        try {
            ResultSet resultSet = st.executeQuery();
            result = convertResultSetToDomainModelList(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException e) {
            st.close();
            con.close();
            System.out.println("error in Repository.findAll query.");
            e.printStackTrace();
            throw e;
        }
    }

    public List<T> getElementsById(List<PK> ids) throws SQLException, CustomException {
        List<T> result = new ArrayList<>();
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getGetElementByIdStatement());
        try {
            for (var id : ids) {
                fillGetElementByIdValues(st, id);
                ResultSet resultSet = st.executeQuery();
                if (!resultSet.next()) {
                    st.close();
                    con.close();
                    throw new CustomException("notFoundException");
                }
                result.add(convertResultSetToDomainModel(resultSet));
            }
            st.close();
            con.close();
        } catch (SQLException | CustomException e) {
            st.close();
            con.close();
            System.out.println("error in Repository.find query.");
            e.printStackTrace();
            throw e;
        }
        return result;
    }
    public boolean isIdValid(String id) {
        return objectMap.containsKey(id);
    }

    protected void initTable(String createTableSql) {
        try {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement createTableStatement = con.prepareStatement(createTableSql);
            createTableStatement.executeUpdate();
            createTableStatement.close();
            con.close();
        } catch (SQLException e) {
            //ignore
        }
    }

    public boolean isIdListValid(List<String> ids) {
        return objectMap.keySet().containsAll(ids);
    }

    public void removeElements(List<String> ids) {
        if (ids == null) {
            objectMap.clear();
        }
    }
    protected Pair<ResultSet, Pair<Connection, PreparedStatement>> executeQuery(String sql, List<String> fillValues) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        fillValues(st, fillValues);
        var result = st.executeQuery();
        return new Pair<>(result, new Pair<>(con, st));
    }
    protected int executeUpdate(String sql, List<String> fillValues) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        fillValues(st, fillValues);
        var result = st.executeUpdate();
        st.close();
        con.close();
        return result;
    }

    protected void fillValues(PreparedStatement st, List<String> values) throws SQLException {
        int valuePosition = 1;
        for (var value : values) {
            st.setString(valuePosition, value);
            valuePosition++;
        }
    }
    protected void finishWithResultSet(Pair<Connection, PreparedStatement> closables) throws SQLException {
        closables.getFirst().close();
        closables.getSecond().close();
    }

}
