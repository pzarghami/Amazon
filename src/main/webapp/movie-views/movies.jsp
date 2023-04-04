<%--<%@page import="java.util.List"%>--%>
<%--<%@page import="ie.app.actor.Actor"%>--%>
<%--<%@page import="ie.app.film.Film"%>--%>
<%--<%@page import="ie.util.types.Constant"%>--%>
<%--<%@page import="ie.Iemdb"%>--%>
<%--<%@page import="ie.generic.view.HtmlUtility"%>--%>
<%--<%@page import="java.util.stream.Collectors"%>--%>
<%--<%--%>
<%--    List<Film> movies = (List<Film>)request.getAttribute("movies");--%>
<%--    List<List<Actor>> moviesCast = (List<List<Actor>>)request.getAttribute("moviesCast");--%>
<%--%>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Movies</title>--%>
<%--    <style>--%>
<%--      li, td, th {--%>
<%--        padding: 5px;--%>
<%--      }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--    <jsp:include page="/header.jsp" />--%>
<%--    <br><br>--%>
<%--    <form action="" method="POST">--%>
<%--        <label>Search:</label>--%>
<%--        <input type="text" name="search" value="">--%>
<%--        <button type="submit" name="action" value="search">Search</button>--%>
<%--        <button type="submit" name="action" value="clear">Clear Search</button>--%>
<%--    </form>--%>
<%--    <br><br>--%>
<%--    <form action="" method="POST">--%>
<%--        <label>Sort By:</label>--%>
<%--        <button type="submit" name="action" value="sort_by_imdb">imdb Rate</button>--%>
<%--        <button type="submit" name="action" value="sort_by_date">releaseDate</button>--%>
<%--    </form>--%>
<%--    <br>--%>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <th>name</th>--%>
<%--            <th>summary</th> --%>
<%--            <th>releaseDate</th>--%>
<%--            <th>director</th>--%>
<%--            <th>writers</th>--%>
<%--            <th>genres</th>--%>
<%--            <th>cast</th>--%>
<%--            <th>imdb Rate</th>--%>
<%--            <th>rating</th>--%>
<%--            <th>duration</th>--%>
<%--            <th>ageLimit</th>--%>
<%--            <th>Link</th>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--            for (int idx = 0; idx < movies.size(); idx++){--%>
<%--                Film film = movies.get(idx);--%>
<%--                List<Actor> cast = moviesCast.get(idx);--%>
<%--        %>--%>
<%--        <tr>--%>
<%--            <td><%= film.getName()%></td>--%>
<%--            <td><%= film.getSummary()%></td>--%>
<%--            <td><%= film.getReleaseDate()%></td>--%>
<%--            <td><%= film.getDirector()%></td>--%>
<%--            <td><%= HtmlUtility.getCSVFromList(film.getWriters())%></td>--%>
<%--            <td><%= HtmlUtility.getCSVFromList(film.getGenres())%></td>--%>
<%--            <td><%= HtmlUtility.getCSVFromList(cast.stream().map(actor -> actor.getName()).collect(Collectors.toList()))%></td>--%>
<%--            <td><%= film.getImdbRate()%></td>--%>
<%--            <td><%= film.getAverageRating()%></td>--%>
<%--            <td><%= film.getDuration()%></td>--%>
<%--            <td><%= film.getAgeLimit()%></td>--%>
<%--            <td><a href="/movies/<%= film.getId() %>">Link</a></td>--%>
<%--        </tr>--%>
<%--            <%}%>--%>
<%--    </table>--%>
<%--</body>--%>
<%--</html>--%>