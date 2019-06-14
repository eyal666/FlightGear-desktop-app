package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map extends Canvas {

    StringProperty path;
    ViewController vc;
    double heightDelta;
    double maxHeight, minHeight;
    double [][] matrix;
    double pixelSize;
    Point2D initCoordinate;
    int columns, rows;

    public Map() {
        this.path=new SimpleStringProperty();
    }

    public void setMapDisplayer(ViewController vc,Scanner s){
        this.vc = vc;
        path.bind(vc.vm.path);
        ArrayList<String[]> arr=new ArrayList<>();
        while(s.hasNext()){
            String[] read=s.next().split(",");
            arr.add(read);
        }

        initCoordinate=new Point2D.Double(Double.parseDouble(arr.get(0)[0]),Double.parseDouble(arr.get(0)[1]));
        pixelSize=Double.parseDouble(arr.get(1)[0]);
        columns=arr.get(2).length;
        rows=arr.size()-2;
        matrix=new double[rows][columns];
        for(int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                matrix[i][j]=Double.parseDouble(arr.get(i+2)[j]);
                if(matrix[i][j]<minHeight) minHeight=matrix[i][j];
                else if(matrix[i][j]>maxHeight) maxHeight=matrix[i][j];
            }
        }
        heightDelta=maxHeight-minHeight;
        System.out.println("csv to matrix completed");
        //loadAircraftImage();
        //setAircraftPosition();
    }

    public void convertPathToLine() {
        System.out.println("solution is: "+path.getValueSafe());
    }
    public Color getColor(double cellHeight){
        Color c=Color.hsb(100*cellHeight/heightDelta, 1.0, 0.5);
        double red=c.getRed(), green=c.getGreen(), blue=c.getBlue();
        return Color.color(red, green, blue);
    }
    public void mapDrawer() {
        GraphicsContext gc=getGraphicsContext2D();
        double H=this.getHeight();
        double W=this.getWidth();
        double h=H/rows;
        double w=W/columns;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gc.setFill(getColor(this.matrix[i][j]));
                gc.fillRect(j*w, i*h, w, h);
            }
        }
    }
}