package osmosis.folder.inspector.file;

public abstract class FileReadyListener {
	private File file;

	public FileReadyListener(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public abstract void onFileReady();
}
