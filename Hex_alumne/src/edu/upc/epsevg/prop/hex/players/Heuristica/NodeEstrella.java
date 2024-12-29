/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import java.awt.Point;
import java.util.Objects;

/**
 *
 * @author bruna
 */
public class NodeEstrella {

    public String corner;
    public Point point;
    public int distance;
    public NodeEstrella anterior;
    public float cost;

    public NodeEstrella(Point point, int distance, float cost, NodeEstrella anterior) {
        this.point = point;
        this.distance = distance;
        this.anterior = anterior;
        this.cost = cost;
    }

    public NodeEstrella(String corner, int distance, float cost, NodeEstrella anterior) {
        this.corner = corner;
        this.distance = distance;
        this.anterior = anterior;
        this.cost = cost;
    }

    public boolean esCantonada() {
        return this.corner != null;
    }

    @Override
    public boolean equals(Object obj) {
        final NodeEstrella other = (NodeEstrella) obj;
        if (other.esCantonada() && this.esCantonada() && other.corner.equals(this.corner)) {
            return true;
        }
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        if (!Objects.equals(this.corner, other.corner)) {
            return false;
        }
        return Objects.equals(this.point, other.point);
    }
}