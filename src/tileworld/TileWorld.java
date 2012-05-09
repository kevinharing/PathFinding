package tileworld;

import utilities.OSDetector;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * TileWorld is a utility class for reading, writing and drawing tile worlds.
 * A tile world is represented in two ways.
 * First, by a file (bitmap image with different colors).
 * Second, by an array of tile types. The array is hidden for other classes.
 * This class is inspired by a previous version by Gerke de Boer.
 * 
 * @author Dennis Breuker
 */
public class TileWorld implements I_TileWorld {
    
    /**
     * The format/extension of the image that is saved.
     */
    private static final String FILE_EXTENSION = "png";
    /**
     * Extra space to fit JPanel in JFrame
     */
    private static final int X_BORDER = 16;
    private static final int Y_BORDER = 38;
    private static final int NO_INDEX = -1;
    /**
     * TileWorld attributes
     */
    private TileType[][] world = null;
    private int width = 0;
    private int height = 0;
    private int blockSize = DEFAULT_BLOCK_SIZE;
    /**
     * The directory where all tile world images that will be searched are stored
     */
    private static String inputDir;
    /**
     * The directory where all tile world solution images are stored
     */
    private static String outputDir;

    /**
     * A TileWorld is created and initialized by providing a file name.
     * @param fileName File name representing an image.
     */
    public TileWorld(String fileName) {
        if(OSDetector.isWindows()) {
            inputDir = "input\\";
            outputDir = "output\\";
        } else {
            inputDir = "input/";
            outputDir = "output/";
        }
        load(fileName);
    }

    /**
     * An empty TileWorld is created by providing its width and height.
     * @param width Width of the tile world.
     * @param height Height of the tile world.
     */
    public TileWorld(int width, int height) {
        this.width = width;
        this.height = height;
        world = new TileType[width][height];
        clear();
    }

    @Override
    public final void load(String fileName) {
        fileName = inputDir + fileName;
        BufferedImage image = readImage(fileName);
        world = imageToWorld(image);
    }

    @Override
    public void save(String fileName) {
        fileName = outputDir + fileName;
        BufferedImage image = worldToImage();
        if (image == null) {
            System.err.println("ERROR during save. No image saved");
        } else {
            writeImage(image, fileName);
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                g.setColor(world[x][y].getColor());
                g.fillRect(x * blockSize, y * blockSize, blockSize - 1, blockSize - 1);
            }
        }
    }

    private void clear() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = TileType.ROAD;
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getSize() {
        return width * height;
    }

    @Override
    public TileType getTileType(int x, int y) {
        if (world == null) {
            return TileType.UNKNOWN;
        } else {
            return world[x][y];
        }
    }

    @Override
    public void setTileType(int x, int y, TileType type) {
        if (world != null) {
            world[x][y] = type;
        }
    }
    
    @Override
    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public int twoDimIndexToOneDimIndex(int x, int y) {
        return y * width + x;
    }

    @Override
    public int oneDimToTwoDimXCoordinate(int index) {
        return index % width;
    }

    @Override
    public int oneDimToTwoDimYCoordinate(int index) {
        return index / width;
    }
    
    @Override
    public void show(String title, int x, int y) {
        show(title, TileWorld.DEFAULT_BLOCK_SIZE, x, y);
    }
    
    @Override
    public void show(String title, int blockSize, int x, int y) {
        // Create a panel containing the tile world
        setBlockSize(blockSize);
        TileWorldPanel tileWorldPanel = new TileWorldPanel(this, blockSize);

        // create a JFrame
        JFrame frame = new JFrame();
        frame.setTitle(title);

        // Add the panel to the frame
        frame.add(tileWorldPanel);
        frame.setSize(tileWorldPanel.getWidth() + X_BORDER, tileWorldPanel.getHeight() + Y_BORDER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocation(x, y);

        // Show the frame
        frame.setVisible(true);
    }
    
    /**
     * 
     * @return the start index
     */
    @Override
    public int findStartIndex() {
        return findIndexContainingType(TileType.START);
    }

    /**
     * 
     * @return the end/destination index
     */
    @Override
    public int findEndIndex() {
        return findIndexContainingType(TileType.END);
    }
    
    /**
     * 
     * @return the start X co-ordinate of the start point
     */
    @Override 
    public int findStartX() {
        return this.oneDimToTwoDimXCoordinate(findStartIndex());
    }
    
    /**
     * 
     * @return the start Y co-ordinate of the start point
     */
    @Override
    public int findStartY() {
        return this.oneDimToTwoDimYCoordinate(findStartIndex());
    }
    
    /**
     * 
     * @return the X co-ordinate of the end/destination point
     */
    @Override
    public int findEndX() {
        return this.oneDimToTwoDimXCoordinate(findEndIndex());
    }
    
    /**
     * 
     * @return the Y co-ordinate of the end/destination point
     */
    @Override
    public int findEndY() {
        return this.oneDimToTwoDimYCoordinate(findEndIndex());
    }
    
    /**
     * 
     * @return  the start TileType
     */
    @Override
    public TileType findStartTile() {
        int startIndex = this.findStartIndex();
        return this.getTileType(this.oneDimToTwoDimXCoordinate(startIndex), 
                this.oneDimToTwoDimYCoordinate(startIndex));
    }
    
    /**
     * 
     * @return the end/destination TileType
     */
    @Override
    public TileType findEndTile() {
        int endIndex = this.findEndIndex();
        return this.getTileType(this.oneDimToTwoDimXCoordinate(endIndex), 
                this.oneDimToTwoDimYCoordinate(endIndex));
    }

    /**
     * Reads an image from a file.
     * 
     * @param fileName Name of the file.
     * @return The image. If an error occurred, null is returned.
     */
    private BufferedImage readImage(String fileName) {
        BufferedImage image = null;

        try {
            File inputFile = new File(fileName);
            image = ImageIO.read(inputFile);
        } catch (IIOException fnfexc) {
            System.err.println("Bitmap file '" + fileName + "' cannot be found");
        } catch (IOException ioexp) {
            System.err.println("No contact with outside world");
        }

        return image;
    }

    /**
     * Writes an image to a file.
     * 
     * @param image The image to be written.
     * @param fileName The file name of the destination file.
     */
    private void writeImage(BufferedImage image, String fileName) {
        try {
            File outputFile = new File(fileName + "." + FILE_EXTENSION);
            ImageIO.write(image, FILE_EXTENSION, outputFile);

        } catch (IOException ioexp) {
            System.err.println("No contact with outside world");
        }
    }

    /**
     * Converts an image to a tile world.
     * The attributes 'width' and 'height' are also set.
     * 
     * @param image The image.
     * @return The tile world. If the image was null, null is returned.
     */
    private TileType[][] imageToWorld(BufferedImage image) {
        if (image == null) {
            return null;
        }

        width = image.getWidth();
        height = image.getHeight();
        int[] rgbWorld = image.getRGB(0, 0, width, height, null, 0, width);

        TileType[][] tileWorld = new TileType[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = twoDimIndexToOneDimIndex(x, y);
                tileWorld[x][y] = TileType.rgbColorToTile(rgbWorld[index]);
                if (tileWorld[x][y] == TileType.UNKNOWN) {
                    System.err.println("Location: " + index + " (" + x + "," + y + ")");
                }
            }
        }
        return tileWorld;
    }

    /**
     * Converts a tile world to a bitmap image.
     * 
     * @return The image. If an error occurred null is returned.
     */
    private BufferedImage worldToImage() {
        if (world == null) {
            return null;
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] rgbWorld = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = twoDimIndexToOneDimIndex(x, y);
                rgbWorld[index] = world[x][y].getRgbColor();
            }
        }

        image.setRGB(0, 0, width, height, rgbWorld, 0, width);

        return image;
    }
    
    /**
     * Searches the tile world for a tile with a given type.
     * 
     * @return The one-dimensional index of the first tile with the given type. If it has not been found, NO_INDEX is returned.
     */
    private int findIndexContainingType(TileType type) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (getTileType(x, y) == type) {
                    return twoDimIndexToOneDimIndex(x, y);
                }
            }
        }
        return NO_INDEX;
    }
}
