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

    public Dijkstra(int mida, MyStatus hgs, boolean i) {
        this.mida = mida;
        this.hgs = hgs;
        virt = i;
    }

    private final int mida;
    HexGameStatus hgs;
    boolean virt;

    private String costat(Point p) {
        if (p.x == mida) {
            return "R";
        }
        if (p.y == mida) {
            return "D";
        }
        return "N";
    }

    private Vector<Point> veins(Point p) {
        Vector<Point> sol = new Vector<>();
        if (p.x > 0) {
            sol.add(new Point(p.x - 1, p.y));
        }
        if (p.y > 0) {
            sol.add(new Point(p.x, p.y - 1));
        }
        sol.add(new Point(p.x + 1, p.y));
        sol.add(new Point(p.x, p.y + 1));
        if (p.x > 0) {
            sol.add(new Point(p.x - 1, p.y + 1));
        }
        if (p.y > 0) {
            sol.add(new Point(p.x + 1, p.y - 1));
        }
        // Camins virtuals

        return sol;
    }

    private Vector<Point> caminsvirtuals(Point p, int player) {
        Vector<Point> sol = new Vector<>();
        // (x+1, y-2)---- (x, y-1) // (x+1, y-1)
        // (x+2, y-1) ----(x+1, y-1) // (x+1, y)
        // (x+1, y+1) ----(x+1, y) // (x, y+1)
        // (x-1, y+2) ---- (x, y+1) // (x-1, y+1)
        // (x-2, y+1) ----- (x-1, y) // (x-1, y+1)
        // (x-1, y-1) -----(x, y-1) // (x-1, y)
        if (p.x + 1 < mida && p.y - 2 >= 0 && hgs.getPos(p.x, p.y - 1) == 0 && hgs.getPos(p.x + 1, p.y - 1) == 0) {
            sol.add(new Point(p.x + 1, p.y - 2));
        }
        if (p.x + 2 <= mida && p.y - 1 >= 0 && hgs.getPos(p.x + 1, p.y - 1) == 0 && hgs.getPos(p.x + 1, p.y) == 0) {
            sol.add(new Point(p.x + 2, p.y - 1));
        }
        if (p.x + 1 < mida && p.y + 1 < mida && hgs.getPos(p.x + 1, p.y) == 0 && hgs.getPos(p.x, p.y + 1) == 0) {
            sol.add(new Point(p.x + 1, p.y + 1));
        }
        if (p.x - 1 >= 0 && p.y + 2 <= mida && hgs.getPos(p.x, p.y + 1) == 0 && hgs.getPos(p.x - 1, p.y + 1) == 0) {
            sol.add(new Point(p.x - 1, p.y + 2));
        }
        if (p.x - 2 >= 0 && p.y + 1 < mida && hgs.getPos(p.x - 1, p.y) == 0 && hgs.getPos(p.x - 1, p.y + 1) == 0) {
            sol.add(new Point(p.x - 2, p.y + 1));
        }
        if (p.x - 1 >= 0 && p.y - 1 >= 0 && hgs.getPos(p.x, p.y - 1) == 0 && hgs.getPos(p.x - 1, p.y) == 0) {
            sol.add(new Point(p.x - 1, p.y - 1));
        }
        return sol;
    }

    public int camiMesCurt(Node nIni, Node nFi, int p) {
        // Hem decidit tenir els nodes en una PriorityQueue 
        //System.out.println("Estic cami mes curt i el meu node ini: " + nIni.corner + " node fi: " + nFi.corner);
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new ComparadorNode());

        boolean[][] visitats = new boolean[11][11];
        // Primera iteració
        switch (nIni.corner) {
            case "L":
                //  System.out.println("Left first");
                for (int i = 0; i < mida; i++) {
                    if (hgs.getPos(0, i) == p) {
                        // System.out.println("esta ocupada "+ p);
                        //System.out.println("afegeixo: 0 " + i);
                        pq.add(new Node(new Point(0, i), 0, nIni));
                        // podria afegir virtuals ?
                    } else if (hgs.getPos(0, i) == 0) {
                        //System.out.println("esta buida " );
                        //System.out.println("afegeixo: 0 " + i);
                        pq.add(new Node(new Point(0, i), 1, nIni));
                        // podria afegir virtuals ?
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
        /*
              for (Node element : pq) {
            if (element.esCantonada()) {
                System.out.println(element.corner);
            } else {
                System.out.println(element.point + " " + element.distance);

            }
        }
    
        System.out.println("---------------------------------------");
        */
        Node actual = pq.poll();
        while (actual != null && !(actual.esCantonada() && actual.corner.equals(nFi.corner))) {

            if (!actual.esCantonada() && !visitats[actual.point.x][actual.point.y]) {
                visitats[actual.point.x][actual.point.y] = true;
                Vector<Point> v = veins(actual.point);
                //Vector<Point> cv = caminsvirtuals(actual.point, p);
                for (Point t : v) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new Node("R", actual.distance, actual));
                            break;
                        case "D":
                            pq.add(new Node("D", actual.distance, actual));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    pq.add(new Node(t, actual.distance + 0, actual));
                                } else if (hgs.getPos(t) == 0) {
                                    pq.add(new Node(t, actual.distance + 1, actual));
                                }
                            }
                    }
                }
                
                Vector<Point> cv = caminsvirtuals(actual.point, p);
                for (Point t : cv) {
                    String c = costat(t);
                    switch (c) {
                        case "R":
                            pq.add(new Node("R", actual.distance, actual));
                            break;
                        case "D":
                            pq.add(new Node("D", actual.distance, actual));
                            break;
                        default:
                            if (!visitats[t.x][t.y]) {
                                if (hgs.getPos(t) == p) {
                                    if(virt || hgs.getPos(actual.point.x, actual.point.y)==p)pq.add(new Node(t, actual.distance + 0, actual));
                                } else if (hgs.getPos(t) == 0) {
                                    if(virt) pq.add(new Node(t, actual.distance + 1, actual));
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
