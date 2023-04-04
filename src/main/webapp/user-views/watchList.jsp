<%--<%@page import="java.util.List"%>--%>
<%--<%@page import="ie.app.film.Film"%>--%>
<%--<%@page import="ie.generic.view.HtmlUtility"%>--%>
<%--<%--%>
<%--    List<Film> watchlistMovies = (List<Film>)request.getAttribute("movies");--%>
<%--    List<Film> recMovies = (List<Film>)request.getAttribute("recMovies");--%>
<%--%>--%>

<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Watch List</title>--%>
<%--    <style>--%>
<%--        li, td, th {--%>
<%--          padding: 5px;--%>
<%--        }--%>
<%--      </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--    <jsp:include page="/header.jsp" />--%>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <th>Movie</th>--%>
<%--            <th>releaseDate</th> --%>
<%--            <th>director</th> --%>
<%--            <th>genres</th> --%>
<%--            <th>imdb Rate</th> --%>
<%--            <th>rating</th> --%>
<%--            <th>duration</th> --%>
<%--            <th></th>--%>
<%--            <th></th>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--            for (int idx = 0; idx < watchlistMovies.size(); idx++){--%>
<%--                Film film = watchlistMovies.get(idx);--%>
<%--        %>--%>
<%--        <tr>--%>
<%--            <td><%= film.getName()%></td>--%>
<%--            <td><%= film.getReleaseDate()%></td>--%>
<%--            <td><%= film.getDirector()%></td>--%>
<%--            <td><%= HtmlUtility.getCSVFromList(film.getGenres())%></td>--%>
<%--            <td><%= film.getImdbRate()%></td>--%>
<%--            <td><%= film.getAverageRating()%></td>--%>
<%--            <td><%= film.getDuration()%></td>--%>
<%--            <td><a href="/movies/<%= film.getId() %>">Link</a></td>--%>
<%--            <td>        --%>
<%--                <form action="" method="POST" >--%>
<%--                    <input id="form_movie_id" type="hidden" name="movie_id" value="<%= film.getId() %>">--%>
<%--                    <input type="hidden" id="form_action" name="action" value="delete">--%>
<%--                    <button type="submit">Remove</button>--%>
<%--                </form>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--            <%}%>--%>
<%--    </table>--%>
<%--    <br>--%>
<%--    <br>--%>
<%--    <h2>Recommendation List</h2>--%>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <th>Movie</th>--%>
<%--            <th>imdb Rate</th> --%>
<%--            <th></th>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--            for (int idx = 0; idx < recMovies.size(); idx++){--%>
<%--                Film film = recMovies.get(idx);--%>
<%--        %>--%>
<%--        <tr>--%>
<%--            <td><%= film.getName()%></td>--%>
<%--            <td><%= film.getImdbRate()%></td>--%>
<%--            <td><a href="/movies/<%= film.getId() %>">Link</a></td>--%>
<%--        </tr>--%>
<%--            <%}%>--%>
<%--    </table>--%>
<%--</body>--%>
<%--</html>--%>