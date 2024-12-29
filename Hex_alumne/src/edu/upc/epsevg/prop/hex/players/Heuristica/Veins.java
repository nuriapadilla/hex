/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players.Heuristica;

import edu.upc.epsevg.prop.hex.players.MyStatus;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author bruna
 */
public class Veins {

    private MyStatus hgs;
    private int mida = 11;

    public Veins(MyStatus hgs) {
        this.hgs = hgs;
    }

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
