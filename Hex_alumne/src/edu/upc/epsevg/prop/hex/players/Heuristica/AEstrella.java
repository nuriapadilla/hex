/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.Heuristica.ComparadorNodeEstrella;
import edu.upc.epsevg.prop.hex.players.Heuristica.NodeEstrella;
import edu.upc.epsevg.prop.hex.HexGameStatus;
import edu.upc.epsevg.prop.hex.players.MyStatus;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 *
 * @author bruna
 */
public class AEstrella {

    public AEstrella(int mida, MyStatus hgs) {
        this.mida = mida;
        this.hgs = hgs;
        vivi = new Veins(hgs);
    }

    private final int mida;
    MyStatus hgs;
    Veins vivi; 

    private float preu(Point P, NodeEstrella nIni, NodeEstrella nFi) {
        // mirem si va d'esquerra a dreta; left right
        NodeEstrella left = new NodeEstrella("L", 0, 0, null);
        NodeEstrella right = new NodeEstrella("R", Integer.MAX_VALUE, 0, null);
        NodeEstrella up = new NodeEstrella("U", 0, 0, null);
        NodeEstrella down = new NodeEstrella("D", Integer.MAX_VALUE, 0, null);
        float c;
        if (nIni.equals(left) && nFi.equals(right)) {
            c = (mida - P.x) / 11.0f;  // Amb un ".0", el càlcul és en float
        } else {
            c = (mida - P.y) / 11.0f;
        }
        return c;

    }

    private String costat(Point p) {
        if (p.x == mida) {
            return "R";
        }
        if (p.y == mida) {
            return "D";
        }
        return "N";
    }
    public int camiMesCurt(NodeEstrella nIni, NodeEstrella nFi, int p) {
        // Hem decidit tenir els nodes en una PriorityQueue 
        //System.out.println("Estic cami mes curt i el meu node ini: " + nIni.corner + " node fi: " + nFi.corner);
        PriorityQueue<NodeEstrella> pq = new PriorityQueue<>(new ComparadorNodeEstrella());

        boolean[][] visitats = new boolean[11][11];
        // Primera iteració
        Point pp;

        switch (nIni.corner) {
            case "L":
                //  System.out.println("Left first");
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(0, i) == p) {
                        // System.out.println("esta ocupada "+ p);
                        //System.out.println("afegeixo: 0 " + i);
                        pp = new Point(0, i);
                        pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), nIni));
                    } else if (hgs.getPos(0, i) == 0) {
                        //System.out.println("esta buida " );
                        //System.out.println("afegeixo: 0 " + i);
                        pp = new Point(0, i);
                        pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), nIni));
                    }
                    if (i - 1 >= 0) {
                        if ((hgs.getPos(0, i - 1) == 0) && (hgs.getPos(0, i) == 0)) {
                            if ((hgs.getPos(1, i-1) == 0)) {
                                pp = new Point(1, i-1);
                                pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), nIni));
                            } else if (hgs.getPos(1, i-1) == p) {
                                pp = new Point(1, i-1);
                                pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), nIni));
                            }
                        }
                    }
                }
                break;
            case "U":
                // System.out.println("Up first");
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(i, 0) == p) {
                        pp = new Point(i, 0);
                        pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), nIni));
                    } else if (hgs.getPos(i, 0) == 0) {
                        pp = new Point(i, 0);
                        pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), nIni));
                    }
                    if (i - 1 >= 0) {
                        if ((hgs.getPos(i-1, 0) == 0) && (hgs.getPos(i, 0) == 0)) {
                            if ((hgs.getPos(i-1, 1) == 0)) {
                                pp = new Point(i-1, 1);
                                pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), nIni));
                            } else if (hgs.getPos(i-1, 1) == p) {
                                pp = new Point(i-1, 1);
                                pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), nIni));
                            }
                        }
                    }
                }
                break;
            default:
                System.out.println("Error en la crida de camiMesCurt");
        }
       
        NodeEstrella actual = pq.poll();
        while (actual != null && !(actual.esCantonada() && actual.corner.equals(nFi.corner))) {
            
            if (!actual.esCantonada() && !visitats[actual.point.x][actual.point.y]) {
                visitats[actual.point.x][actual.point.y] = true;
                Vector<Point> v = vivi.veins(actual.point);
                //Vector<Point> cv = caminsvirtuals(actual.point, p);
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new NodeEstrella("R", actual.distance, 0, actual));
                            break;
                        case "D":
                            pq.add(new NodeEstrella("D", actual.distance, 0, actual));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new NodeEstrella(t, actual.distance + 0, 0, actual));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new NodeEstrella(t, actual.distance + 1, 0, actual));
                                }
                            }
                    }
                }
            }
            actual = pq.poll();
        }
        if (actual == null) {
            //System.out.println("Imprimeixo el tauler");
            //System.out.println(hgs.toString());
            System.out.println("ES nulllll");
        }
        return actual.distance;
    }
}
