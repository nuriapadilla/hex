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

    boolean id;
    int profMax;
    boolean fi = false;
    boolean iniMillorJugada = false;
    PlayerMove millorJugada;


    public Jugador1(boolean i, int p){
        id = i;
        profMax = p;
    }
    public int heuristica() {
        return 0;
    }

    /**
     * Implementa l'algorisme Minimax per calcular el millor moviment
     * disponible.
     *
     * @param hgs
     * @param profunditat Profunditat restant a explorar.
     * @return Columna òptima per al moviment.
     */
    public PlayerMove minimax(HexGameStatus hgs, int profunditat) { // LA PRINCIPAL
        if(fi) return null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        PlayerMove jugada = null;
        boolean primer= true;
        int maxEval = Integer.MIN_VALUE;
      
        // Si tenim millor jugada previa, prioritzem exprorar-la
        if(iniMillorJugada){
            HexGameStatus newHgs = new HexGameStatus(hgs);
            Point punt = millorJugada.getPoint();
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                if (newHgs.isGameOver()) {
                    return new PlayerMove(punt, 0, profunditat, SearchType.MINIMAX); // canviar el 0 i la profunditat, esta malament
                } else {
                    int h = minimazing(newHgs, profunditat, alpha, beta);
                    if (h > maxEval || primer) {
                        maxEval = h;
                        primer = false;
                        jugada = new PlayerMove(punt, 0, profunditat, SearchType.MINIMAX);
                    }
                    if (h > alpha) {
                        alpha = h;
                    }
                    if (alpha > beta) {
                        return jugada;
                    }
                }
            }
        }
        
        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                if (fi) {
                    return null;
                }
                System.out.println("Evaluo la posició: " + i +" "+ j);
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);
                if(iniMillorJugada && (millorJugada.getPoint()).equals(punt)){
                    System.out.println("No entenc"+ millorJugada.getPoint().x);
                    break;
                }
                if (newHgs.getPos(i, j) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return new PlayerMove(punt, 0, profunditat, SearchType.MINIMAX); // canviar el 0 i la profunditat, esta malament
                    } else {
                        int h = minimazing(newHgs, profunditat, alpha, beta);
                        //System.out.println("Per la columna: " + i + " tenim heuristica: " + h);
                        if (h > maxEval || primer) {
                            System.out.println("Aquesta es millor");
                            primer = false;
                            maxEval = h;
                            jugada = new PlayerMove(punt, 0, profunditat, SearchType.MINIMAX);
                        }
                        if (h > alpha) {
                            alpha = h;
                        }
                        if (alpha > beta) {
                            System.out.println("Fins aqui!");
                            break;
                        }
                    }
                }
            }
        }
        if(maxEval == Integer.MIN_VALUE) System.out.println("Perdo 100%");
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
        if (fi) return minEval; // retorno qualsevol cosa 
        if (profunditat == 0) {
            int h = heuristica();
            return h;
        }

        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                if(fi) return Integer.MAX_VALUE; // retorno qualsevol cosa
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);
                if (newHgs.getPos(i, j) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        //System.out.println("perdo");
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
        if(fi) return maxEval; // retorno qualsevol cosa
        if (profunditat == 0) {
            int h = heuristica();
            return h;
        }

        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                if(fi) return maxEval; // retorno qualsevol cosa
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);
                if (newHgs.getPos(i, j) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        //System.out.println("guanyo");
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
        hgs.getMoves(); // això perq ho fem ????????
        if(id){
            int prof = 1;
            while(!fi){
                PlayerMove newJugada = minimax(hgs, prof);
                if(!fi){
                    millorJugada = newJugada;
                    iniMillorJugada = true;
                }
                prof++;
            }
        }else{
            millorJugada = minimax(hgs, profMax);
        }
        return millorJugada;
        //PlayerMove bb = new PlayerMove(new);
    }

    @Override
    public void timeout() {
        if(id) fi = true;
    }

    @Override
    public String getName() {
        return("bondia");   
    }

}
