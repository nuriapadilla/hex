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
 * Cada node pot representar una posició específica al tauler o un node adjacent
 * a un dels costats del tauler (L, R, T, D).
 * </p>
 * <p>
 * Els nodes adjacents als costats es refereixen als punts que actuen com
 * a punts de connexió als costats esquerre (L), dret (R), superior (T) i inferior (D)
 * del tauler per calcular els camins.
 * </p>
 * 
 * @author bruna
 */
public class NodeEstrella {

    /**
     * Identificador del costat del tauler al qual està adjacent el node.
     * Pot ser "L" (esquerra), "R" (dreta), "T" (a dalt), o "D" (a baix).
     * Si no està associat a cap costat, aquest camp és {@code null}.
     */
    public String corner;

    /**
     * Posició del node dins del tauler. És {@code null} si el node representa
     * un punt adjunt a un costat.
     */
    public Point point;

    /**
     * Distància acumulada des de l'inici del camí fins a aquest node.
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
     * @param point Posició del node dins del tauler.
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
     * Crea un node que representa un punt adjacent a un costat del tauler.
     * 
     * @param corner Identificador del costat ("L", "R", "T", o "D").
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
     * Determina si aquest node representa un punt adjacent a un costat del tauler.
     * 
     * @return {@code true} si el node és un punt adjacent a un costat,
     *         {@code false} en cas contrari.
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
