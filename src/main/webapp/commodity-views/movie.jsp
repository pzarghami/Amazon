<%--<%@page import="ie.app.film.Film"%>--%>
<%--<%@page import="ie.generic.view.HtmlUtility"%>--%>
<%--<%@page import="java.util.List"%>--%>
<%--<%@page import="ie.app.actor.Actor"%>--%>
<%--<%@page import="ie.app.comment.Comment"%>--%>


<%--<%--%>
<%--  Film movie = (Film)request.getAttribute("movie");--%>
<%--  List<Actor> cast = (List<Actor>)request.getAttribute("cast");--%>
<%--  List<Comment> comments = (List<Comment>)request.getAttribute("comments");--%>
<%--%>--%>

<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>

<%--<head>--%>
<%--  <meta charset="UTF-8" />--%>
<%--  <title>Movie</title>--%>
<%--  <style>--%>
<%--    li,--%>
<%--    td,--%>
<%--    th {--%>
<%--      padding: 5px;--%>
<%--    }--%>
<%--  </style>--%>
<%--</head>--%>

<%--<body>--%>
<%--  <jsp:include page="/header.jsp" />--%>

<%--  <ul>--%>
<%--    <li id="name">name: <%= movie.getName()%></li>--%>
<%--    <li id="summary"><%= movie.getSummary()%></li>--%>
<%--    <li id="releaseDate">releaseDate: <%= movie.getReleaseDate()%></li>--%>
<%--    <li id="director">director: <%= movie.getDirector()%></li>--%>
<%--    <li id="writers">writers: <%= HtmlUtility.getCSVFromList(movie.getWriters())%></li>--%>
<%--    <li id="genres">genres: <%= HtmlUtility.getCSVFromList(movie.getGenres())%></li>--%>
<%--    <li id="imdbRate">imdb Rate: <%= movie.getImdbRate()%></li>--%>
<%--    <li id="rating">rating: <%= movie.getAverageRating()%></li>--%>
<%--    <li id="duration">duration: <%= movie.getDuration()%> minutes</li>--%>
<%--    <li id="ageLimit">ageLimit: <%= movie.getAgeLimit()%></li>--%>
<%--  </ul>--%>
<%--  <h3>Cast</h3>--%>
<%--  <table>--%>
<%--    <tr>--%>
<%--      <th>name</th>--%>
<%--      <th>age</th>--%>
<%--      <th></th>--%>
<%--    </tr>--%>
<%--      <% for (Actor actor : cast){%>--%>
<%--        <tr>--%>
<%--            <td><%= actor.getName()%></td>--%>
<%--            <td><%= actor.getAge()%></td>--%>
<%--            <td><a href="/actors/<%= actor.getId() %>">Link</a></td>--%>
<%--        </tr>--%>
<%--      <%}%>--%>
<%--  </table>--%>
<%--  <br>--%>
<%--    <form action="" method="POST">--%>
<%--      <label>Rate(between 1 and 10):</label>--%>
<%--      <input type="number" id="quantity" name="quantity" min="1" max="10">--%>
<%--      <input type="hidden" id="form_action" name="action" value="rate">--%>
<%--      <input type="hidden" id="form_movie_id" name="movie_id" value="<%= movie.getId()%>">--%>
<%--      <button type="submit">rate</button>--%>
<%--    </form>--%>
<%--    <br>--%>
<%--    <form action="/watchlist" method="POST">--%>
<%--      <input type="hidden" id="form_action" name="action" value="add">--%>
<%--      <input type="hidden" id="form_movie_id" name="movie_id" value="<%= movie.getId()%>">--%>
<%--      <button type="submit">Add to WatchList</button>--%>
<%--    </form>--%>
<%--    <br>--%>
<%--    <table>--%>
<%--      <tr>--%>
<%--        <th>nickname</th>--%>
<%--        <th>comment</th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--      </tr>--%>
<%--        <% for (Comment comment : comments){%>--%>
<%--          <tr>--%>
<%--              <td>@<%= comment.getCommentOwnerNickName() %></td>--%>
<%--              <td><%= comment.getText()%></td>--%>
<%--              <td>--%>
<%--                <form action="" method="POST">--%>
<%--                  <label for=""><%= comment.getCommentLikes()%></label>--%>
<%--                  <input--%>
<%--                    id="form_comment_id"--%>
<%--                    type="hidden"--%>
<%--                    name="comment_id"--%>
<%--                    value="<%= comment.getId()%>"--%>
<%--                  />--%>
<%--                  <input type="hidden" id="form_action" name="action" value="like">--%>
<%--                  <input type="hidden" id="form_movie_id" name="movie_id" value="<%= movie.getId()%>">--%>
<%--                  <button type="submit">like</button>--%>
<%--                </form>--%>
<%--              </td>--%>
<%--              <td>--%>
<%--                <form action="" method="POST">--%>
<%--                  <label for=""><%= comment.getCommentDislikes()%></label>--%>
<%--                  <input--%>
<%--                    id="form_comment_id"--%>
<%--                    type="hidden"--%>
<%--                    name="comment_id"--%>
<%--                    value="<%= comment.getId()%>"--%>
<%--                  />--%>
<%--                  <input type="hidden" id="form_action" name="action" value="dislike">--%>
<%--                  <input type="hidden" id="form_movie_id" name="movie_id" value="<%= movie.getId()%>">--%>
<%--                  <button type="submit">dislike</button>--%>
<%--                </form>--%>
<%--              </td>--%>
<%--          </tr>--%>
<%--        <%}%>--%>
<%--    </table>--%>
<%--    <br><br>--%>
<%--    <form action="" method="POST">--%>
<%--      <label>Your Comment:</label>--%>
<%--      <input type="text" name="comment_text" value="">--%>
<%--      <input type="hidden" id="form_action" name="action" value="comment">--%>
<%--      <input type="hidden" id="form_movie_id" name="movie_id" value="<%= movie.getId()%>">--%>
<%--      <button type="submit">Add Comment</button>--%>
<%--    </form>--%>
<%--</body>--%>
<%--</html>--%>