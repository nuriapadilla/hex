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
 *
 * @author nuria
 */
public class Dijkstra {

    public Dijkstra(int mida, MyStatus hgs) {
        this.mida = mida;
        this.hgs = hgs;
        vivi = new Veins(hgs);
    }

    private final int mida;
    HexGameStatus hgs;
    Veins vivi;

    private String costat(Point p) {
        if (p.x == mida) {
            return "R";
        }
        if (p.y == mida) {
            return "D";
        }
        return "N";
    }

    public double camiMesCurt(Node nIni, Node nFi, int p) {
        // Hem decidit tenir els nodes en una PriorityQueue 
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new ComparadorNode());

        boolean[][] visitats = new boolean[11][11];
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
                            pq.add(new Node("R", actual.distance, actual.virutalcount));
                            break;
                        case "D":
                            pq.add(new Node("D", actual.distance, actual.virutalcount));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new Node(t, actual.distance + 0, actual.virutalcount));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new Node(t, actual.distance + 1, actual.virutalcount));
                                }
                            }
                    }
                }
                v = vivi.caminsVirtuals(actual.point);
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new Node("R", actual.distance, actual.virutalcount+1));
                            break;
                        case "D":
                            pq.add(new Node("D", actual.distance, actual.virutalcount+1));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new Node(t, actual.distance + 0, actual.virutalcount+1));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new Node(t, actual.distance + 1, actual.virutalcount+1));
                                }
                            }
                    }
                }
            }
            actual = pq.poll();
        }
        double pes = (double) actual.virutalcount / (mida * mida);
        return pes;
    }
}
