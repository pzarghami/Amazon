<%@page import="java.util.List"%>
<%@page import="ie.Constant"%>
<%@page import="ie.Baloot"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ie.commodity.CommodityManager" %>
<%@ page import="ie.commodity.Commodity" %>
<%@ page import="ie.View" %>
<%@ page import="java.util.Collections" %>
<%@ page import="ie.commodity.sorts.SortById" %>
<%@ page import="ie.commodity.sorts.SortByPrice" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<%
    var commodityManger=CommodityManager.getInstance();
    List<Commodity> commoditiesList= commodityManger.getElementsById(commodityManger.commoditiesListWithFilters);
    Collections.sort(commoditiesList, new SortById());
    if(commodityManger.sortByPriceFlag)
        Collections.sort(commoditiesList,new SortByPrice());

%>
<body>
    <jsp:include page="/header.jsp" />
    <br><br>
    <form action="" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="">
        <button type="submit" name="action" value="search_by_category">Search By Category</button>
        <button type="submit" name="action" value="search_by_name">Search By Name</button>
        <button type="submit" name="action" value="clear">Clear Search</button>
    </form>
    <br><br>
    <form action="" method="POST">
        <label>Sort By:</label>
        <button type="submit" name="action" value="sort_by_price">Price</button>
        <button type="submit" name="action" value="clear_sort">Clear Sort</button>
    </form>
    <br><br>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Provider Name</th>
            <th>Price</th>
            <th>Categories</th>
            <th>Rating</th>
            <th>In Stock</th>
            <th>Links</th>
        </tr>
        <%
            for (int idx = 0; idx < commoditiesList.size(); idx++){
                Commodity commodity = (Commodity) commoditiesList.get(idx);
        %>
        <tr>
            <td><%= commodity.getId()%></td>
            <td><%= commodity.getName()%></td>
            <td><%= commodity.getProvideId()%></td>
            <td><%= commodity.getPrice()%></td>
            <td><%= View.getCSVFromList(commodity.getCategories())%></td>
            <td><%= commodity.getRate()%></td>
            <td><%= commodity.getInStock()%></td>
            <td><a href="/commodities/<%= commodity.getId() %>">Link</a></td>
        </tr>
            <%}%>
    </table>
</body>
</html>