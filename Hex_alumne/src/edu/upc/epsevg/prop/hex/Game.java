package edu.upc.epsevg.prop.hex;

import edu.upc.epsevg.prop.hex.players.HumanPlayer;
import edu.upc.epsevg.prop.hex.players.RandomPlayer;
import edu.upc.epsevg.prop.hex.IPlayer;
import edu.upc.epsevg.prop.hex.IPlayer;
import edu.upc.epsevg.prop.hex.IPlayer;
import edu.upc.epsevg.prop.hex.players.HEXageradaAI;
import edu.upc.epsevg.prop.hex.players.H_E_X_Player;
import edu.upc.epsevg.prop.hex.players.Jugador2;
import edu.upc.epsevg.prop.hex.players.JugadorEstrella;




import javax.swing.SwingUtilities;

/**
 * Checkers: el joc de taula.
 * @author bernat
 */
public class Game {
        /**
     * @param args
     */
    public static void main(String[] args) { 
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                IPlayer player1 = new H_E_X_Player(3/*GB*/);
                
                IPlayer player2 = new HumanPlayer("Human");
                                
                IPlayer normal = new HEXageradaAI(true, 2, false);
                IPlayer star = new HEXageradaAI(true, 2, true);
                IPlayer player4 = new Jugador2(true, 2, 50);
                IPlayer estrella = new JugadorEstrella(true,3);
                
                new Board(player1,normal, 11 /*mida*/,  10/*s*/, false);
             }
        });
    }
}
