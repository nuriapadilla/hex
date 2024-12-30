/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import java.awt.Point;

/**
 * La classe {@code Eval} representa un moviment avaluat en el joc Hex.
 * Conté un punt del tauler i el valor heurístic associat, utilitzat per a 
 * prioritzar jugades durant l'exploració de moviments.
 * 
 * @author nuria
 */
public class Eval {
    Point punt;
    double heur;

    /**
     * Constructor per inicialitzar un objecte {@code Eval} amb un punt i el seu
     * valor heurístic.
     *
     * @param p el punt del tauler associat al moviment.
     * @param h el valor heurístic assignat al moviment.
     */
    public Eval(Point p, double h) {
        punt = p;
        heur = h;
    }
}
