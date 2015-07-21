package Main;

import processing.core.PImage;

/**
 * Created by Matt on 5/25/2015.
 */
public class Blur
{
    /*
    Fast: 40 times faster than filter(BLUR,1);
    Small: Available only in 1 pixel radius
    Shitty: Rounding errors make image dark soon
    What happens:
       11111100 11111100 11111100 11111100 = mask
       AAAAAAAA RRRRRRRR GGGGGGGG BBBBBBBB = PImage.pixel[i]
       AAAAAA00 RRRRRR00 GGGGGG00 BBBBBB00 = masked pixel
    AA AAAAAARR RRRRRRGG GGGGGGBB BBBBBB00 = sum of four masked pixel, alpha overflows, who cares
       00AAAAAA RRRRRRRR GGGGGGGG BBBBBBBB 00 = shift results to right -> broken alpha, good RGB (rounded down) averages
    */
    PImage fastSmallShittyBlur(PImage a, PImage b)
    { //a=src, b=dest img
        int pa[]=a.pixels;
        int pb[]=b.pixels;
        int h=a.height;
        int w=a.width;
        final int mask=(0xFF&(0xFF<<2))*0x01010101;
        for(int y=1;y<h-1;y++){ //edge pixels ignored
            int rowStart=y*w  +1;
            int rowEnd  =y*w+w-1;
            for(int i=rowStart;i<rowEnd;i++){
                pb[i]=(
                        ( (pa[i-w]&mask) // sum of neighbours only, center pixel ignored
                                +(pa[i+w]&mask)
                                +(pa[i-1]&mask)
                                +(pa[i+1]&mask)
                        )>>2)
                        |0xFF000000 //alpha -> opaque
                ;
            }
        }
        return b;
    }

}
