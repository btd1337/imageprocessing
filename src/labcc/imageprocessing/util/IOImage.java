package labcc.imageprocessing.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// http://alvinalexander.com/blog/post/java/getting-rgb-values-for-each-pixel-in-image-using-java-bufferedi
// http://stackoverflow.com/questions/16475482/how-can-i-load-a-bitmap-image-and-manipulate-individual-pixels


public class IOImage {

	private int h, w, red[][], blue[][], green[][];
	private BufferedImage image;

	public int getHeight() {
		return h;
	}
	
	public int getWidth() {
		return w;
	}

	public int[][] getBlue() {
		return blue;
	}
	
	public int[][] getGreen() {
		return green;
	}
	
	public int[][] getRed() {
		return red;
	}
	
	private void populatePixelARGB(int pixel, int i, int j) {
		this.red[i][j] = (pixel >> 16) & 0xff;
		this.green[i][j] = (pixel >> 8) & 0xff;
		this.blue[i][j] = (pixel) & 0xff;
	}

	private void marchThroughImage(BufferedImage image) {

		this.w = image.getWidth();
		this.h = image.getHeight();

		this.red = new int[h][w];
		this.green = new int[h][w];
		this.blue = new int[h][w];

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel = image.getRGB(j, i);
				populatePixelARGB(pixel, i, j);
			}
		}
	}
	
	public void write(String path, String filename) throws IOException{
		this.update();
		ImageIO.write(this.image, "bmp", new File(path, filename));
	}

	
	private void update() {
		for (int y = 0; y < this.w; y++) 
		    for (int x = 0; x < this.h; x++) 
		        this.image.setRGB(y, x, rgb2int(this.red[x][y], this.green[x][y], this.blue[x][y]));
	}
	
	private int rgb2int(int r, int g, int b) {
		return (r << 16) | (g << 8) | b;
	}
	
	
	public IOImage(String file) {
		try {
			this.image = ImageIO.read(new File(file));
			marchThroughImage(image);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public BufferedImage getImage() {
		this.update();
		return this.image;
	}

}