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
import java.util.PriorityQueue;

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
    long nodesExplored;
    int player;
    int mida;

    public Jugador1(boolean i, int p) {
        id = i;
        profMax = p;
    }

    public int heuristica(HexGameStatus hgs) {
        nodesExplored = nodesExplored + 1;
        int cami1;
        int cami2;
        Node left = new Node("L", 0, null);
        Node right = new Node("R", Integer.MAX_VALUE, null);
        Node up = new Node("U", 0, null);
        Node down = new Node("D", Integer.MAX_VALUE, null);
        //System.out.println(hgs.toString());
        if (player == 1) {
            cami1 = camiMesCurt(hgs, left, right, 1);
            cami2 = camiMesCurt(hgs, up, down, -1);
        } else {
            cami2 = camiMesCurt(hgs, left, right, -1);
            cami1 = camiMesCurt(hgs, up, down, 1);
        }
        return cami2 - cami1;
        //return 0;
    }

    int camiMesCurt(HexGameStatus hgs, Node nIni, Node nFi, int p) {
        // Problema: !!!! Pot utilitzar les cantonades per trobar el més curt aaaaaAAAA no pot ser això NOMES LA PRIMERAAAA
        // Hem decidit tenir els nodes en una PriorityQueue 
     //   System.out.println("Estic cami mes curt");
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new ComparadorNode()); // Preguntar Bernat!!!!!
        // Necessito tenir una llista de nodes visistats !!!
        boolean[][] visitats = new boolean[11][11];
        // Primera iteració
        switch(nIni.corner){
            case "L":
              //  System.out.println("Left first");
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(0, i) == p) {
                       // System.out.println("esta ocupada "+ p);
                        //System.out.println("afegeixo: 0 " + i);
                        pq.add(new Node(new Point(0, i), 0, nIni));
                    } else if (hgs.getPos(0, i) == 0) {
                        //System.out.println("esta buida " );
                        //System.out.println("afegeixo: 0 " + i);
                        pq.add(new Node(new Point(0, i), 1, nIni));
                    }
                }                
                break;
            case "U":
               // System.out.println("Up first");
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(i, 0) == p) {
                        pq.add(new Node(new Point(i, 0), 0, nIni));
                    } else if (hgs.getPos(i, 0) == 0) {
                        pq.add(new Node(new Point(i, 0), 1, nIni));
                    }
                }
                break;
            default:
                System.out.println("Error en la crida de camiMesCurt");
        }
        Node actual = pq.poll();
        while (actual != null && !actual.equals(nFi)) {
            if(!actual.esCantonada()){
                visitats[actual.point.x][actual.point.y] = true;
              //  System.out.println("Visito el punt: " + actual.point.x +"  "+ actual.point.y + " dist: "+ actual.distance);
                if(actual.point.x> 0 && !visitats[actual.point.x -1][actual.point.y]){
                    if (hgs.getPos(actual.point.x - 1, actual.point.y) == p) {
                //        System.out.println("afegeixo: " + (actual.point.x - 1) + " " + actual.point.y);
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x - 1, actual.point.y) == 0) {
                  //      System.out.println("afegeixo: " + (actual.point.x - 1) + " " + actual.point.y);
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y), actual.distance + 1, actual));
                    }
                }
                //System.out.println("El primer ok");
                
                if(actual.point.y>0 && !visitats[actual.point.x][actual.point.y -1]){
                    if (hgs.getPos(actual.point.x, actual.point.y - 1) == p) {
                        //System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y-1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y - 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x, actual.point.y-1) == 0) {
                        //System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y - 1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y - 1), actual.distance + 1, actual));
                    }
                }
                //System.out.println("Segon ok  "+ (actual.point.x+1)+ " "+ actual.point.y);
                if ((actual.point.x+1) < mida && !visitats[actual.point.x + 1][actual.point.y]) {
                    if (hgs.getPos(actual.point.x + 1, actual.point.y) == p) {
                  //      System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y ));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x + 1, actual.point.y) == 0) {
                    //    System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y), actual.distance + 1, actual));
                    }
                }
                //System.out.println("Tercer ok");
                if ((actual.point.y+1) < mida && !visitats[actual.point.x][actual.point.y+1]) {
                    if (hgs.getPos(actual.point.x, actual.point.y+1) == p) {
                       // System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y+1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x, actual.point.y+1) == 0) {
                       // System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y+1), actual.distance + 1, actual));
                    }
                }
                //System.out.println("Quart ok");
                if (actual.point.x > 0 && actual.point.y < mida-1 && !visitats[actual.point.x - 1][actual.point.y + 1]) {
                    if (hgs.getPos(actual.point.x - 1, actual.point.y + 1) == p) {
                  //      System.out.println("afegeixo: " + (actual.point.x-1) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y + 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x - 1, actual.point.y + 1) == 0) {
                    //    System.out.println("afegeixo: " + (actual.point.x-1) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y + 1), actual.distance + 1, actual));
                    }
                }
                //System.out.println("5 ok");
                if (actual.point.y > 0 && actual.point.x < mida-1 && !visitats[actual.point.x + 1][actual.point.y - 1]) {
                    if (hgs.getPos(actual.point.x + 1, actual.point.y - 1) == p) {
                  //      System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y - 1));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y - 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x + 1, actual.point.y -1) == 0) {
                    //    System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y - 1));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y - 1), actual.distance + 1, actual));
                    }
                }
               // System.out.println("6 ok");
                if (actual.point.x == mida-1){
                    pq.add(new Node("R", actual.distance, actual));
                }
                if (actual.point.y == mida-1){
                    pq.add(new Node("D", actual.distance, actual));
                }
            }
            actual = pq.poll();
        }
        return actual.distance;
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
        if (fi) {
            return null;
        }
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        PlayerMove jugada = null;
        boolean primer = true;
        int maxEval = Integer.MIN_VALUE;
        SearchType search = id == true ? SearchType.MINIMAX_IDS : SearchType.MINIMAX;
        // Si tenim millor jiugada previa, prioritzem exprorar-la
        if (iniMillorJugada) {
            HexGameStatus newHgs = new HexGameStatus(hgs);
            Point punt = millorJugada.getPoint();
            if (newHgs.getPos(punt.x, punt.y) == 0) {
                newHgs.placeStone(punt);
                //System.out.println("LA m");
                if (newHgs.isGameOver()) {
                    return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                } else {
                    int h = minimazing(newHgs, profunditat, alpha, beta);
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
                // System.out.println("Evaluo la posició: " + i +" "+ j);
                HexGameStatus newHgs = new HexGameStatus(hgs);
                Point punt = new Point(i, j);
                if (iniMillorJugada && (millorJugada.getPoint()).equals(punt)) {
                    // System.out.println("No entenc"+ millorJugada.getPoint().x);
                    break;
                }
                if (newHgs.getPos(i, j) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                    } else {
                        int h = minimazing(newHgs, profunditat, alpha, beta);
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
                           // System.out.println("Fins aqui!");
                            break;
                        }
                    }
                }
            }
        }
        if (maxEval == Integer.MIN_VALUE) {
            System.out.println("Perdo 100%");
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
        if (fi) {
            return minEval; // retorno qualsevol cosa 
        }
        if (profunditat == 0) {
            int h = heuristica(hgs);
            return h;
        }

        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                if (fi) {
                    return Integer.MAX_VALUE; // retorno qualsevol cosa
                }
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
        if (fi) {
            return maxEval; // retorno qualsevol cosa
        }
        if (profunditat == 0) {
            int h = heuristica(hgs);
            return h;
        }

        for (int i = 0; i < hgs.getSize(); i++) {
            for (int j = 0; j < hgs.getSize(); j++) {
                if (fi) {
                    return maxEval; // retorno qualsevol cosa
                }
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
        // hgs.getMoves(); // això perq ho fem ????????
        player = hgs.getCurrentPlayerColor();
        mida = hgs.getSize();
        nodesExplored = 0;
     //   System.out.println(hgs.getPos(1, 0));
        if (id) {
            int prof = 1;
            while (!fi) {
                nodesExplored = 0;
                System.out.println("Miro la prof: " + prof);
                // TODO: si ja veiem que guanyem amb tot o perdem a tot fem un if guarro i tallem, no cal seguir amb més profunditat
                PlayerMove newJugada = minimax(hgs, prof);
                if (!fi) {
                    millorJugada = newJugada;
                    iniMillorJugada = true;
                }
                prof++;
            }
            fi = false;
        } else {
            millorJugada = minimax(hgs, profMax);
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
