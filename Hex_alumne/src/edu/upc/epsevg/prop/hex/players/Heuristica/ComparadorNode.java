/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.Heuristica.Node;
import java.util.Comparator;

/**
 *
 * @author nuria
 */
public class ComparadorNode implements Comparator<Node>{

    public ComparadorNode() {
    }
    
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.distance, o2.distance);
    }
}
