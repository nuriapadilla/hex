/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.epsevg.prop.hex;

import edu.upc.epsevg.prop.hex.HexGameStatus;
import edu.upc.epsevg.prop.hex.PlayerType;
import edu.upc.epsevg.prop.hex.players.ComparadorNode;
import edu.upc.epsevg.prop.hex.players.Dijkstra;
import edu.upc.epsevg.prop.hex.players.Node;
import edu.upc.epsevg.prop.hex.players.ProfeGameStatus2;
import edu.upc.epsevg.prop.hex.players.ProfeGameStatus3;
import edu.upc.epsevg.prop.hex.players.ProfeGameStatus3.Result;
import java.awt.Point;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Vector;

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
            {-1, -1, 1, -1, 0, 0}, // 0   Y
            {-1, -1, 1, 0, 0, 0}, // 1
            {-1, 1, 0, 1, 0, 0}, // 2
            {-1, 1, 1, 1, 1, 0}, // 3
            {1, -1, 0, 0, 0, 0}, // 4
            {1, -1, 0, 0, 0, 0} // 5   Y
        };
        byte[][] board3 = {
            {1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 0, 0, -1, 0, 0},
            {1, 1, 1, -1, 0, 0, 0, -1, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        System.out.println("executem aixo visca");

        HexGameStatus gs = new HexGameStatus(board3, PlayerType.PLAYER2);
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
        Dijkstra di = new Dijkstra(11, hgs);
        int player = -1;
        if (player == 1) {
            cami2 = di.camiMesCurt(left, right, 1);
            cami1 = di.camiMesCurt(up, down, -1);
        } else {
            cami1 = di.camiMesCurt(left, right, 1);
            cami2 = di.camiMesCurt(up, down, -1);
        }
        System.out.println("Cami 1:   " + cami1);
        System.out.println("Cami 2:   " + cami2);
        return cami1 - cami2;
    }
}
