/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.Heuristica.Node;
import java.util.Comparator;
/**
 * Comparador per a objectes de la classe {@link Node}.
 * Aquest comparador ordena els nodes en funció de la seva distància,
 * assignant prioritat als nodes amb menor distància.
 * 
 * @author nuria
 */
public class ComparadorNode implements Comparator<Node> {

    /**
     * Crea una nova instància de {@code ComparadorNode}.
     */
    public ComparadorNode() {
    }
    
    /**
     * Compara dos objectes {@link Node} basant-se en la seva distància.
     * 
     * @param o1 el primer node a comparar.
     * @param o2 el segon node a comparar.
     * @return un valor negatiu si {@code o1} té una distància menor que {@code o2},
     *         un valor positiu si {@code o1} té una distància major que {@code o2}, 
     *         o 0 si tenen la mateixa distància.
     */
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.distance, o2.distance);
    }
}

