package org.nerdizin.skirmish.util;

import javafx.scene.media.AudioClip;

public enum SoundFx {

    LASPISTOL("laser.mp3");

    private AudioClip audioClip;
    private final static String SOUND_FOLDER = "/resources/sounds/";

    SoundFx(final String soundFile) {
        this.audioClip = new AudioClip(SoundFx.class.getResource(SOUND_FOLDER + soundFile).toString());
    }

    public void play() {
        audioClip.play();
    }

}
