/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.Heuristica.NodeEstrella;
import java.util.Comparator;

/**
 *
 * @author bruna
 */
public class ComparadorNodeEstrella implements Comparator<NodeEstrella> {

    public ComparadorNodeEstrella() {
    }

    @Override
    public int compare(NodeEstrella t, NodeEstrella t1) {
        float tfloat = (float) t.distance;
        float t1float = (float) t1.distance;
        float compa = tfloat + t.cost;
        float compa1 = t1float + t1.cost;
       
        
        return Float.compare(compa, compa1);
    }

}
