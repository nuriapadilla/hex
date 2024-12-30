/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import java.awt.Point;
import java.util.Objects;

/**
 * Representa un node en l'algorisme de cerca Dijkstra, utilitzat per calcular
 * el camí més curt en el tauler del joc Hex.
 * <p>
 * Els nodes poden representar una posició específica dins del tauler o un node
 * associat a un costat del tauler (L, R, T, D). Cada node també té un valor
 * de <b>virtualcount</b>, que indica el nombre de camins virtuals que passen
 * per aquest camí.
 * </p>
 * 
 * @author nuria
 */
public class Node {

    /**
     * Identificador del costat del tauler al qual està associat el node.
     * Pot ser "L" (esquerra), "R" (dreta), "T" (a dalt), o "D" (a baix).
     * Si no està associat a cap costat, aquest camp és {@code null}.
     */
    public String corner;

    /**
     * Posició del node dins del tauler. És {@code null} si el node representa
     * un punt associat a un costat.
     */
    public Point point;

    /**
     * Distància acumulada des de l'inici del camí fins a aquest node.
     */
    public int distance;

    /**
     * Nombre de camins virtuals associats a aquest node.
     * <p>
     * Els camins virtuals són aquells que no tenen ocupacions físiques al tauler
     * però que es consideren accessibles segons les regles de l'algorisme.
     * </p>
     */
    public int virtualcount;

    /**
     * Crea un node que representa una posició específica al tauler.
     * 
     * @param point Posició del node dins del tauler.
     * @param distance Distància acumulada des de l'inici.
     * @param virtualcount Nombre de camins virtuals associats a aquest node.
     */
    public Node(Point point, int distance, int virtualcount) {
        this.point = point;
        this.distance = distance;
        this.virtualcount = virtualcount;
    }

    /**
     * Crea un node que representa un punt associat a un costat del tauler.
     * 
     * @param corner Identificador del costat ("L", "R", "T", o "D").
     * @param distance Distància acumulada des de l'inici.
     * @param virtualcount Nombre de camins virtuals associats a aquest node.
     */
    public Node(String corner, int distance, int virtualcount) {
        this.corner = corner;
        this.distance = distance;
        this.virtualcount = virtualcount;
    }

    /**
     * Determina si aquest node representa un punt associat a un costat del tauler.
     * 
     * @return {@code true} si el node és un punt associat a un costat,
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
        final Node other = (Node) obj;
        if (other.esCantonada() && this.esCantonada() && other.corner == this.corner) {
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
