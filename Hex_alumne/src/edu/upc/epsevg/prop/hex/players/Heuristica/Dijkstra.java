/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.Heuristica.ComparadorNode;
import edu.upc.epsevg.prop.hex.players.Heuristica.Node;
import edu.upc.epsevg.prop.hex.HexGameStatus;
import edu.upc.epsevg.prop.hex.players.MyStatus;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * La classe {@code Dijkstra} implementa l'algorisme de camí més curt
 * personalitzat per al joc Hex. Aquesta classe calcula el camí més curt entre
 * dos nodes del tauler, considerant també camins "virtuals" que representen
 * connexions potencials o jugades futures.
 *
 * Es basa en una implementació amb una cua de prioritat per gestionar els nodes
 * visitats i optimitzar l'exploració del tauler.
 *
 * @author nuria
 */
public class Dijkstra {

    private final int mida;
    HexGameStatus hgs;
    Veins vivi;
    /**
     * Constructor de la classe {@code Dijkstra}.
     *
     * @param mida la mida del tauler de joc.
     * @param hgs l'estat actual del tauler encapsulat en {@code MyStatus}.
     */
    public Dijkstra(int mida, MyStatus hgs) {
        this.mida = mida;
        this.hgs = hgs;
        vivi = new Veins(hgs);
    }

    /**
     * Determina el costat (R, D o N) del tauler on es troba un punt donat.
     *
     * @param p el punt a comprovar.
     * @return {@code "R"} si el punt està al costat dret, {@code "D"} si és a
     * la part inferior, o {@code "N"} si no està en cap costat específic.
     */
    private String costat(Point p) {
        if (p.x == mida) {
            return "R";
        }
        if (p.y == mida) {
            return "D";
        }
        return "N";
    }

    /**
     * Calcula el camí més curt entre dos nodes al tauler, considerant tant els
     * camins reals com els virtuals.
     *
     * @param nIni el node inicial, representat per un objecte {@code Node}.
     * @param nFi el node final, representat per un objecte {@code Node}.
     * @param p el color del jugador per al qual es calcula el camí.
     * @return el pes del camí més curt, que inclou tant distàncies físiques com
     * un corrector pels virtuals.
     */
    public double camiMesCurt(Node nIni, Node nFi, int p) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new ComparadorNode());
        boolean[][] visitats = new boolean[mida][mida];
        // Primera iteració
        switch (nIni.corner) {
            case "L":
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(0, i) == p) {
                        pq.add(new Node(new Point(0, i), 0, 0));
                    } else if (hgs.getPos(0, i) == 0) {
                        pq.add(new Node(new Point(0, i), 1, 0));
                    }
                    // Virtuals
                    if (i - 1 >= 0) {
                        if ((hgs.getPos(0, i - 1) == 0) && (hgs.getPos(0, i) == 0)) {
                            if ((hgs.getPos(1, i - 1) == 0)) {
                                Point pp = new Point(1, i - 1);
                                pq.add(new Node(pp, 1, 1));
                            } else if (hgs.getPos(1, i - 1) == p) {
                                Point pp = new Point(1, i - 1);
                                pq.add(new Node(pp, 0, 1));
                            }
                        }
                    }
                }
                break;
            case "U":
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(i, 0) == p) {
                        pq.add(new Node(new Point(i, 0), 0, 0));
                    } else if (hgs.getPos(i, 0) == 0) {
                        pq.add(new Node(new Point(i, 0), 1, 0));
                    }
                    // Virtuals
                    if (i - 1 >= 0) {
                        if ((hgs.getPos(i - 1, 0) == 0) && (hgs.getPos(i, 0) == 0)) {
                            if ((hgs.getPos(i - 1, 1) == 0)) {
                                Point pp = new Point(i - 1, 1);
                                pq.add(new Node(pp, 1, 1));
                            } else if (hgs.getPos(i - 1, 1) == p) {
                                Point pp = new Point(i - 1, 1);
                                pq.add(new Node(pp, 0, 1));
                            }
                        }
                    }
                }
                break;
            default:
                System.out.println("Error en la crida de camiMesCurt");
        }
        Node actual = pq.poll();
        while (actual != null && !(actual.esCantonada() && actual.corner.equals(nFi.corner))) {
            if (!actual.esCantonada() && !visitats[actual.point.x][actual.point.y]) {
                visitats[actual.point.x][actual.point.y] = true;
                Vector<Point> v = vivi.veins(actual.point);                
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new Node("R", actual.distance, actual.virtualcount));
                            break;
                        case "D":
                            pq.add(new Node("D", actual.distance, actual.virtualcount));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new Node(t, actual.distance + 0, actual.virtualcount));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new Node(t, actual.distance + 1, actual.virtualcount));
                                }
                            }
                    }
                }
                v = vivi.caminsVirtuals(actual.point);
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new Node("R", actual.distance, actual.virtualcount+1));
                            break;
                        case "D":
                            pq.add(new Node("D", actual.distance, actual.virtualcount+1));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new Node(t, actual.distance + 0, actual.virtualcount+1));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new Node(t, actual.distance + 1, actual.virtualcount+1));
                                }
                            }
                    }
                }
            }
            actual = pq.poll();
        }
        double pes = (double) actual.virtualcount / (mida * mida);
        pes = pes + (double) actual.distance;
        return pes;
    }
}
