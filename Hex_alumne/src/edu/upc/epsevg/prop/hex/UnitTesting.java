/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.epsevg.prop.hex;

import edu.upc.epsevg.prop.hex.HexGameStatus;
import edu.upc.epsevg.prop.hex.PlayerType;
import edu.upc.epsevg.prop.hex.players.ComparadorNode;
import edu.upc.epsevg.prop.hex.players.Node;
import edu.upc.epsevg.prop.hex.players.ProfeGameStatus2;
import edu.upc.epsevg.prop.hex.players.ProfeGameStatus3;
import edu.upc.epsevg.prop.hex.players.ProfeGameStatus3.Result;
import java.awt.Point;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 * @author bernat
 */
public class UnitTesting {

    public static void main(String[] args) {

        byte[][] board = {
            //X   0  1  2  3  4  5  6  7  8
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0   Y
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, // 1
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, // 2
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, // 3
            {0, 0, 0, 0, -1, 0, 0, 0, 0}, // 4  
            {0, 0, 0, 0, 0, 1, 0, 0, 0}, // 5    
            {0, 0, 0, -1, -1, -1, 1, -1, 0}, // 6      
            {0, 0, 1, 1, 1, 1, -1, 1, 0}, // 7       
            {0, 0, 0, 0, 0, 0, -1, 0, 1} // 8    Y         
        };

        byte[][] board2 = {
            //X   0  1  2  3  4  5
            {-1, -1, 1, 0, 0, 0}, // 0   Y
            {-1, -1, 1, 0, 0, 0}, // 1
            {-1, 1, 0, 1, -1, 0}, // 2
            {-1, 1, 1, 1, 1, 0}, // 3
            {1, -1, 0, 0, 0, 0}, // 4
            {1, -1, 0, 0, 0, 0} // 5   Y
        };

        System.out.println("executem aixo visca");

        HexGameStatus gs = new HexGameStatus(board2, PlayerType.PLAYER2);
        System.out.println(gs.toString());
        int heuri = heuristica(gs);
        System.out.println("heuri");

    }

    public static int heuristica(HexGameStatus hgs) {
        int cami1;
        int cami2;
        Node left = new Node("L", 0, null);
        Node right = new Node("R", Integer.MAX_VALUE, null);
        Node up = new Node("U", 0, null);
        Node down = new Node("D", Integer.MAX_VALUE, null);
        //System.out.println(hgs.toString());
        int player = -1;
        if (player == 1) {
            cami2 = camiMesCurt(hgs, left, right, 1);
            cami1 = camiMesCurt(hgs, up, down, -1);
        } else {
            cami1 = camiMesCurt(hgs, left, right, 1);
            cami2 = camiMesCurt(hgs, up, down, -1);
        }
        return cami1 - cami2;
        //return 0;
    }

    public static int camiMesCurt(HexGameStatus hgs, Node nIni, Node nFi, int p) {
        // Problema: !!!! Pot utilitzar les cantonades per trobar el més curt aaaaaAAAA no pot ser això NOMES LA PRIMERAAAA
        // Hem decidit tenir els nodes en una PriorityQueue 
        System.out.println("Estic cami mes curt i el meu node ini: "+ nIni.corner + " node fi: "+ nFi.corner);
        int mida = 6;

        PriorityQueue<Node> pq = new PriorityQueue<Node>(new ComparadorNode()); // Preguntar Bernat!!!!!
        // Necessito tenir una llista de nodes visistats !!!
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
        for (Node element : pq) {
            if (element.esCantonada()) {
                System.out.println(element.corner );
            } else {
                System.out.println(element.point + " " + element.distance);

            }
        }
        System.out.println("---------------------------------------");

        Node actual = pq.poll();
        while (actual != null && !(actual.esCantonada() && actual.corner.equals(nFi.corner))) {

            if(actual.esCantonada()) System.out.println("Evaluo" + actual.corner);
            else System.out.println("Evaluo "+ actual.point);
            for (Node element : pq) {
                if (element.esCantonada()) {
                    System.out.println(element.corner+ " " + element.distance );
                } else {
                    System.out.println(element.point + " " + element.distance);

                }
            }
            System.out.println("---------------------------------------");

            if (!actual.esCantonada() && !visitats[actual.point.x][actual.point.y]) {
                visitats[actual.point.x][actual.point.y] = true;
                //  System.out.println("Visito el punt: " + actual.point.x +"  "+ actual.point.y + " dist: "+ actual.distance);
                if (actual.point.x > 0 && !visitats[actual.point.x - 1][actual.point.y]) {
                    if (hgs.getPos(actual.point.x - 1, actual.point.y) == p) {
                        //        System.out.println("afegeixo: " + (actual.point.x - 1) + " " + actual.point.y);
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x - 1, actual.point.y) == 0) {
                        //      System.out.println("afegeixo: " + (actual.point.x - 1) + " " + actual.point.y);
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y), actual.distance + 1, actual));
                    }
                }
                //System.out.println("El primer ok");

                if (actual.point.y > 0 && !visitats[actual.point.x][actual.point.y - 1]) {
                    if (hgs.getPos(actual.point.x, actual.point.y - 1) == p) {
                        //System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y-1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y - 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x, actual.point.y - 1) == 0) {
                        //System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y - 1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y - 1), actual.distance + 1, actual));
                    }
                }
                //System.out.println("Segon ok  "+ (actual.point.x+1)+ " "+ actual.point.y);
                if ((actual.point.x + 1) < mida && !visitats[actual.point.x + 1][actual.point.y]) {
                    if (hgs.getPos(actual.point.x + 1, actual.point.y) == p) {
                        //      System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y ));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x + 1, actual.point.y) == 0) {
                        //    System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y), actual.distance + 1, actual));
                    }
                }
                //System.out.println("Tercer ok");
                if ((actual.point.y + 1) < mida && !visitats[actual.point.x][actual.point.y + 1]) {
                    if (hgs.getPos(actual.point.x, actual.point.y + 1) == p) {
                        // System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y + 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x, actual.point.y + 1) == 0) {
                        // System.out.println("afegeixo: " + (actual.point.x) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x, actual.point.y + 1), actual.distance + 1, actual));
                    }
                }
                //System.out.println("Quart ok");
                if (actual.point.x > 0 && actual.point.y < mida - 1 && !visitats[actual.point.x - 1][actual.point.y + 1]) {
                    if (hgs.getPos(actual.point.x - 1, actual.point.y + 1) == p) {
                        //      System.out.println("afegeixo: " + (actual.point.x-1) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y + 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x - 1, actual.point.y + 1) == 0) {
                        //    System.out.println("afegeixo: " + (actual.point.x-1) + " " + (actual.point.y + 1));
                        pq.add(new Node(new Point(actual.point.x - 1, actual.point.y + 1), actual.distance + 1, actual));
                    }
                }
                //System.out.println("5 ok");
                if (actual.point.y > 0 && actual.point.x < mida - 1 && !visitats[actual.point.x + 1][actual.point.y - 1]) {
                    if (hgs.getPos(actual.point.x + 1, actual.point.y - 1) == p) {
                        //      System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y - 1));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y - 1), actual.distance + 0, actual));
                    } else if (hgs.getPos(actual.point.x + 1, actual.point.y - 1) == 0) {
                        //    System.out.println("afegeixo: " + (actual.point.x+1) + " " + (actual.point.y - 1));
                        pq.add(new Node(new Point(actual.point.x + 1, actual.point.y - 1), actual.distance + 1, actual));
                    }
                }
                // System.out.println("6 ok");
                if (actual.point.x == mida - 1) {
                      System.out.println(actual.distance + "suuuuu");

                    pq.add(new Node("R", actual.distance, actual));

                }
                if (actual.point.y == mida - 1) {
                    System.out.println(actual.distance + "saaaa");
                    pq.add(new Node("D", actual.distance, actual));
                }
            }
            actual = pq.poll();
        }
        if (actual == null) {
            System.out.println("ÉS nulllll");
        }
        return actual.distance;
    }

}
