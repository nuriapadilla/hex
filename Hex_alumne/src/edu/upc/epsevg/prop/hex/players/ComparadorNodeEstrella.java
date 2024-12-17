/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import java.util.Comparator;

/**
 *
 * @author bruna
 */
public class ComparadorNodeEstrella implements Comparator<NodeEstrella>{

    public ComparadorNodeEstrella() {
    }

    @Override
    public int compare(NodeEstrella t, NodeEstrella t1) {
        return Integer.compare(t.distance, t1.distance);
    }
}