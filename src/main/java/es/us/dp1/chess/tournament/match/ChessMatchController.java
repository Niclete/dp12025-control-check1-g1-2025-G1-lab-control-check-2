package es.us.dp1.chess.tournament.match;


import es.us.dp1.chess.tournament.user.UserService;



public class ChessMatchController {

    UserService userService;
    ChessMatchService matchService;


    public ChessMatchController(ChessMatchService service,UserService userService){
        this.matchService=service;
        this.userService=userService;    
    }

}