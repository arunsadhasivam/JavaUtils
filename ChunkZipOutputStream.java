

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;


/**
 * class Responsible for splitting the zip based on configured file limit
 *
 * @author Arun kumar
 *
 */
public class ChunkZipOutputStream {
	/** variable to hold logger constant **/
	private static Logger itsLogger = LogManager.getLogger(ChunkZipOutputStream.class);

	private ZipOutputStream zipOutputStream;

	private String zipPath;
	private String zipName;

	public long currentSize;
	private int currentChunkIndex=1;

	public ChunkZipOutputStream(String path, String zipName)
			throws FileNotFoundException {
		this.zipPath = path;
		this.zipName = zipName;
		constructNewStream();
	}

	/**
	 * To add new Zip Entry
	 * @param entry
	 * @throws IOException
	 */
	public void addEntry(ZipEntry entry, FileInputStream checkSize) throws IOException {
		long entrySize = 0;
		byte b[] = new byte[1024];
		int length;
		try {
			while ((length = checkSize.read(b)) >= 0) {
				entrySize+= length;
			}
		} catch (Exception e) {
			itsLogger.error("addEntry - calculate size error: " + e.getMessage());
		}
		long maxZipSize = CommonConstants.MAX_BYTE_SIZE;
		if ((MigrationUtils.toMB(currentSize + entrySize)) > (maxZipSize-1)  ) {
			closeStream();
			constructNewStream();
			currentSize += entrySize;
			zipOutputStream.putNextEntry(entry);
		} else {
			currentSize += entrySize;
			zipOutputStream.putNextEntry(entry);
		}
	}

	public void closeStream() throws IOException {
		zipOutputStream.close();
	}

	/**
	 * To construct the few zip output stream with new name
	 * @throws FileNotFoundException
	 */
	private void constructNewStream() throws FileNotFoundException {
		zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(
				zipPath, constructCurrentPartName())));
		currentChunkIndex++;
		currentSize = 0;
	}



	/**
	 * To write the file input
	 * @param fis
	 */
	public void write(FileInputStream fis) {
		byte b[] = new byte[1024];
		int length;
		try {
			while ((length = fis.read(b)) >= 0) {
				zipOutputStream.write(b, 0, length);
				//currentSize+= length;
			}
		} catch (IOException e) {
			itsLogger.error("ChunkZipOutputStream:write:" + e.getMessage());
		}

	}

	/**
	 * To close Zip Entry
	 */
	public void closeEntry() {
		try {
			zipOutputStream.closeEntry();
		} catch (IOException e) {
			itsLogger.error("ChunkZipOutputStream:closeEntry:" + e);
		}
	}

	/**
	 * To construct new Name if it exceeds limit.
	 * @return
	 */
	private String constructCurrentPartName() {
		// This will give names is the form of <file_name>.part.0.zip,
		// <file_name>.part.1.zip, etc.
		zipName= zipName.replace(CommonConstants.ZIP, CommonConstants.EMPTY);
		StringBuilder partNameBuilder = new StringBuilder(zipName);
		partNameBuilder.append(CommonConstants.PART_POSTFIX);
		partNameBuilder.append(currentChunkIndex);
		partNameBuilder.append(CommonConstants.ZIP);

		return partNameBuilder.toString();
	}
}
