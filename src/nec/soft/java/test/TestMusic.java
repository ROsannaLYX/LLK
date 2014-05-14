package nec.soft.java.test;

import javax.sound.midi.*;

import nec.soft.java.utils.ImagesFactory;

import java.io.*;
import java.net.*;

/**
 * 控制背景音乐
 */
public class TestMusic implements MetaEventListener, Runnable {
	private String midiFile = "sound/bg.mid";
	private Sequence sequence = null;
	private Sequencer sequencer;
	private boolean isPlaying = false;
	private volatile Thread thread;

	public TestMusic() {
		try {
			loadMidi(midiFile);
		} catch (InvalidMidiDataException ex) {
		} catch (IOException ex) {
		}
	}

	/**
	 * 读取midi文件
	 */
	public void loadMidi(String filename) throws IOException, InvalidMidiDataException {
		URL url = ImagesFactory.class.getResource("../sound/bg.mid");
		sequence = MidiSystem.getSequence(url);
	}

	public void play() {
		if (isPlaying) { // 如果已经在播放，返回
			return;
		}

		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
			sequencer.addMetaEventListener(this);
		} catch (InvalidMidiDataException ex) {
		} catch (MidiUnavailableException e) {
		}

		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (isPlaying) {
			sequencer.stop();
			isPlaying = false;
		}
		if (thread != null) {
			thread = null;
		}
	}

	public void run() {
		Thread currentThread = Thread.currentThread();
		while (currentThread == thread && !isPlaying) {
			sequencer.start();
			isPlaying = true;
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException ex) {
			}
		}
	}

	public void meta(MetaMessage event) {
		if (event.getType() == 47) {
			System.out.println("Sequencer is done playing.");
		}
	}
	public static void main(String[] args) {
		new TestMusic().play();
	}
}
