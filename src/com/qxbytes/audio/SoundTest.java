package com.qxbytes.audio;

public class SoundTest {
	/*
	 * hit1 - explosion (+6dB) - q player shoot (-20.0dB)
	 * hit2 - dampened lazer (+6dB) - q dampened shooting (-20.0dB)
	 * hit3 - lazer (+6dB) - q hit enemy shot(-20.0dB)
	 * hit4 - DO NOT USE (UNSUPPORTED) - q excellent bit lazer (-20.0dB)
	 * silence1 - super low sounds (6dB) - q player hit? (-20.0dB)
	 */
	public static void main(String[] args) {



		// do whatever computation you like, while music plays
		int N = 10000;
		double sum = 0.0;
		AChannel.playTrack(7, true);

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += Math.sin(i + j);

			}
		}
		System.out.println(sum);

		AChannel.stopTrack();
		AChannel.playTrack(5, true);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += Math.sin(i + j);

			}
		}
		System.out.println(sum);
		//AChannel.playTrack(7, true);
	}

}
