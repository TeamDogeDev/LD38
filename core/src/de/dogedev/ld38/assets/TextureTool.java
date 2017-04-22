package de.dogedev.ld38.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextureTool {

    public static void main(String[]args) throws FileNotFoundException {
        TexturePacker.process("raw", "sprites", "packed");
        generateKeyFile();
    }

    private static void generateKeyFile() throws FileNotFoundException {
        TextureAtlas.TextureAtlasData textureAtlasData = new TextureAtlas.TextureAtlasData(new FileHandle("sprites/packed.atlas"), new FileHandle("sprites/packed.atlas").parent(), false);
        String outputFileContent = "";
        File file = new File("..\\src\\de\\dogedev\\ld38\\Key.java");
        PrintWriter writer = new PrintWriter(file);

        writer.println("package de.dogedev.ld38;");
        writer.println("public class Key {");
        for(TextureAtlas.TextureAtlasData.Region region : textureAtlasData.getRegions()){
            if(region.index == 0){
                String line = "public static final String "+region.name.toUpperCase().replace('/','_').replace('-','_') + "_LIST = \"" + region.name + "\";";
                outputFileContent += line+System.lineSeparator();
                writer.println(line);
            } else if(region.index == -1) {
                String line = "public static final String "+region.name.toUpperCase().replace('/','_').replace('-','_') + " = \"" + region.name + "\";";
                outputFileContent += line+System.lineSeparator();
                writer.println(line);
            }
        }

        writer.println("}");

        writer.flush();
        writer.close();
        System.out.println(outputFileContent);
    }
}
