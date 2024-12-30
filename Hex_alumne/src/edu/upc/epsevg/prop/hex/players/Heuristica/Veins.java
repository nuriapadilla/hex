/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.MyStatus;
import java.awt.Point;
import java.util.Vector;

/**
 * Classe que calcula els veïns d'una posició específica en un tauler Hex,
 * incloent veïns directes i camins virtuals per a possibles jugades
 * estratègiques.
 *
 * @author bruna i nuria
 */
public class Veins {

    private MyStatus hgs;   // Estat actual del joc
    private int mida;       // Mida del tauler
    
    /**
     * Constructor de la classe Veins.
     *
     * @param hgs l'estat actual del joc, representat per un objecte MyStatus.
     */
    public Veins(MyStatus hgs) {
        this.hgs = hgs;
        mida = hgs.getSize();
    }

    /**
     * Retorna un vector de punts que representen els veïns d'una posició
     * específica al tauler, incloent els veïns directes i possibles camins
     * virtuals.
     *
     * Els camins virtuals són posicions addicionals que permeten estratègies
     * avançades en el joc, com per exemple connexions virtuals a través de
     * posicions buides.
     *
     * @param p el punt actual per al qual es volen trobar els veïns.
     * @return un vector de punts que conté tots els veïns directes i virtuals.
     */
    public Vector<Point> veins(Point p) {
        Vector<Point> sol = new Vector<>();
        if (p.x > 0) {
            sol.add(new Point(p.x - 1, p.y));
        }
        if (p.y > 0) {
            sol.add(new Point(p.x, p.y - 1));
        }
        sol.add(new Point(p.x + 1, p.y));
        sol.add(new Point(p.x, p.y + 1));
        if (p.x > 0) {
            sol.add(new Point(p.x - 1, p.y + 1));
        }
        if (p.y > 0) {
            sol.add(new Point(p.x + 1, p.y - 1));
        }
        // Camins virtuals
        if (p.x + 1 < mida && p.y - 2 >= 0 && hgs.getPos(p.x, p.y - 1) == 0 && hgs.getPos(p.x + 1, p.y - 1) == 0) {
            sol.add(new Point(p.x + 1, p.y - 2));
        }
        if (p.x + 2 <= mida && p.y - 1 >= 0 && hgs.getPos(p.x + 1, p.y - 1) == 0 && hgs.getPos(p.x + 1, p.y) == 0) {
            sol.add(new Point(p.x + 2, p.y - 1));
        }
        if (p.x + 1 < mida && p.y + 1 < mida && hgs.getPos(p.x + 1, p.y) == 0 && hgs.getPos(p.x, p.y + 1) == 0) {
            sol.add(new Point(p.x + 1, p.y + 1));
        }
        if (p.x - 1 >= 0 && p.y + 2 <= mida && hgs.getPos(p.x, p.y + 1) == 0 && hgs.getPos(p.x - 1, p.y + 1) == 0) {
            sol.add(new Point(p.x - 1, p.y + 2));
        }
        if (p.x - 2 >= 0 && p.y + 1 < mida && hgs.getPos(p.x - 1, p.y) == 0 && hgs.getPos(p.x - 1, p.y + 1) == 0) {
            sol.add(new Point(p.x - 2, p.y + 1));
        }
        if (p.x - 1 >= 0 && p.y - 1 >= 0 && hgs.getPos(p.x, p.y - 1) == 0 && hgs.getPos(p.x - 1, p.y) == 0) {
            sol.add(new Point(p.x - 1, p.y - 1));
        }

        // camins virtuals de les parets
        if (p.x + 1 == mida && p.y + 1 < mida && hgs.getPos(p.x, p.y) == 0 && hgs.getPos(p.x, p.y + 1) == 0) {
            sol.add(new Point(p.x + 1, p.y));
        }
        if (p.y + 1 == mida && p.x + 1 < mida && hgs.getPos(p.x, p.y) == 0 && hgs.getPos(p.x + 1, p.y) == 0) {
            sol.add(new Point(p.x, p.y + 1));
        }
        return sol;
    }

}
