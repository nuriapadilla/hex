/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.Heuristica.NodeEstrella;
import java.util.Comparator;
/**
 * Comparador per a objectes de la classe {@link NodeEstrella}.
 * Aquest comparador utilitza la distància i el cost acumulat d'un node per determinar
 * l'ordre de prioritat en estructures com les cues de prioritat.
 * 
 * @author bruna
 */
public class ComparadorNodeEstrella implements Comparator<NodeEstrella> {

    /**
     * Crea una nova instància de {@code ComparadorNodeEstrella}.
     */
    public ComparadorNodeEstrella() {
    }

    /**
     * Compara dos objectes {@link NodeEstrella} en funció de la seva distància i cost acumulat.
     * 
     * @param t el primer node a comparar.
     * @param t1 el segon node a comparar.
     * @return un valor negatiu si {@code t} té menor prioritat que {@code t1}, 
     *         un valor positiu si {@code t} té major prioritat que {@code t1}, 
     *         o 0 si tenen la mateixa prioritat.
     */
    @Override
    public int compare(NodeEstrella t, NodeEstrella t1) {
        float tfloat = (float) t.distance;
        float t1float = (float) t1.distance;
        float compa = tfloat + t.cost;
        float compa1 = t1float + t1.cost;
       
        return Float.compare(compa, compa1);
    }
}
