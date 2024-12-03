/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import java.awt.Point;
import java.util.Comparator;

/**
 *
 * @author nuria
 */
class Node {

    public Node(Point point, int distance, Point anterior) {
        this.point = point;
        this.distance = distance;
        this.anterior = anterior;
    }
    Point point;
    int distance;
    Point anterior;
}

class ComparadorComplex implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.distance, o1.distance);
    }
    
}
