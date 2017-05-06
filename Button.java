import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import javax.imageio.*;

/**
 * Button for the tic tac toe board
 * 
 * @author Mustafa Abdulmajeed
 * @version 1
 */
public class Button extends JButton implements ActionListener
{
    // instance variables - replace the example below with your own
    private int col;
    private int row;
    //private ImageIcon x, o;
    private TicTacToeFrame gameRef;
    /**
     * Constructor takes in current position of button, and also the game reference it belongs to.
     */
    public Button(int row, int col, TicTacToeFrame game)
    {
        // initialise instance variables
        super(" ");
        this.col = col;
        this.row = row;
        gameRef = game;
        this.addActionListener(this);
    }

    /**
     * checks if user clicked button, if clicked then update game and disable this button
     */
    public void actionPerformed(ActionEvent e){
        String player = gameRef.getPlayer() ? "x" : "o";
        try{
            ImageIcon img = gameRef.getIcon(player);
            this.setIcon(img);
            this.setDisabledIcon(img);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getResource(player+".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex)
        {
        }
        gameRef.changePlayer();
        if(gameRef.haveWinner(row, col)){
            gameRef.win(player);
        }
        gameRef.update();
        this.setEnabled(false);
    }

    /**
     * if game ends all buttons are disabled through this function
     */
    public void endMove(){
        this.setEnabled(false);
    }

    /**
     * if new game, all buttons are re-enabled using this method
     */
    public void newMove(){
        this.setEnabled(true);
    }

}
