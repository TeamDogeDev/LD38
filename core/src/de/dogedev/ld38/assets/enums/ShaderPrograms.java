package de.dogedev.ld38.assets.enums;

/**
 * Created by elektropapst on 22.04.2017.
 */
public enum ShaderPrograms {

    CLOUD_SHADER("shader/passthrough.vsh", "shader/cloudShader.fsh");

    public String vertexShaderFile;
    public String fragmentShaderFile;

    ShaderPrograms(String vertexShaderFile, String fragmentShaderFile) {
        this.vertexShaderFile = vertexShaderFile;
        this.fragmentShaderFile = fragmentShaderFile;
    }
}
