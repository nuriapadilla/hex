/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import java.awt.Point;
import java.util.Objects;

/**
 *
 * @author nuria
 */
class Node {

    String corner;
    Point point;
    int distance;
    Node anterior;

    public Node(Point point, int distance, Node anterior) {
        this.point = point;
        this.distance = distance;
        this.anterior = anterior;
    }
    
    public Node(String corner, int distance, Node anterior){
        this.corner = corner;
        this.distance = distance;
        this.anterior = anterior;
    }
    public boolean esCantonada(){
        return this.corner!=null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.corner, other.corner)) {
            return false;
        }
        return Objects.equals(this.point, other.point);
    }
}

