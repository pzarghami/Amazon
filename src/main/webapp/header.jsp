  <%@page import="ie.Baloot"%>
  <%@page import="ie.Constant"%>


  <a href="<%="/"%>">Home</a>
  <%
    String email;
    String username;
    if(Baloot.loggedInUser==null){
      email="";
      username="";
    }
    else {
      email = Baloot.loggedInUser.getEmail();
      username=Baloot.loggedInUser.getUsername();
    }
  %>
  <p id="email">Email: <%= email%></p>
  <p id="username">Username: <%= username%></p>