package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ayush Kadakia
 */ 

public class Collage {

    private Picture originalPicture;

  
    private Picture collagePicture;

    private int collageDimension;

  
    private int tileDimension;
    
  
    public Collage (String filename) {
        this.collageDimension = 4;
        this.tileDimension = 150;
        this.originalPicture = new Picture(filename);

        this.collagePicture = new Picture(this.tileDimension * this.collageDimension, this.tileDimension * this.collageDimension);


        for (int c = 0; c < (this.tileDimension*this.collageDimension); c++) {
            for(int r = 0; r < (this.tileDimension*this.collageDimension); r++) {
                int scalec = c * this.originalPicture.width()/(this.tileDimension*this.collageDimension);
                int scaler = r * this.originalPicture.height() /(this.tileDimension*this.collageDimension);
                Color color = this.originalPicture.get(scalec, scaler);
                this.collagePicture.set(c,r,color);
            } 
        }
      
    }

    public Collage (String filename, int td, int cd) {
        this.collageDimension = cd;
        this.tileDimension = td;
        this.originalPicture = new Picture(filename);
        this.collagePicture = new Picture (this.tileDimension * this.collageDimension, this.tileDimension* this.collageDimension);

        for (int c = 0; c < (this.tileDimension*this.collageDimension); c++) {
            for(int r = 0; r < (this.tileDimension*this.collageDimension); r++) {
                int scalec = c * this.originalPicture.width()/(this.tileDimension*this.collageDimension);
                int scaler = r * this.originalPicture.height() /(this.tileDimension*this.collageDimension);
                Color color = this.originalPicture.get(scalec, scaler);
                this.collagePicture.set(c,r,color);
            } 
        }
    }

    public static void scale (Picture source, Picture target) {

        int width = target.width();
        int height = target.height();
        for (int targetCol = 0; targetCol < width; targetCol++) {
            for (int targetRow = 0; targetRow < height; targetRow++) {
                int sourceCol = targetCol * source.width()  / width;
                int sourceRow = targetRow * source.height() / height;
                Color color = source.get(sourceCol, sourceRow);
                target.set(targetCol, targetRow, color);
            }
        }

    }

   
    public int getCollageDimension() {
        return collageDimension;
    }


       
    public int getTileDimension() {
        return tileDimension;
    }

   
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    
    public Picture getCollagePicture() {
        return collagePicture;
    }


    public void showOriginalPicture() {
        originalPicture.show();
    }

       
    public void showCollagePicture() {
	    collagePicture.show();
    }

  
    public void makeCollage () {

        Picture change = new Picture (this.tileDimension, this.tileDimension);

        for (int c = 0; c < this.tileDimension; c++) {
            for (int r = 0; r < this.tileDimension; r++) {
                int scalec = c * this.originalPicture.width() / this.tileDimension;
                int scaler = r * this.originalPicture.height() / this.tileDimension;
                Color color = this.originalPicture.get(scalec, scaler);
                change.set(c,r,color);
            }
        }
        int zcol = 0;
        int zrow = 0;
        for (int c = 0; c < (this.tileDimension*this.collageDimension); c++ ) {
            if (zcol == this.tileDimension) {
                zcol =0;
            }

            zrow = 0;
            for (int r = 0; r < (this.tileDimension*this.collageDimension);r++) {
                if (zrow == this.tileDimension) {
                    zrow = 0;
                }
            Color color = change.get(zcol,zrow);
            this.collagePicture.set(c,r,color);
            zrow++;
            }
        zcol++;
        }

    }

   
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        int r = 0; 
        int b = 0; 
        int g = 0;

        int newColumn = collageCol * this.tileDimension;
        int newRow = collageRow * this.tileDimension;

        for (int c = newColumn; c < (newColumn + this.tileDimension); c++ ) {
            for (int row = newRow; row < (newRow + this.tileDimension); row++) {
                Color color = this.collagePicture.get(c, row);
                if (component.equals("red")) {
                    r = color.getRed();
                } else if (component.equals("blue")) {
                    b = color.getBlue();
                } else if (component.equals("green")) {
                    g = color.getGreen();
                }
                Color different = new Color(r,g,b);
                this.collagePicture.set(c,row, different);
            }
        }

    }

    public void replaceTile (String filename,  int collageCol, int collageRow) {

        int newColumn = collageCol * this.tileDimension;
        int newRow = collageRow * this.tileDimension;

        Picture tempo = new Picture(filename);
        Picture Change = new Picture(this.tileDimension, this.tileDimension);

        for (int c = 0; c < this.tileDimension; c++) {
            for (int r = 0; r < this.tileDimension; r++) {
                int scalec = c * tempo.width() / this.tileDimension;
                int scaler = r * tempo.height() / this.tileDimension;
                Color color = tempo.get(scalec, scaler);
                Change.set(c,r,color);
            }
        }

        for (int c = 0; c < this.tileDimension; c++) {

            newRow = collageRow * this.tileDimension;
            for (int r = 0; r < this.tileDimension; r++) {
                Color color = Change.get(c, r);
                this.collagePicture.set(newColumn, newRow, color);
                newRow++;

            }
            newColumn++;
        }

    }

    public void grayscaleTile (int collageCol, int collageRow) {
        int newColumn = collageCol * this.tileDimension;
        int newRow = collageRow * this.tileDimension;

        for (int c = newColumn; c < (newColumn + this.tileDimension); c++) {
            for (int r = newRow; r < (newRow + this.tileDimension); r++) {
                Color color = this.collagePicture.get(c,r);
                Color gray = art.Collage.toGray(color);
                this.collagePicture.set(c,r,gray);
            }
        }

 
    }


    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   
        return 0.299*r + 0.587*g + 0.114*b;
    }

  
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }


    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
