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
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Classe implementa un jugador automàtic per al joc Hex, utilitzant algoritmes 
 * de cerca com Minimax amb poda Alpha-Beta i una heurística
 * basada en distàncies més curtes.
 * Aquesta classe pot operar en dos modes: Minimax simple i Minimax amb cerca iterativa
 * (Iterative Deepening Search, IDS).
 * 
 * @author bruna i nuria
 */
public class Jugador2 implements IPlayer, IAuto {

    boolean id;
    int profMax;
    boolean fi = false;
    PlayerMove millorJugada;
    long nodesExplored = 0;
    int player;
    int mida;
    int max;
    
    /**
     * Constructor de la classe Jugador2.
     *
     * @param i identifica si es fa un minmax o un IDS (true = IDS, false = minmax).
     * @param p la profunditat màxima per a les operacions d'exploració o cerca.
     * @param max el valor màxim permès a l'hora d'explorar branques en l'estratègia del jugador.
     */
    public Jugador2(boolean i, int p, int max) {
        id = i;
        profMax = p;
        this.max = max;
    }

    /**
     * Calcula el valor heurístic de l'estat actual del joc basat en les
     * distàncies més curtes entre nodes específics al tauler.
     *
     * @param hgs l'estat actual del tauler, representat per un objecte de tipus
     * MyStatus.
     * @return la diferència entre els camins més curts dels dos jugadors, que
     * indica l'avantatge estratègic del jugador actual.
     */
    public double heuristica(MyStatus hgs) {
        nodesExplored = nodesExplored + 1;
        double cami1;
        double cami2;
        Node left = new Node("L", 0, 0);
        Node right = new Node("R", Integer.MAX_VALUE, 0);
        Node up = new Node("U", 0, 0);
        Node down = new Node("D", Integer.MAX_VALUE, 0);
        Dijkstra di = new Dijkstra(mida, hgs);
        if (player == 1) {
            cami2 = di.camiMesCurt(left, right, 1);
            cami1 = di.camiMesCurt(up, down, -1);
        } else {
            cami1 = di.camiMesCurt(left, right, 1);
            cami2 = di.camiMesCurt(up, down, -1);
        }
        return cami1 - cami2;
    }

    /**
     * Aplica l'algorisme Minimax amb poda alpha-beta per determinar la millor
     * jugada per al jugador actual, basant-se en l'estat del tauler.
     *
     * @param hgs l'estat actual del tauler de joc, representat per un objecte
     * MyStatus.
     * @param profunditat la profunditat màxima d'exploració de l'arbre de
     * cerca.
     * @param map una estructura de memòria cau (HashMap) que associa estats del
     * tauler amb les millors jugades prèviament calculades per optimitzar la
     * cerca.
     * @return un objecte PlayerMove que conté la millor jugada trobada, el
     * nombre de nodes explorats, la profunditat utilitzada i el tipus de cerca.
     */
    public PlayerMove minimax(MyStatus hgs, int profunditat,  HashMap<MyStatus, Vector<Point>> map ) { // LA PRINCIPAL
        if (fi) {
            return null;
        }
        double alpha = -Double.MAX_VALUE;
        double beta = Double.MAX_VALUE;
        PlayerMove jugada = null;
        boolean primer = true;
        double maxEval = -Double.MAX_VALUE;
        PriorityQueue<Eval> pq = new PriorityQueue<>(Comparator.comparingDouble((Eval e) -> e.heur).reversed());
        Point punt;
        SearchType search = id == true ? SearchType.MINIMAX_IDS : SearchType.MINIMAX;
        if (map.containsKey(hgs)) {
            Vector<Point> punts = map.get(hgs);
            for (int i = 0; i < punts.size(); i++) {
                punt = punts.get(i);
                MyStatus newHgs = new MyStatus(hgs);
                if (newHgs.getPos(punt) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return new PlayerMove(punt, nodesExplored, profunditat, search);
                    } else {
                        double h = minimazing(newHgs, profunditat - 1, alpha, beta, map);
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

        } else {

            for (int i = 0; i < hgs.getSize(); i++) {
                for (int j = 0; j < hgs.getSize(); j++) {
                    if (fi) {
                        return null;
                    }
                    MyStatus newHgs = new MyStatus(hgs);
                    punt = new Point(i, j);
                    if (newHgs.getPos(i, j) == 0) {
                        newHgs.placeStone(punt);
                        if (newHgs.isGameOver()) {
                            return new PlayerMove(punt, nodesExplored, profunditat, search); // canviar el 0 i la profunditat, esta malament
                        } else {
                            double h = minimazing(newHgs, profunditat - 1, alpha, beta, map);
                            pq.add(new Eval(punt, h));
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
            map.put(hgs, getmaxfirst(pq));
        }
        return jugada;
    }
    
    /**
     * Obté els punts amb les millors puntuacions de la cua de prioritats, fins
     * a un màxim especificat.
     *
     * @param pq una cua de prioritats (PriorityQueue) que conté objectes de
     * tipus Eval, ordenats segons el seu valor heurístic.
     * @return un vector de punts (Vector<Point>) que representa les millors
     * jugades seleccionades de la cua, fins al límit especificat per `max`.
     */
    Vector<Point> getmaxfirst(PriorityQueue<Eval> pq) {
        Vector<Point> sol = new Vector<>();
        for (int i = 0; i < max && pq.peek() != null; i++) {
            sol.add(pq.poll().punt);
        }
        return sol;
    }

    /**
     * Calcula el valor mínim (minimització) per a un estat del joc mitjançant
     * l'algorisme Minimax amb poda alpha-beta. Aquesta funció s'utilitza per
     * simular les jugades del contrari i determinar la millor estratègia
     * possible per al jugador actual.
     *
     * @param hgs l'estat actual del tauler, representat per un objecte
     * MyStatus.
     * @param profunditat la profunditat restant d'exploració de l'arbre de
     * decisions.
     * @param alpha el millor valor ja conegut per al jugador maximitzador (poda
     * alpha).
     * @param beta el millor valor ja conegut per al jugador minimitzador (poda
     * beta).
     * @param map una memòria cau (HashMap) que emmagatzema els millors
     * moviments associats a estats prèviament calculats per optimitzar la
     * cerca.
     * @return el valor heurístic mínim per al jugador actual en aquest estat
     * del joc.
     */
    public double minimazing(MyStatus hgs, int profunditat, double alpha, double beta,  HashMap<MyStatus, Vector<Point>> map ) {
        double minEval = -Double.MAX_VALUE;
        PriorityQueue<Eval> pq = new PriorityQueue<>(Comparator.comparingDouble((Eval e) -> e.heur));
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
        if (map.containsKey(hgs)) {
            Vector<Point> punts = map.get(hgs);
            for (int i = 0; i < punts.size(); i++) {
                punt = punts.get(i);
                newHgs = new MyStatus(hgs);
                if (newHgs.getPos(punt.x, punt.y) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return -Double.MAX_VALUE;
                    } else {
                        double h = maximazing(newHgs, profunditat - 1, alpha, beta, map);
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
        } else {
            for (int i = 0; i < hgs.getSize(); i++) {
                for (int j = 0; j < hgs.getSize(); j++) {
                    if (fi) {
                        return Double.MAX_VALUE; // retorno qualsevol cosa
                    }
                    newHgs = new MyStatus(hgs);
                    punt = new Point(i, j);
                    if (newHgs.getPos(i, j) == 0) {
                        newHgs.placeStone(punt);
                        if (newHgs.isGameOver()) {
                            return -Double.MAX_VALUE;
                        } else {
                            double h = maximazing(newHgs, profunditat - 1, alpha, beta, map);
                            pq.add(new Eval(punt, h));
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
                                    break;
                                }
                            }
                        }
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
                map.put(hgs, getmaxfirst(pq));
            }

        }

        return minEval;
    }

    /**
     * Calcula el valor màxim (maximització) per a un estat del joc mitjançant
     * l'algorisme Minimax amb poda alpha-beta. Aquesta funció representa les
     * jugades òptimes per al jugador actual.
     *
     * @param hgs l'estat actual del tauler, representat per un objecte
     * MyStatus.
     * @param profunditat la profunditat restant d'exploració de l'arbre de
     * decisions.
     * @param alpha el millor valor ja conegut per al jugador maximitzador (poda
     * alpha).
     * @param beta el millor valor ja conegut per al jugador minimitzador (poda
     * beta).
     * @param map una memòria cau (HashMap) que emmagatzema els millors
     * moviments associats a estats prèviament calculats per optimitzar la
     * cerca.
     * @return el valor heurístic màxim per al jugador actual en aquest estat
     * del joc.
     */
    public double maximazing(MyStatus hgs, int profunditat, double alpha, double beta,  HashMap<MyStatus, Vector<Point>> map ) {
        double maxEval = -Double.MAX_VALUE;
        PriorityQueue<Eval> pq = new PriorityQueue<>(Comparator.comparingDouble((Eval e) -> e.heur).reversed());
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
        if (map.containsKey(hgs)) {
            Vector<Point> punts = map.get(hgs);
            for (int i = 0; i < punts.size(); i++) {
                punt = punts.get(i);
                newHgs = new MyStatus(hgs);
                if (newHgs.getPos(punt.x, punt.y) == 0) {
                    newHgs.placeStone(punt);
                    if (newHgs.isGameOver()) {
                        return Double.MAX_VALUE;
                    } else {
                        double h = minimazing(newHgs, profunditat - 1, alpha, beta, map);
                        if (h > maxEval) {
                            maxEval = h;
                            p = punt;
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
        } else {
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
                            return Double.MAX_VALUE;
                        } else {
                            double h = minimazing(newHgs, profunditat - 1, alpha, beta, map);
                            pq.add(new Eval(punt, h));
                            if (h > maxEval) {
                                maxEval = h;
                                p = punt;
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
            map.put(hgs, getmaxfirst(pq));
        }
        return maxEval;
    }

    @Override
    public PlayerMove move(HexGameStatus hgs) {
        HashMap<MyStatus, Vector<Point>> map = new HashMap<>(); ;
        player = hgs.getCurrentPlayerColor();
        mida = hgs.getSize();
        nodesExplored = 0;
        if (id) {
            int prof = 1;
            while (!fi) {
                nodesExplored = 0;
                MyStatus mhgs = new MyStatus(hgs);
                PlayerMove newJugada = minimax(mhgs, prof, map);
                if (!fi) {
                    millorJugada = newJugada;
                }
                prof++;
            }
            fi = false;
        } else {
            MyStatus mhgs = new MyStatus(hgs);
            millorJugada = minimax(mhgs, profMax, map);
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
        return ("Jugador 2");
    }

}
