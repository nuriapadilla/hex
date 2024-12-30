/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import edu.upc.epsevg.prop.hex.HexGameStatus;
import java.awt.Point;

/**
 *
 * @author bruna
 */
public class MyStatus extends HexGameStatus {
     static int[][][] zorbit = null;
     int hash;

    @Override
    public int hashCode() {
        return hash;
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
        final MyStatus other = (MyStatus) obj;
        return this.hash == other.hash;
    }

     
    @Override
    public void placeStone(Point point) {
        super.placeStone(point); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        hash^= zorbit[point.x][point.y][1];
        hash^=zorbit[point.x][point.y][super.getCurrentPlayerColor()+1];
    }
    
    public MyStatus(MyStatus hgs) {
        super(hgs);
        int mida = hgs.getSize();
        hash = hgs.hash;
        initZorbit(mida);
    }

    
    public MyStatus(HexGameStatus hgs){
        super(hgs);
        int mida = hgs.getSize();
        hash = 0;
        initZorbit(mida);
        for(int i=0; i<mida;i++){
            for(int j=0;j<mida;j++){
                int aux = hgs.getPos(i, j);
                hash^= zorbit[i][j][aux+1];
        }
        }
    }
    

    public MyStatus(int mida) {
        super(mida);
        hash = 0;
        initZorbit(mida);
        for(int i=0; i<mida;i++){
            for(int j=0;j<mida;j++){
                hash^= zorbit[i][j][1];
        }
        }
    }
    
    private static void initZorbit(int mida){
        if(zorbit == null){
            zorbit = new int[mida][mida][3];
            for (int i = 0; i < mida; i++) {
                for (int j = 0; j < mida; j++) {
                    for (int k = 0; k < 3; k++) {
                        int randomNumber = (int) (Math.random() * Integer.MAX_VALUE);
                        zorbit[i][j][k] = randomNumber;

                    }
                }
            }
        }
    }
    
}
