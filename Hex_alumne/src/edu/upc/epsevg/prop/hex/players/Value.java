/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import java.awt.Point;

/**
 *
 * @author nuria
 */
class Value {

    public void setP(Point p) {
        this.p = p;
    }

    public void setHeuristica(Integer heuristica) {
        this.heuristica = heuristica;
    }

    public Point getP() {
        return p;
    }

    public int getHeuristica() {
        return heuristica;
    }

    public Value(Point p, Integer heuristica) {
        this.p = p;
        this.heuristica = heuristica;
    }
    private Point p;
    private Integer heuristica;
    
}
