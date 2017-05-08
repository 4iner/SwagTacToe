import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.GridLayout;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import javax.imageio.*;

/**
 * A simple tic-tac-toe game with Java swing GUI.
 * 
 * @author Mustafa Abdulmajeed
 * @version 1
 */

public class TicTacToeFrame 
{ 
    private JLabel status; // text area to print game status
    private JFrame frame;
    private JPanel panel;
    private Button buttons[][] = new Button[3][3];
    private boolean winner;
    private String player;
    private boolean plr;
    private int numFreeSquares;
    private boolean quit;
    private Clip music;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private ImageIcon xIcon;
    private ImageIcon oIcon;
    /** 
     * Constructs a new Tic-Tac-Toe JFrame with 3 JPanels, one for the board, one for status, and one for menu (newgame/exit)
     */
    public TicTacToeFrame()
    { 
        // add the necessary code here
        panel = new JPanel();
        winner = false;
        quit = false;
        plr = true;
        player = "X";
        try{
            //o icon source: https://twitter.com/realovirtualcom
            oIcon = new ImageIcon(ImageIO.read(getClass().getResource("o.png")));
            //x icon source: https://x.company/
            xIcon = new ImageIcon(ImageIO.read(getClass().getResource("x.png"))); 
        } catch(Exception ex){
        }
        numFreeSquares = 9;
        frame = new JFrame("TicTacToe");
        status = new JLabel("Welcome to TicTacToe! It is currently X's turn. ",SwingConstants.CENTER);
        frame.setSize(400, 400);
        panel.setLayout(new GridLayout(3,3));
        for(int i = 0; i < 3; i++){
            for(int c = 0; c < 3;c++){
                buttons[i][c]  = new Button(i, c, this);
                buttons[i][c].setPreferredSize(new Dimension(50,50));
                panel.add(buttons[i][c]);
                buttons[i][c].setText(" ");
            }
        }
        JPanel panel1 = new JPanel();
        panel1.add(status);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 2));
        JMenuBar jBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        
        JMenuItem newGameB = new JMenuItem("New game");
        JMenuItem quitB = new JMenuItem("Quit");
        jBar.add(menu);
        menu.add(newGameB);
        menu.add(quitB);
        frame.setJMenuBar(jBar);
       /* try{
            AudioInputStream audioInputStream  =
                AudioSystem.getAudioInputStream(
                    this.getClass().getResource("song.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            music = clip;
            audioInputStream.close();
        }
        catch(Exception ex)
        {
        }*/
        newGameB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                }
            });
        quitB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    quit = true;
                    //music.stop();
                    clip.close();
                    //music.close();
                }
            });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    int ans = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to leave this game? :(", "Really Closing?", 
                        JOptionPane.YES_NO_OPTION);
                    if ( ans == JOptionPane.YES_OPTION){
                        frame.setVisible(false);
                        quit = true;
                        clip.stop();
                        //music.stop();
                        clip.close();
                        //music.close();
                        frame.dispose();
                    } else if (ans == JOptionPane.NO_OPTION){
                        
                    }
                }
            });
        //panel2.add(newGameB);
        //panel2.add(quitB);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(300, 300));
        panel1.setPreferredSize(new Dimension(150,30));
        //panel2.setPreferredSize(new Dimension(150,100));
        ImageIcon img = new ImageIcon(this.getClass().getResource("icon.jpg"));
        frame.setIconImage(img.getImage());
        frame.add(panel);
        frame.add(panel1);
        frame.add(panel2);  
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * returns if the game is done.
     */
    public boolean end(){
        return quit;
    }
    /**
     * returns the icon of the respective player
     */
    public ImageIcon getIcon(String p){
        return p.equals("X")?xIcon:oIcon;
    }
    
    /**
     * resets the board and starts a new game
     */
    public void resetGame(){
        winner = false;
        player = "X";
        plr = true;
        numFreeSquares =  9;
        for(int i = 0; i < 3; i++){
            for(int c = 0; c < 3;c++){
                buttons[i][c].setText(" ");
                buttons[i][c].setIcon(null);
                buttons[i][c].newMove();
            }
        }
        clip.stop();
        clip.close();
        status.setText("New game started. X's turn.");
    }

    /**
     * updates the current board
     */
    public void update(){
        numFreeSquares--;
        if(winner){
            status.setText(player+" won the game.");
            for(int i = 0; i < 3; i++){
                for(int c = 0; c < 3;c++){
                    buttons[i][c].endMove();
                }
            }
        }
        else if(numFreeSquares==0){
            try{
                AudioInputStream audioInputStream  =
                    AudioSystem.getAudioInputStream(
                        this.getClass().getResource("tie.wav"));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                audioInputStream.close();
            }
            catch(Exception ex)
            {
            }
            status.setText("Tied game. Press New Game, or Quit");
        }
        else {
            player = plr ? "X" : "O";
            status.setText("Game in progress. "+player+"'s turn.");
        }
    }

    /**
     * checks if there is a winner at the current location
     */
    public boolean haveWinner(int row, int col) 
    {
        // check row "row"
        if(buttons[row][0].getIcon()!=null && buttons[row][1].getIcon()!=null && buttons[row][2].getIcon()!=null)
            if (buttons[row][0].getIcon().equals(buttons[row][1].getIcon()) &&
            buttons[row][0].getIcon().equals(buttons[row][2].getIcon()) ) return true;

        // check column "col"
        if(buttons[0][col].getIcon()!=null && buttons[1][col].getIcon()!=null && buttons[2][col].getIcon()!=null)
            if ( buttons[0][col].getIcon().equals(buttons[1][col].getIcon()) &&
            buttons[0][col].getIcon().equals(buttons[2][col].getIcon()) ) return true;

        // if row=col check one diagonal
        if (row==col && buttons[0][0].getIcon()!=null && buttons[1][1].getIcon()!=null && buttons[2][2].getIcon()!=null)
            if ( buttons[0][0].getIcon().equals(buttons[1][1].getIcon()) &&
            buttons[0][0].getIcon().equals(buttons[2][2].getIcon()) ) return true;

        // if row=2-col check other diagonal
        if (row==2-col && buttons[0][2].getIcon()!=null && buttons[1][1].getIcon()!=null && buttons[2][0].getIcon()!=null)
            if ( buttons[0][2].getIcon().equals(buttons[1][1].getIcon()) &&
            buttons[0][2].getIcon().equals(buttons[2][0].getIcon()) ) return true;

        // no winner yet
        return false;
    }

    /**
     * return turn of the player
     */
    public boolean getPlayer(){
        return plr;
    }

    /**
     * change turns of players
     */
    public void changePlayer(){
        plr = !plr;
    }

    /**
     * if someone wins, this method tells us we won, and changes the player name to that player.
     */
    public void win(String player){
        try{
            AudioInputStream audioInputStream  =
                AudioSystem.getAudioInputStream(
                    this.getClass().getResource("triple.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            audioInputStream.close();
        }
        catch(Exception ex)
        {
        }
        winner = true;
        this.player = player;
    }

}