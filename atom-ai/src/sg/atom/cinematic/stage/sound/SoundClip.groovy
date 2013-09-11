package sg.atom.cinematic.stage.sound

public class SoundClip {
    String path;
    boolean music;

    SoundClip(String filename, boolean isMusic) {
        path =  filename;
        music = isMusic;
    }
}

