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
import java.util.HashMap;

/**
 *
 * @author bruna
 */
public class JugadorEstrella implements IPlayer, IAuto {
    
    boolean id;
    int profMax;
    boolean fi = false;
    PlayerMove millorJugada;
    long nodesExplored = 0;
    int player;
    int mida;
    HashMap<MyStatus, Point> map;
    HashMap<MyStatus, Integer> hmap;
    public JugadorEstrella(boolean i, int p) {
        id = i;
        profMax = p;
        map = new HashMap<>();
        hmap = new HashMap<>();
    }

    public int heuristica(MyStatus hgs) {
        nodesExplored = nodesExplored + 1;
        if(hmap.containsKey(hgs)) return hmap.get(hgs);
        int cami1;
        int cami2;
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
        hmap.put(hgs, cami1-cami2);
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
        //System.out.println("Estic al minimax");
        if (fi) {
            return null;
        }
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        PlayerMove jugada = null;
        boolean primer = true;
        int maxEval = Integer.MIN_VALUE;
        SearchType search = id == true ? SearchType.MINIMAX_IDS : SearchType.MINIMAX;
        Point primerajugada = null;
        // Si tenim millor jiugada previa, prioritzem exprorar-la
        if (map.containsKey(hgs)) {
            //System.out.println("Millor jugada");
            Point punt = map.get(hgs);
            primerajugada = punt;
            //System.out.println(punt);
            MyStatus newHgs = new MyStatus(hgs);
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                //System.out.println("LA m");
                if (newHgs.isGameOver()) {
                    
                    return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                } else {
                    int h = minimazing(newHgs, profunditat - 1, alpha, beta);
                    if (h > maxEval || primer) {
                        maxEval = h;
                        primer = false;
                        jugada = new PlayerMove(punt, nodesExplored, profunditat, search);
                    }
                    if (h > alpha) {
                        alpha = h;
                    }
                    if (alpha > beta) {
                        // System.out.println("Poda");
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
                // System.out.println("Evaluo la posició: " + i +" "+ j);
                MyStatus newHgs = new MyStatus(hgs);
                Point punt = new Point(i, j);
                if (!(primerajugada != null && primerajugada.equals(punt))) {
                    if (newHgs.getPos(i, j) == 0) {
                        newHgs.placeStone(punt);
                        //map.put(newHgs, punt);
                        if (newHgs.isGameOver()) {
                            return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                        } else {
                            int h = minimazing(newHgs, profunditat - 1, alpha, beta);
                            //System.out.println("Per la columna: " + i + " tenim heuristica: " + h);
                            if (h > maxEval || primer) {
                                // System.out.println("Aquesta es millor");
                                primer = false;
                                maxEval = h;
                                jugada = new PlayerMove(punt, nodesExplored, profunditat, search);
                            }
                            if (h > alpha) {
                                alpha = h;
                            }
                            if (alpha > beta) {
                                //       System.out.println("Poda");
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (maxEval == Integer.MIN_VALUE) {
            //System.out.println("Perdo 100%");
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
    public int minimazing(MyStatus hgs, int profunditat, int alpha, int beta) {
        int minEval = Integer.MAX_VALUE;
        if (fi) {
            return minEval; // retorno qualsevol cosa 
        }
        if (profunditat == 0) {
            int h = heuristica(hgs);
            return h;
        }
        Point p = null;
        Point primerajugada = null;
        Point punt;
        MyStatus newHgs;
        if (map.containsKey(hgs)) {
            punt = map.get(hgs);
            primerajugada = punt;
            newHgs = new MyStatus(hgs);
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                //System.out.println("LA m");
                if (newHgs.isGameOver()) {
                    map.put(hgs,punt);
                    return Integer.MIN_VALUE;
                } else {
                    int h = maximazing(newHgs, profunditat - 1, alpha, beta);
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
                            return minEval;
                        }
                    }
                }
            }
}
            for (int i = 0; i < hgs.getSize(); i++) {
                for (int j = 0; j < hgs.getSize(); j++) {
                    if (fi) {
                        return Integer.MAX_VALUE; // retorno qualsevol cosa
                    }
                    newHgs = new MyStatus(hgs);
                    punt = new Point(i, j);
                    if (!(primerajugada != null && primerajugada.equals(punt))) {
                        if (newHgs.getPos(i, j) == 0) {
                            newHgs.placeStone(punt);
                            if (newHgs.isGameOver()) {
                                //System.out.println("perdo");
                                map.put(hgs, punt);
                                return Integer.MIN_VALUE;
                            } else {
                                int h = maximazing(newHgs, profunditat - 1, alpha, beta);
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
        
        if(p==null){
            System.out.println("p es null");
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
    public int maximazing(MyStatus hgs, int profunditat, int alpha, int beta) {
        int maxEval = Integer.MIN_VALUE;
        if (fi) {
            return maxEval; // retorno qualsevol cosa
        }
        if (profunditat == 0) {
            int h = heuristica(hgs);
            return h;
        }
        MyStatus newHgs;
        Point p = null;
        Point punt;
        Point primerajugada = null;
        if (map.containsKey(hgs)) {
            punt = map.get(hgs);
            primerajugada = punt;
            newHgs = new MyStatus(hgs);
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                //System.out.println("LA m");
                if (newHgs.isGameOver()) {
                    return Integer.MAX_VALUE;
                } else {
                    int h = minimazing(newHgs, profunditat - 1, alpha, beta);
                    if (h > maxEval) {
                        maxEval = h;
                        p=punt;
                    }
                    if (h > alpha) {
                        alpha = h;
                    }
                    if (alpha > beta) {
                        //           System.out.println("Poda");
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
                    newHgs = new MyStatus(hgs);
                    punt = new Point(i, j);
                    if (newHgs.getPos(i, j) == 0) {
                        newHgs.placeStone(punt);
                        if (newHgs.isGameOver()) {
                            //System.out.println("guanyo");
                            return Integer.MAX_VALUE;
                        } else {
                            int h = minimazing(newHgs, profunditat - 1, alpha, beta);
                            if (h > maxEval) {
                                maxEval = h;
                                p=punt;
                            }
                            if (h > alpha) {
                                alpha = h;
                            }
                            if (alpha > beta) {
                                //           System.out.println("Poda");
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
        //   System.out.println(hgs.getPos(1, 0));
        if (id) {
            int prof = 1;
            while (!fi) {
                nodesExplored = 0;
                //System.out.println("Miro la prof: " + prof);
                // TODO: si ja veiem que guanyem amb tot o perdem a tot fem un if guarro i tallem, no cal seguir amb més profunditat
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
        return ("bondia");
    }
}
