package ie.user;

import ie.Controller;

public class UserController extends Controller{
    private UserView viewHandler;
    public UserController(){this.viewHandler = new UserView();}
}
