/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import edu.upc.epsevg.prop.hex.players.Heuristica.Dijkstra;
import edu.upc.epsevg.prop.hex.players.Heuristica.Node;
import edu.upc.epsevg.prop.hex.HexGameStatus;
import edu.upc.epsevg.prop.hex.IAuto;
import edu.upc.epsevg.prop.hex.IPlayer;
import edu.upc.epsevg.prop.hex.PlayerMove;
import edu.upc.epsevg.prop.hex.SearchType;
import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author nuria
 */
public class Jugador1 implements IPlayer, IAuto {

    boolean id;
    int profMax;
    boolean fi = false;
    PlayerMove millorJugada;
    long nodesExplored = 0;
    int player;
    int mida;
    HashMap<MyStatus, Point> map;
    public Jugador1(boolean i, int p, boolean j) {
        id = i;
        profMax = p;
        map = new HashMap<>();
    }

    public double heuristica(MyStatus hgs) {
        nodesExplored = nodesExplored + 1;

        double cami1;
        double cami2;
        Node left = new Node("L", 0, null);
        Node right = new Node("R", Integer.MAX_VALUE, null);
        Node up = new Node("U", 0, null);
        Node down = new Node("D", Integer.MAX_VALUE, null);
        //System.out.println(hgs.toString());
        Dijkstra di = new Dijkstra(mida, hgs);
        if (player == 1) {
            cami2 = di.camiMesCurt(left, right, 1);
            cami1 = di.camiMesCurt(up, down, -1);
        } else {
            cami1 = di.camiMesCurt(left, right, 1);
            cami2 = di.camiMesCurt(up, down, -1);
        }
        return cami1 - cami2;
        //return 0;
    }

    /**
     * Implementa l'algorisme Minimax per calcular el millor moviment
     * disponible.
     *
     * @param hgs
     * @param profunditat Profunditat restant a explorar.
     * @return Columna òptima per al moviment.
     */
    public PlayerMove minimax(MyStatus hgs, int profunditat) { // LA PRINCIPAL
        if (fi) {
            return null;
        }
        double alpha = -Double.MAX_VALUE;
        double beta = Double.MAX_VALUE;
        PlayerMove jugada = null;
        boolean primer = true;
        double maxEval = -Double.MAX_VALUE;
        SearchType search = id == true ? SearchType.MINIMAX_IDS : SearchType.MINIMAX;
        Point primerajugada = null;
        // Si tenim millor jugada previa, prioritzem exprorar-la
        if (map.containsKey(hgs) && map.get(hgs)!=null) {
            Point punt = map.get(hgs);
            primerajugada = punt;
            MyStatus newHgs = new MyStatus((MyStatus)hgs);
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                if (newHgs.isGameOver()) {
                    return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                } else {
                    double h = minimazing(newHgs, profunditat - 1, alpha, beta);
                    if (h > maxEval || primer) {
                        maxEval = h;
                        primer = false;
                        jugada = new PlayerMove(punt, nodesExplored, profunditat, search);
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
                MyStatus newHgs = new MyStatus((MyStatus)hgs);
                Point punt = new Point(i, j);
                if (!(primerajugada != null && primerajugada.equals(punt))) {
                    if (newHgs.getPos(i, j) == 0) {
                        newHgs.placeStone(punt);
                        //map.put(newHgs, punt);
                        if (newHgs.isGameOver()) {
                            return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                        } else {
                            double h = minimazing(newHgs, profunditat - 1, alpha, beta);
                            if (h > maxEval || primer) {
                                primer = false;
                                maxEval = h;
                                jugada = new PlayerMove(punt, nodesExplored, profunditat, search);
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
        }
        map.put(hgs, jugada.getPoint());
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
    public double minimazing(MyStatus hgs, int profunditat, double alpha, double beta) {
        double minEval = Double.MAX_VALUE;
        if (fi) {
            return minEval; // retorno qualsevol cosa 
        }
        if (profunditat == 0) {
            double h = heuristica(hgs);
            return h;
        }
        Point p = null;
        Point primerajugada = null;
        Point punt;
        MyStatus newHgs;
        if (map.containsKey(hgs) && map.get(hgs)!=null ) {
            punt = map.get(hgs);
            primerajugada = punt;
            newHgs = new MyStatus((MyStatus)hgs);
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                //System.out.println("LA m");
                if (newHgs.isGameOver()) {
                    map.put(hgs,punt);
                    return -Double.MAX_VALUE;
                } else {
                    double h = maximazing(newHgs, profunditat - 1, alpha, beta);
                    if (h < minEval) {
                        minEval = h;
                        p = punt;
                        if (minEval < beta) {
                            beta = minEval;
                        }
                        if (h < beta) {
                            beta = h;
                        }
                        if (beta < alpha) {
                            return minEval;
                        }
                    }
                }
            }
}
            for (int i = 0; i < hgs.getSize(); i++) {
                for (int j = 0; j < hgs.getSize(); j++) {
                    if (fi) {
                        return Double.MAX_VALUE; // retorno qualsevol cosa
                    }
                    newHgs = new MyStatus((MyStatus)hgs);
                    punt = new Point(i, j);
                    if (!(primerajugada != null && primerajugada.equals(punt))) {
                        if (newHgs.getPos(i, j) == 0) {
                            newHgs.placeStone(punt);
                            if (newHgs.isGameOver()) {
                                //System.out.println("perdo");
                                map.put(hgs, punt);
                                return -Double.MAX_VALUE;
                            } else {
                                double h = maximazing(newHgs, profunditat - 1, alpha, beta);
                                if (h < minEval) {
                                    minEval = h;
                                    p = punt;
                                    if (minEval < beta) {
                                        beta = minEval;
                                    }
                                    if (h < beta) {
                                        beta = h;
                                    }
                                    if (beta < alpha) {
                                        //             System.out.println("Poda");
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
            }
        
        map.put(hgs, p);
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
    public double maximazing(MyStatus hgs, int profunditat, double alpha, double beta) {
        double maxEval = Double.MIN_VALUE;
        if (fi) {
            return maxEval; // retorno qualsevol cosa
        }
        if (profunditat == 0) {
            double h = heuristica(hgs);
            return h;
        }
        MyStatus newHgs;
        Point p = null;
        Point punt;
        Point primerajugada = null;
        if (map.containsKey(hgs) && map.get(hgs)!=null) {
            punt = map.get(hgs);
            primerajugada = punt;
            newHgs = new MyStatus((MyStatus)hgs);
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                if (newHgs.isGameOver()) {
                    return Double.MAX_VALUE;
                } else {
                    double h = minimazing(newHgs, profunditat - 1, alpha, beta);
                    if (h > maxEval) {
                        maxEval = h;
                        p=punt;
                    }
                    if (h > alpha) {
                        alpha = h;
                    }
                    if (alpha > beta) {
                        return maxEval;
                    }
                }
            }
}
            for (int i = 0; i < hgs.getSize(); i++) {
                for (int j = 0; j < hgs.getSize(); j++) {
                    if (fi) {
                        return maxEval; // retorno qualsevol cosa
                    }
                    newHgs = new MyStatus((MyStatus)hgs);
                    punt = new Point(i, j);
                    if (newHgs.getPos(i, j) == 0) {
                        newHgs.placeStone(punt);
                        if (newHgs.isGameOver()) {
                            return Double.MAX_VALUE;
                        } else {
                            double h = minimazing(newHgs, profunditat - 1, alpha, beta);
                            if (h > maxEval) {
                                maxEval = h;
                                p=punt;
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
        
        map.put(hgs, p);
        return maxEval;
    }

    @Override
    public PlayerMove move(HexGameStatus hgs
    ) {
        player = hgs.getCurrentPlayerColor();
        mida = hgs.getSize();
        nodesExplored = 0;
        if (id) {
            int prof = 1;
            while (!fi) {
                nodesExplored = 0;
                MyStatus mhgs = new MyStatus(hgs);
                PlayerMove newJugada = minimax(mhgs, prof);
                if (!fi) {
                    millorJugada = newJugada;
                }
                prof++;
            }
            fi = false;
        } else {
            MyStatus mhgs = new MyStatus(hgs);
            millorJugada = minimax(mhgs, profMax);
        }
        return millorJugada;
        //PlayerMove bb = new PlayerMove(new);
    }

    @Override
    public void timeout() {
        if (id) {
            fi = true;
        }
    }

    @Override
    public String getName() {
        return ("Jugador 1");
    }
}
