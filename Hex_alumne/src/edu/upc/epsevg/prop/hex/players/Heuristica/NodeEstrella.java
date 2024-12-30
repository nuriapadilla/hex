/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import java.awt.Point;
import java.util.Objects;

/**
 * Representa un node en l'algorisme de cerca A*, utilitzat per calcular
 * el camí més curt en el tauler del joc Hex.
 * <p>
 * Cada node pot representar una posició específica al tauler o una cantonada
 * virtual per a càlculs heurístics.
 * </p>
 * 
 * @author bruna
 */
public class NodeEstrella {

    /**
     * Identificador de la cantonada que representa el node.
     * Pot ser "L" (esquerra), "R" (dreta), "U" (a dalt), o "D" (a baix).
     * Si no és una cantonada, aquest camp és {@code null}.
     */
    public String corner;

    /**
     * Posició del node al tauler.
     */
    public Point point;

    /**
     * Distància acumulada des de l'inici fins a aquest node.
     */
    public int distance;

    /**
     * Referència al node anterior en el camí.
     */
    public NodeEstrella anterior;

    /**
     * Cost heurístic estimat des d'aquest node fins al node final.
     */
    public float cost;

    /**
     * Crea un node que representa una posició específica al tauler.
     * 
     * @param point Posició del node al tauler.
     * @param distance Distància acumulada des de l'inici.
     * @param cost Cost heurístic estimat fins al node final.
     * @param anterior Referència al node anterior en el camí.
     */
    public NodeEstrella(Point point, int distance, float cost, NodeEstrella anterior) {
        this.point = point;
        this.distance = distance;
        this.anterior = anterior;
        this.cost = cost;
    }

    /**
     * Crea un node que representa una cantonada del tauler.
     * 
     * @param corner Identificador de la cantonada ("L", "R", "U" o "D").
     * @param distance Distància acumulada des de l'inici.
     * @param cost Cost heurístic estimat fins al node final.
     * @param anterior Referència al node anterior en el camí.
     */
    public NodeEstrella(String corner, int distance, float cost, NodeEstrella anterior) {
        this.corner = corner;
        this.distance = distance;
        this.anterior = anterior;
        this.cost = cost;
    }

    /**
     * Determina si aquest node representa una cantonada del tauler.
     * 
     * @return {@code true} si el node és una cantonada, {@code false} en cas contrari.
     */
    public boolean esCantonada() {
        return this.corner != null;
    }

    /**
     * Compara aquest node amb un altre per determinar si són iguals.
     * 
     * @param obj L'objecte a comparar.
     * @return {@code true} si els dos nodes són iguals; {@code false} en cas contrari.
     */
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
