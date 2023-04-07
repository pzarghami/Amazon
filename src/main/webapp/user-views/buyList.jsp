<%@page import="java.util.List"%>
<%@page import="ie.Baloot"%>
<%@page import="ie.commodity.Commodity"%>
<%@page import="ie.generic.HtmlUtility"%>
<%
    List<Commodity> buyList = (List<Commodity>)request.getAttribute("commodities");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<%
    String email;
    String username;
    String address;
    String birthDate;
    int credit;
    float price;

    email = Baloot.loggedInUser.getEmail();
    username = Baloot.loggedInUser.getUsername();
    address = Baloot.loggedInUser.getAddress();
    birthDate = Baloot.loggedInUser.getBirthDate();
    price = Baloot.loggedInUser.getCurrentBuyListPrice();
    credit = Baloot.loggedInUser.getCredit();
%>
<body>
<jsp:include page="/header.jsp" />
<ul>
    <li id="username">Username: <%= username%></li>
    <li id="email">Email: <%= email%></li>
    <li id="birthDate">Birth Date: <%= birthDate%></li>
    <li id="address">Address: <%= address%></li>
    <li id="credit">Credit: <%= credit%></li>
    <li>Current Buy List Price: <%= price%></li>
    <li>
        <a href="/credit">Add Credit</a>
    </li>
    <li>
        <form action="" method="POST">
            <label>Submit & Pay</label>
            <input id="form_payment" type="hidden" name="userId" value="Farhad">
            <button type="submit">Payment</button>
        </form>
    </li>
</ul>
<table>
    <caption>
        <h2>Buy List</h2>
    </caption>
    <tbody><tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th></th>
        <th></th>
    </tr>
    <%
        for (Commodity commodity : buyList) {
    %>
    <tr>
        <td><%=commodity.getId() %>
        </td>
        <td><%=commodity.getName() %>
        </td>
        <td><%=commodity.getProvideId() %>
        </td>
        <td><%=commodity.getPrice() %>
        </td>
        <td><%=HtmlUtility.getCSVFromList(commodity.getCategories()) %>
        </td>
        <td><%=commodity.getRate() %>
        </td>
        <td><%=commodity.getInStock() %>
        </td>
        <td><a href="/commodities/<%= commodity.getId() %>">Link</a></td>
        <td>
            <form action="" method="POST">
                <input id="form_commodity_id" type="hidden" name="commodity_id" value="<%= commodity.getId() %>">
                <input type="hidden" id="form_action" name="action" value="delete">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%}%>
    </tbody></table>
</body></html>