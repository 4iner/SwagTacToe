

/**
 * Main class to run the game until the game ends, by which then the game is terminated
 * 
 * @author Mustafa Abdulmajeed
 * @version 1
 */
public class Main
{
    public static void main(String[] args){
        TicTacToeFrame game = new TicTacToeFrame();
        while(game.end()){}//NOT an infinite loop; game.end() changes every iteration. This checks if user pressed quit.
        //if(game.end()) game.stopMusic();
    }
}
