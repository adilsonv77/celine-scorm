package br.univali.celine.lms.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import br.univali.celine.lms.utils.zip.Zip;
import br.univali.celine.scorm.model.cam.AbstractItem;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Organization;
import br.univali.celine.scorm.model.cam.Resource;
import br.univali.celine.scorm.model.cam.Resources;
import br.univali.celine.scorm.model.imsss.ControlMode;
import br.univali.celine.scorm.model.imsss.RandomizationControls;
import br.univali.celine.scorm.model.imsss.RandomizationTiming;
import br.univali.celine.scorm.model.imsss.SelectionTiming;
import br.univali.celine.scorm.versions.BuildVersion;

/***
 * 
 * Facilitate the package construction. Manage copy files.
 * 
 * @author adilsonv
 * 
 */
public class EasyPackage {

	private String packageName;
	private String destFolder;
	private EasyContentPackage easyContentPackage;
	private File fileDestFolder;
	private Resources resources;
	private List<String> files;
	private String organizationName;
	private String organizationIdentifier;
	private BuildVersion scormVersion;
	private AbstractItem root;

	public EasyPackage(BuildVersion version, String packageName,
			String destFolder, String organizationName,
			String organizationIdentifier) {
		this.scormVersion = version;

		this.packageName = packageName;
		this.destFolder = destFolder;
		this.organizationName = organizationName;
		this.organizationIdentifier = organizationIdentifier;

		this.fileDestFolder = new File(destFolder);
		deleteFolder(fileDestFolder);
		fileDestFolder.mkdirs();

		this.easyContentPackage = new EasyContentPackage();
		this.files = new ArrayList<String>();

		root = scormVersion.buildItem();
		root.setIdentifier(this.organizationIdentifier);
		root.setTitle(organizationName);
	}

	public ContentPackage generate() throws Exception {
		ContentPackage cp = easyContentPackage.build(organizationName,
				organizationIdentifier);
		Organization org = cp.getOrganizations().getDefaultOrganization();
		org.getImsssSequencing().assign(root.getImsssSequencing());

		String[] fileNames = scormVersion.getXSDFileNames();
		for (String fileName : fileNames) {

			copyFile(fileName, destFolder);

		}

		PrintStream out = new PrintStream(destFolder + "/imsmanifest.xml");
		out.print(cp.toString());
		out.close();

		Zip zip = new Zip();
		zip.zipDir(packageName, new File(destFolder).getAbsolutePath());

		return cp;
	}

	public void addFilesFromFolder(String folderName) throws IOException {
		File folder = new File(folderName);
		File[] files = folder.listFiles();
		for (File f : files) {
			
			File dest = new File(fileDestFolder.getAbsolutePath() + "/" + f.getName());
			if (f.isDirectory()) {
			
				File beforeDestFolder = fileDestFolder;
				dest.mkdir();

				fileDestFolder = dest;
				addFilesFromFolder(f.getAbsolutePath());
				fileDestFolder = beforeDestFolder;
					
			} else {
				
				this.files.add(f.getName());
				copyFile(f, dest);
						
			}
		}

	}

	public Resource createResourceSCO(String identifier, String... filesNames)
			throws Exception {
		if (identifier == null
				|| easyContentPackage.getResources().getResource(identifier) != null)
			throw new Exception(
					"Identifier must not be null or exists another resource with the same identifier");

		Resource res = new Resource();
		res.setScormType("sco");
		res.setIdentifier(identifier);

		for (String f : filesNames) {
			if (!files.contains(f))
				throw new Exception("File " + f + "doesn't exists on list");
			res.addFile(new br.univali.celine.scorm.model.cam.File(f));
		}

		if (filesNames.length > 0)
			res.setHref(filesNames[0]);

		this.easyContentPackage.addResource(res);

		return res;
	}

	public Resource createResourceAsset(String identifier, String fileName)
			throws Exception {
		if (identifier == null || resources.getResource(identifier) != null)
			throw new Exception(
					"Identifier must not be null or exists another resource with the same identifier");

		Resource res = new Resource();
		res.setScormType("asset");
		res.setIdentifier(identifier);
		res.setHref(fileName);

		if (!files.contains(fileName))
			throw new Exception("File " + fileName + "doesn't exists on list");
		res.addFile(new br.univali.celine.scorm.model.cam.File(fileName));

		this.easyContentPackage.addResource(res);

		return res;
	}

	public void addFileSCO(Resource res, String fileName) throws Exception {
		if (!res.getScormType().equals("sco"))
			throw new Exception("The resource is not a SCO");

		if (!files.contains(fileName))
			throw new Exception("File " + fileName + "doesn't exists on list");
		res.addFile(new br.univali.celine.scorm.model.cam.File(fileName));
		if (res.getHref() == null)
			res.setHref(fileName);

	}

	private AbstractItem doCreateItem(String identifier, String title, Resource res) {
		
		AbstractItem item = scormVersion.buildItem();
		item.setIdentifier(identifier);
		item.setTitle(title);
		if (res != null)
			item.setIdentifierref(res.getIdentifier());
		
		return item;
	}
	
	public AbstractItem createItem(String identifier, String title) {
		
		AbstractItem item = doCreateItem(identifier, title, null);
		item.getImsssSequencing().getControlMode().setFlow(true);
		easyContentPackage.addItem((Item) item);
		return item;
	}

	public AbstractItem createItem(String identifier, String title, Resource res) {


		AbstractItem item = doCreateItem(identifier, title, res);
		easyContentPackage.addItem((Item) item);
		return item;

	}

	public AbstractItem createItem(AbstractItem parent, String identifier,
			String title, Resource res) {
		
		AbstractItem item = doCreateItem(identifier, title, res);
		parent.addItem((Item)item);
		
		return item;
	}

	public AbstractItem getOrganization() {
		return root;
	}

	/* ***************************************************************************** */
	/* Sequencing Navigation Methods */
	/* ***************************************************************************** */
	
	/**
	 * Indicates that the children of the item cannot choice. Just use this if the activity is not a leaf.
	 * About "Sequencing controls" and "Sequencing Control Choice". 
	 * 	 
	 * @param item
	 */
	public void setCannotChoice(AbstractItem item) {
		ControlMode cm = item.getImsssSequencing().getControlMode();
		cm.setChoice(false);
	}

	/**
	 * Indicates that the item cannot exit during interaction. 
	 * About "Sequencing controls" and "Sequencing Control Choice Exit".
	 * 	 
	 * @param item
	 */
	public void setCannotExit(AbstractItem item) {
		ControlMode cm = item.getImsssSequencing().getControlMode();
		cm.setChoiceExit(false);
		
	}

	/**
	 * Indicates that the children from item are reorder. About "Randomization controls".
	 * 	 
	 * @param item
	 * @param once true for once time else for each new attempt
	 */
	public void setRandomize(AbstractItem item, boolean once) {
		RandomizationControls rc = item.getImsssSequencing().getRandomizationControls();
		rc.setReorderChildren(true);
		rc.setRandomizationTiming((once?RandomizationTiming.once:RandomizationTiming.onEachNewAttempt).toString());
		
	}

	/**
	 * Indicates how many children can select from item. About "Selection controls". 
	 * 
	 * @param item
	 * @param howManyChildren
	 */
	public void setLimitChildren(AbstractItem item, int howManyChildren) {
		RandomizationControls rc = item.getImsssSequencing().getRandomizationControls();
		rc.setSelectCount(howManyChildren);
		rc.setSelectionTiming(SelectionTiming.once.toString());
	}

	/* ***************************************************************************** */
	/* Private Methods */
	/* ***************************************************************************** */

	private void copyFile(String fileName, String destFolder) throws Exception {
		System.out.println(fileName);
		File f = new File(fileName);

		InputStream in = getClass().getResourceAsStream(fileName);
		FileOutputStream out = new FileOutputStream(destFolder + "/"
				+ f.getName());

		do {
			int b = in.read();
			if (b == -1)
				break;

			out.write(b);

		} while (true);

		out.close();
		in.close();

	}

	private void copyFile(File source, File destination) throws IOException {

		if (destination.exists())
			destination.delete();

	
		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;

		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destinationChannel = new FileOutputStream(destination).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(),
					destinationChannel);
		} finally {
			if (sourceChannel != null && sourceChannel.isOpen())
				sourceChannel.close();
			if (destinationChannel != null && destinationChannel.isOpen())
				destinationChannel.close();
		}
	}

	private boolean deleteFolder(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteFolder(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();

	}

}
