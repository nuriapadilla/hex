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
 * Implementació de l'algorisme A* (A estrella) per trobar el camí més curt en un tauler de Hex.
 * Aquest algorisme utilitza una combinació de distància acumulada i una heurística basada
 * en el cost per prioritzar la cerca dels camins més prometedors.
 * 
 * @author bruna
 */
public class AEstrella {

    private final int mida; // La mida del tauler de joc.
    MyStatus hgs; // L'estat actual del joc, representat per un objecte MyStatus.
    Veins vivi; // Gestor dels veïns d'un node, incloent-hi els camins virtuals.

    /**
     * Constructor de la classe AEstrella.
     * 
     * @param mida la mida del tauler.
     * @param hgs l'estat actual del joc.
     */
    public AEstrella(int mida, MyStatus hgs) {
        this.mida = mida;
        this.hgs = hgs;
        vivi = new Veins(hgs);
    }

    /**
     * Calcula el cost heurístic d'un punt per avaluar la seva proximitat a l'objectiu.
     * Aquest cost es calcula en funció de si el camí es mou d'esquerra a dreta
     * o de dalt a baix.
     * 
     * @param P el punt que s'està avaluant.
     * @param nIni el node inicial.
     * @param nFi el node final.
     * @return el cost heurístic del punt.
     */
    private float preu(Point P, NodeEstrella nIni, NodeEstrella nFi) {
        NodeEstrella left = new NodeEstrella("L", 0, 0, 0);
        NodeEstrella right = new NodeEstrella("R", Integer.MAX_VALUE, 0, 0);
        NodeEstrella up = new NodeEstrella("U", 0, 0, 0);
        NodeEstrella down = new NodeEstrella("D", Integer.MAX_VALUE, 0, 0);
        float c;
        if (nIni.equals(left) && nFi.equals(right)) {
            c = (mida - P.x) / 11.0f;  // Cost basat en la distància horitzontal.
        } else {
            c = (mida - P.y) / 11.0f;  // Cost basat en la distància vertical.
        }
        return c;
    }

    /**
     * Determina el costat del tauler al qual pertany un punt donat.
     * 
     * @param p el punt a avaluar.
     * @return una cadena que indica el costat: "R" (dreta), "D" (baix) o "N" (cap).
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
     * Calcula el camí més curt entre dos nodes utilitzant l'algorisme A*.
     * Prioritza nodes amb costos acumulats més baixos i camins més curts.
     * 
     * @param nIni el node inicial.
     * @param nFi el node final.
     * @param p el color del jugador (1 o -1) per determinar les peces pròpies.
     * @return el pes del camí més curt, tenint en compte la distància i els camins virtuals.
     */
    public double camiMesCurt(NodeEstrella nIni, NodeEstrella nFi, int p) {
        PriorityQueue<NodeEstrella> pq = new PriorityQueue<>(new ComparadorNodeEstrella());
        boolean[][] visitats = new boolean[11][11];
        Point pp;

        switch (nIni.corner) {
            case "L":
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(0, i) == p) {
                        pp = new Point(0, i);
                        pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), 0));
                    } else if (hgs.getPos(0, i) == 0) {
                        pp = new Point(0, i);
                        pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), 1));
                    }
                    if (i - 1 >= 0 && (hgs.getPos(0, i - 1) == 0) && (hgs.getPos(0, i) == 0)) {
                        if (hgs.getPos(1, i - 1) == 0) {
                            pp = new Point(1, i - 1);
                            pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), 0));
                        } else if (hgs.getPos(1, i - 1) == p) {
                            pp = new Point(1, i - 1);
                            pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), 1));
                        }
                    }
                }
                break;
            case "U":
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(i, 0) == p) {
                        pp = new Point(i, 0);
                        pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), 0));
                    } else if (hgs.getPos(i, 0) == 0) {
                        pp = new Point(i, 0);
                        pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), 1));
                    }
                    if (i - 1 >= 0 && (hgs.getPos(i - 1, 0) == 0) && (hgs.getPos(i, 0) == 0)) {
                        if (hgs.getPos(i - 1, 1) == 0) {
                            pp = new Point(i - 1, 1);
                            pq.add(new NodeEstrella(pp, 1, preu(pp, nIni, nFi), 0));
                        } else if (hgs.getPos(i - 1, 1) == p) {
                            pp = new Point(i - 1, 1);
                            pq.add(new NodeEstrella(pp, 0, preu(pp, nIni, nFi), 1));
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
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new NodeEstrella("R", actual.distance, 0, actual.virtualcount));
                            break;
                        case "D":
                            pq.add(new NodeEstrella("D", actual.distance, 0, actual.virtualcount));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new NodeEstrella(t, actual.distance + 0, preu(t, nIni, nFi), actual.virtualcount));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new NodeEstrella(t, actual.distance + 1, preu(t, nIni, nFi), actual.virtualcount));
                                }
                            }
                    }
                }

                v = vivi.caminsVirtuals(actual.point);
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new NodeEstrella("R", actual.distance, 0, actual.virtualcount + 1));
                            break;
                        case "D":
                            pq.add(new NodeEstrella("D", actual.distance, 0, actual.virtualcount + 1));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new NodeEstrella(t, actual.distance + 0, preu(t, nIni, nFi), actual.virtualcount + 1));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new NodeEstrella(t, actual.distance + 1, preu(t, nIni, nFi), actual.virtualcount + 1));
                                }
                            }
                    }
                }
            }
            actual = pq.poll();
        }

        if (actual == null) {
            System.out.println("ES nulllll");
        }

        double pes = (double) actual.virtualcount / (mida * mida);
        pes += (double) actual.distance;
        return pes;
    }
}
