package br.univali.celine.lms.utils.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;

public class Zip {

	private ZipListener listener;

	public Zip() {

	}

	public void setListener(ZipListener listener) {
		this.listener = listener;
	}

	public void zip(ArrayList<String> fileList, File destFile,
			int compressionLevel) throws Exception {

		if (destFile.exists())
			throw new Exception("File " + destFile.getName()
					+ " already exists!!");

		int fileLength;
		byte[] buffer = new byte[4096];

		FileOutputStream fos = new FileOutputStream(destFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		ZipOutputStream zipFile = new ZipOutputStream(bos);
		zipFile.setLevel(compressionLevel);

		for (int i = 0; i < fileList.size(); i++) {

			FileInputStream fis = new FileInputStream(fileList.get(i));
			BufferedInputStream bis = new BufferedInputStream(fis);
			ZipEntry ze = new ZipEntry(FilenameUtils.getName(fileList.get(i)));
			zipFile.putNextEntry(ze);

			while ((fileLength = bis.read(buffer, 0, 4096)) > 0)
				zipFile.write(buffer, 0, fileLength);

			zipFile.closeEntry();
			bis.close();
		}

		zipFile.close();

	}

	public void unzip(File zipFile, File dir) throws Exception {

		InputStream is = null;
		OutputStream os = null;
		ZipFile zip = null;

		try {

			zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> e = zip.entries();

			byte[] buffer = new byte[2048];
			int currentEntry = 0;

			while (e.hasMoreElements()) {

				ZipEntry zipEntry = (ZipEntry) e.nextElement();
				File file = new File(dir, zipEntry.getName());
				currentEntry++;

				if (zipEntry.isDirectory()) {

					 if (!file.exists())
						 file.mkdirs();
					 
					continue;
					
				}

				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();

				try {

					int bytesLidos = 0;

					is = zip.getInputStream(zipEntry);
					os = new FileOutputStream(file);

					if (is == null)
						throw new ZipException("Erro ao ler a entrada do zip: "
								+ zipEntry.getName());

					while ((bytesLidos = is.read(buffer)) > 0)
						os.write(buffer, 0, bytesLidos);

					if (listener != null)
						listener.unzip((100 * currentEntry) / zip.size());

				} finally {

					if (is != null)
						try {
							is.close();
						} catch (Exception ex) {
						}

					if (os != null)
						try {
							os.close();
						} catch (Exception ex) {
						}

				}
			}

		} finally {

			if (zip != null)
				try {
					zip.close();
				} catch (Exception e) {
				}

		}

	}

	public void zipDir(String zipFileName, String dir) throws Exception {
		File dirObj = new File(dir);
		if (!dirObj.isDirectory()) {
			throw new Exception(dir + " is not a directory");
		}

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

		addDir(dirObj, out, dir.length()+1);

		out.close();

	}

	private void addDir(File dirObj, ZipOutputStream out, int rootLen) throws IOException {
		File[] files = dirObj.listFiles();
		byte[] tmpBuf = new byte[1024];

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				addDir(files[i], out, rootLen);
				continue;
			}

			FileInputStream in = new FileInputStream(files[i].getAbsolutePath());

			out.putNextEntry(new ZipEntry(files[i].getAbsolutePath().substring(rootLen)));

			int len;
			while ((len = in.read(tmpBuf)) > 0) {
				out.write(tmpBuf, 0, len);
			}

			out.closeEntry();
			in.close();
		}
	}

}
