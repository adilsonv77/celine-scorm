/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.celine.lms.integration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.univali.celine.lms.utils.zip.Zip;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackageReader20043rd;
import br.univali.celine.scorm.model.cam.Item20043rd;
import br.univali.celine.scorm.model.cam.Organization;
import br.univali.celine.scorm.model.cam.Resource;

public class ContentPackageBuilder {

	private class FileItem {
		File zipFile;
		String itemName;
		String itemFileRoot;
	}

	private List<FileItem> fileItens = new ArrayList<FileItem>();
	private String tempDir;

	public void add(File zipFile, String itemName, String itemFileRoot) {

		FileItem fi = new FileItem();
		fi.zipFile = zipFile;
		fi.itemName = itemName;
		fi.itemFileRoot = itemFileRoot;

		fileItens.add(fi);

	}

	public ContentPackageBuilder(String tempDir) {
		this.tempDir = tempDir;
	}

	public void build(String organizationName, String outputFileName)
			throws Exception {

		String sdir = tempDir + "/" + organizationName;
		File fdir = new File(sdir);
		FileUtils.deleteDirectory(new File(tempDir));

		fdir.mkdirs();

		ContentPackage cp = ContentPackage.buildBasic(organizationName, organizationName, new ContentPackageReader20043rd());
		Organization org = cp.getOrganizations().getDefaultOrganization();

		Zip zip = new Zip();
		for (FileItem fi : fileItens) {

			zip.unzip(fi.zipFile, new File(sdir + "/" + fi.itemName));
			Item20043rd item = Item20043rd.buildBasic(fi.itemName, fi.itemName);
			item.setIdentifierref("RES-" + fi.itemName);

			org.addItem(item);

			Resource res = new Resource();
			res.setHref(fi.itemName + "/" + fi.itemFileRoot);
			res.setIdentifier(item.getIdentifierref());
			res.setScormType("sco");
			res.setType("webcontent");

			cp.getResources().addResource(res);

		}

		FileUtils.writeStringToFile(new File(sdir + "/imsmanifest.xml"), cp.toString());

		zip.zipDir(outputFileName, sdir);

	}

}
