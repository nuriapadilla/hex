/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.hex.players;

import edu.upc.epsevg.prop.hex.HexGameStatus;
import java.awt.Point;

/**
 * La classe {@code MyStatus} és una extensió de {@link HexGameStatus} que afegeix
 * funcionalitat per calcular i gestionar un valor hash únic per a l'estat del tauler
 * mitjançant Zobrist hashing. Aquesta implementació és útil per a optimitzar
 * operacions com comparacions i ús en estructures de dades com taules hash.
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

     /**
     * Col·loca una peça al tauler en una posició determinada i actualitza el
     * valor hash utilitzant Zobrist hashing.
     *
     * @param point el punt on col·locar la peça.
     */
    @Override
    public void placeStone(Point point) {
        super.placeStone(point); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        hash^= zorbit[point.x][point.y][1];
        hash^=zorbit[point.x][point.y][super.getCurrentPlayerColor()+1];
    }
    
    /**
     * Constructor que crea una còpia d'un altre objecte {@code MyStatus} i
     * copia el valor hash existent.
     *
     * @param hgs l'objecte {@code MyStatus} a copiar.
     */
    public MyStatus(MyStatus hgs) {
        super(hgs);
        int mida = hgs.getSize();
        hash = hgs.hash;
        initZorbit(mida);
    }

    /**
     * Constructor que crea un objecte {@code MyStatus} a partir d'un objecte
     * {@link HexGameStatus}. Calcula el valor hash inicial basat en l'estat del
     * tauler.
     *
     * @param hgs l'objecte {@code HexGameStatus} a convertir.
     */
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
    
    /**
     * Constructor que crea un estat inicial buit per a un tauler de mida
     * específica. Calcula el valor hash inicial per al tauler buit.
     *
     * @param mida la mida del tauler.
     */
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
    
    /**
     * Inicialitza la matriu {@code zorbit} amb valors aleatoris per al càlcul
     * del hash segons la mida del tauler. Aquesta inicialització es realitza
     * només una vegada.
     *
     * @param mida la mida del tauler.
     */
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
