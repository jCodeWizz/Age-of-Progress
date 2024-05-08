package dev.codewizz.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Sound;
import dev.codewizz.utils.Assets;

public class SoundManager {

    private float musicVolume = 1f;
    private float soundVolume = 1f;

    private Sound backgroundMusic;
    private long id;

    public SoundManager() {
    }

    public void playMusic(Sound music) {
        this.backgroundMusic = music;

        this.id = this.backgroundMusic.play(musicVolume);
        this.backgroundMusic.setLooping(id, true);
    }

    public void playSound(Sound sound) {
        sound.play(soundVolume);
    }

    public void playMusic(String music) {
        playMusic(Assets.getSound(music));
    }

    public void playSound(String sound) {
        playSound(Assets.getSound(sound));
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
        this.backgroundMusic.setVolume(this.id, musicVolume);
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }
}
