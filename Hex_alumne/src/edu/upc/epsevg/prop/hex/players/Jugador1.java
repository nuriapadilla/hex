/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import edu.upc.epsevg.prop.hex.HexGameStatus;
import edu.upc.epsevg.prop.hex.IAuto;
import edu.upc.epsevg.prop.hex.IPlayer;
import edu.upc.epsevg.prop.hex.PlayerMove;
import edu.upc.epsevg.prop.hex.SearchType;
import java.awt.Point;

/**
 *
 * @author nuria
 */
public class Jugador1 implements IPlayer, IAuto {

    public Jugador1(){
        
    }
    public int heuristica() {
        return 0;
    }

    /**
     * Implementa l'algorisme Minimax per calcular el millor moviment
     * disponible.
     *
     * @param profunditat Profunditat restant a explorar.
     * @return Columna òptima per al moviment.
     */
    public PlayerMove minimax(HexGameStatus hgs, int profunditat) { // LA PRINCIPAL
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        PlayerMove jugada = new PlayerMove(new Point(0,0), 0, profunditat, SearchType.MINIMAX);
        int maxEval = Integer.MIN_VALUE;
        int column = -1;
        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);
                if (newHgs.getPos(i, j) == 0) {
                    // altanto
                    if (column == -1) {
                        column = i;
                        jugada = new PlayerMove(punt, 0, profunditat, SearchType.MINIMAX);
                    }
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return new PlayerMove(punt, 0, profunditat, SearchType.MINIMAX); // canviar el 0 i la pprofunditat, esta malament
                    } else {
                        int h = minimazing(newHgs, profunditat, alpha, beta);
                        //System.out.println("Per la columna: " + i + " tenim heuristica: " + h);
                        if (h > maxEval) {
                            maxEval = h;
                            column = i;
                        }
                        if (h > alpha) {
                            alpha = h;

                        }
                        if (alpha > beta) {
                            break;
                        }
                    }
                }
            }

        }

        return jugada;
    }

    /**
     * Funció de suport per a Minimax: minimització dels valors heurístics.
     *
     * @param hgs
     * @param profunditat Profunditat restant a explorar.
     * @param alpha Valor alfa per a la poda alfa-beta.
     * @param beta Valor beta per a la poda alfa-beta.
     * @return Valor heurístic mínim trobat.
     */
    public int minimazing(HexGameStatus hgs, int profunditat, int alpha, int beta) {
        int minEval = Integer.MAX_VALUE;

        if (profunditat == 0) {
            int h = heuristica();
            return h;
        }

        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);
                if (newHgs.getPos(i, j) == 0) {

                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {

                        return Integer.MIN_VALUE;
                    } else {
                        int h = maximazing(newHgs, profunditat - 1, alpha, beta);
                        if (h < minEval) {
                            minEval = h;
                            if (minEval < beta) {
                                beta = minEval;
                            }
                            if (h < beta) {
                                beta = h;

                            }
                            if (beta < alpha) {
                                break;
                            }
                        }
                    }
                }

                if (beta <= alpha) {
                    break;
                }

            }
        }

        return minEval;
    }

    /**
     * Funció de suport per a Minimax: maximització dels valors heurístics.
     *
     * @param hgs
     * @param profunditat Profunditat restant a explorar.
     * @param alpha Valor alfa per a la poda alfa-beta.
     * @param beta Valor beta per a la poda alfa-beta.
     * @return Valor heurístic màxim trobat.
     */
    public int maximazing(HexGameStatus hgs, int profunditat, int alpha, int beta) {
        int maxEval = Integer.MIN_VALUE;

        if (profunditat == 0) {
            int h = heuristica();
            return h;
        }

        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);

                if (newHgs.getPos(i, j) == 0) {

                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return Integer.MAX_VALUE;
                    } else {
                        int h = minimazing(newHgs, profunditat - 1, alpha, beta);
                        if (h > maxEval) {
                            maxEval = h;
                        }
                        if (h > alpha) {
                            alpha = h;

                        }
                        if (alpha > beta) {
                            break;
                        }
                    }

                }

            }

        }
        return maxEval;
    }

    @Override
    public PlayerMove move(HexGameStatus hgs) {
        hgs.getMoves();
        PlayerMove jugada = minimax(hgs, 3);
        return jugada;
        //PlayerMove bb = new PlayerMove(new);
    }

    @Override
    public void timeout() {
        //S'ha de canviar un boolea

    }

    @Override
    public String getName() {
        return("bondia");   
    }

}
