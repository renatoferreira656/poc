package br.com.dextra.poccanvas;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    private float x;
    private float y;

    public ChartPoint(float x , float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
