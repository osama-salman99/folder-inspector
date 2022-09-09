package osmosis.folder.inspector.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class File {
	private static File currentFile;
	private final File parent;
	private final java.io.File fileObject;
	private final List<File> children;
	private final java.io.File[] childrenFileObjects;
	private FileReadyListener fileReadyListener;
	private boolean started;
	private long size;
	private boolean ready;

	public File(java.io.File fileObject, File parent) {
		this.parent = parent;
		this.fileObject = fileObject;
		this.childrenFileObjects = fileObject.listFiles();
		this.children = new ArrayList<>();
		this.fileReadyListener = null;
		this.started = false;
		this.ready = false;
		this.size = -1;
	}

	public static File getCurrentFile() {
		return currentFile;
	}

	public static void setCurrentFile(File currentFile) {
		File.currentFile = currentFile;
	}

	public void calculateDirectorySize() {
		started = true;
		size = 0;
		if (fileObject.isDirectory()) {
			if (childrenFileObjects != null) {
				for (java.io.File child : childrenFileObjects) {
					children.add(new File(child, this));
				}
			}
			for (File directory : new ArrayList<>(children)) {
				directory.calculateDirectorySize();
				size += directory.getSize();
				children.sort(Comparator.comparingLong(File::getSize));
				Collections.reverse(children);
				invokeListener();
			}
		} else {
			size = fileObject.length();
		}
		ready = true;
	}

	public String getPath() {
		return fileObject.getAbsolutePath();
	}

	public File getParent() {
		return parent;
	}

	public long getSize() {
		return size;
	}

	public List<File> getChildren() {
		return children;
	}

	public String getName() {
		return fileObject.getName();
	}

	public void setFileReadyListener(FileReadyListener fileReadyListener) {
		this.fileReadyListener = fileReadyListener;
	}

	public void invokeListener() {
		if (fileReadyListener != null) {
			fileReadyListener.onFileReady();
		}
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isReady() {
		return ready;
	}

	public int getNumberOfChildren() {
		return childrenFileObjects != null ? childrenFileObjects.length : 0;
	}
}
