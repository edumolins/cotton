package com.cotton.objects;

public class ObjSong {

	private int songId;
	private String songTitle;
	private String songArtist;
	private byte[] songImage;

	public ObjSong(int songId, String songTitle, String songArtist, byte[] songImage) {
		this.songId = songId;
		this.songTitle = songTitle;
		this.songArtist = songArtist;
		this.songImage = songImage;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public String getSongTitle() {
		return songTitle;
	}

	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	public String getSongArtist() {
		return songArtist;
	}

	public void setSongArtist(String songArtist) {
		this.songArtist = songArtist;
	}

	public byte[] getSongImage() {
		return songImage;
	}

	public void setSongImage(byte[] songImage) {
		this.songImage = songImage;
	}
}