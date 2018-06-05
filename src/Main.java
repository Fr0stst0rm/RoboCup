import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        buildMap();

        System.out.println(BotStatus.mazeMap.toString());

        ASternDerDeinenNamenTraegt astern = new ASternDerDeinenNamenTraegt(new Point(0,2),new Point(4,5) );

        astern.getPath();

    }

    private void buildMap() {
        MapTile tile = new MapTile();
        tile.isStart = true;
        tile.wallEast = true;
        tile.wallSouth = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 0, tile);

        tile = new MapTile();
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-1, 1, tile);

        tile = new MapTile();
        tile.wallSouth = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-2, 1, tile);

        tile = new MapTile();
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(1, 1, tile);

        tile = new MapTile();
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(2, 1, tile);

        tile = new MapTile();
        tile.wallSouth = true;
        tile.wallEast = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(3, 1, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 1, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-2, 2, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-1, 2, tile);

        tile = new MapTile();
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 2, tile);

        tile = new MapTile();
        tile.visited = true;

        BotStatus.mazeMap.addTile(1, 2, tile);

        tile = new MapTile();
        tile.wallNorth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(2, 2, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(3, 2, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-2, 3, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-1, 3, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 3, tile);

        tile = new MapTile();
        tile.visited = true;

        BotStatus.mazeMap.addTile(1, 3, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(2, 3, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.wallNorth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(3, 3, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-2, 4, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.wallNorth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-1, 4, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.wallEast = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 4, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(1, 4, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallNorth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(2, 4, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(3, 4, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-2, 5, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-1, 5, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.wallEast = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 5, tile);

        tile = new MapTile();
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(1, 5, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallNorth = true;
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(2, 5, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(3, 5, tile);

        tile = new MapTile();
        tile.wallNorth = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-2, 6, tile);

        tile = new MapTile();
        tile.wallNorth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(-1, 6, tile);

        tile = new MapTile();
        tile.wallNorth = true;
        tile.wallEast = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(0, 6, tile);

        tile = new MapTile();
        tile.wallNorth = true;
        tile.wallWest = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(1, 6, tile);

        tile = new MapTile();
        tile.wallNorth = true;
        tile.wallSouth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(2, 6, tile);

        tile = new MapTile();
        tile.wallEast = true;
        tile.wallNorth = true;
        tile.visited = true;

        BotStatus.mazeMap.addTile(3, 6, tile);


    }

    public void printMapToFile() {
        File mapOut = new File("map.txt");

        try {

            if (mapOut.exists()) {
                mapOut.delete();
            }

            mapOut.createNewFile();

            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapOut)));

            bfw.write(BotStatus.mazeMap.toString());

            bfw.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
