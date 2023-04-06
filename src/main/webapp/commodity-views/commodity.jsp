
<%@page import="ie.comment.Comment"%>
<%@page import="ie.Controller"%>
<%@ page import="ie.user.User" %>
<%@ page import="ie.user.UserManager" %>
<%@ page import="ie.commodity.CommodityManager" %>
<%@ page import="ie.provider.ProviderManager" %>
<%@ page import="ie.View" %>
<%@ page import="ie.commodity.Commodity" %>
<%@ page import="ie.exeption.CustomException" %>
<%@ page import="ie.comment.CommentManager" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <title>Commodity</title>
  <style>
      li {
          padding: 5px;
      }
      table {
          width: 100%;
          text-align: center;
      }
  </style>
</head>
<%
    var commodityId= (String)request.getAttribute("commodityId");
    Commodity   commodity = CommodityManager.getInstance().getElementById(commodityId);
    List<Comment> comments= CommentManager.getInstance().getElementsById(commodity.getComments());

%>
<body>
  <jsp:include page="/header.jsp" />

  <ul>
      <li id="id">Id: <%=commodity.getId() %></li>
      <li id="name">Name: <%=commodity.getName()%></li>
      <li id="providerName">Provider Name: <%=
              ProviderManager.getInstance().getElementById(String.valueOf(commodity.getProvideId())).getName()
      %></li>
      <li id="price">Price: <%=
          commodity.getPrice()
      %></li>
      <li id="categories">Categories: <%=
          View.getCSVFromList(commodity.getCategories())
      %></li>
      <li id="rating">Rating: <%=
          commodity.getRate()
      %></li>
      <li id="inStock">In Stock: <%=
          commodity.getInStock()
      %></li>
  </ul>

  <label>Add Your Comment:</label>
  <form action="" method="post">
      <input type="text" name="comment" value="" />
      <button type="submit" name="action" value="comment">Submit</button>
  </form>
  <br>
  <form action="" method="POST">
      <label>Rate(between 1 and 10):</label>
      <input type="number" id="quantity" name="quantity" min="1" max="10">
      <button type="submit" name="action" value="rate">Rate</button>
  </form>
  <br>
  <form action="" method="POST">
      <button type="submit" name="action" value="addToBuyList">Add to BuyList</button>
  </form>
  <table>
      <tr>
          <th>Username</th>
          <th>Comment</th>
          <th>Date</th>
          <th></th>
          <th></th>
      </tr>
      <% for (Comment comment : comments){%>
      <tr>
          <td>@<%= comment.getCommentUsernameOwner() %></td>
          <td><%= comment.getText()%></td>
          <td><%= comment.getDate()%></td>
          <td>
              <form action="" method="POST">
                  <label for=""><%= comment.getLike()%></label>
                  <input
                          id="form_comment_id"
                          type="hidden"
                          name="comment_id"
                          value="<%= comment.getIds()%>"
                  />
                  <input type="hidden" id="form_action" name="action" value="like">
                  <button type="submit" name="action" value="like">like</button>
              </form>
          </td>
          <td>
              <form action="" method="POST">
                  <label for=""><%= comment.getDisLike()%></label>
                  <input
                          id="form_comment_id"
                          type="hidden"
                          name="comment_id"
                          value="<%= comment.getIds()%>"
                  />
                  <input type="hidden" id="form_action" name="action" value="dislike">
                  <button type="submit" name="action" value="dislike">dislike</button>
              </form>
          </td>
      </tr>
      <%}%>
  </table>

</body>
</html>