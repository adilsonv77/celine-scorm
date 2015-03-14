# Introduction #

On another post examplifies how it's possible to read imsmanifest. In this page is examplified how can change a SCORM package based on an existing package.

# Details #

First, we need to download the following files from [here](https://drive.google.com/folderview?id=0BzNO6qBfK-pGOWY1bV9KYUNvaGc&usp=sharing):
  * celine.jar
  * commons-digester`*`.jar
  * commons-logging`*`.jar
  * commons-beanutils`*`.jar
  * commons-io`*`.jar

As showed in the [ReadManifest](http://code.google.com/p/celine-scorm/wiki/ReadManifest) page, first we need to load the file.

```
ContentPackageReader cpr = ContentPackageReaderFactory.getContentPackageReader("course/imsmanifest.xml");
ContentPackage cp = cpr.read(file);
```

Now, is time to demonstrate a class that manipulate the content package. First, we create the object.

```
EasyPackage easy = new EasyPackage(cp, "newcourse.zip", "course", "newcourse", false);

```

The parameters are:
  * cp: the source content package
  * newcourse.zip: the file will be generated at the end of proccess
  * course: the source folder, where are located all the files
  * newcourse: the destination folder. It will create a copy of the source folders. It is neccessary because the class EasyPackage has features to join content packages
  * false: if the SCO identifiers will be modificated. We use true when our intention is join content packages

Now, we can access some SCO and perform modifications. The getSCO method retrieves an SCO by the id parameter. The method setCannotExit() changes the "choiceExit" control mode to false.

```
AbstractItem sco = easy.getSCO("C1_D1");
easy.setCannotExit(sco);
```

The class EasyPackage has only four methods to change sequencing and navegation values. However, if you need modify other values, please contact us. These values was neccessary in some of our projects.

There is another way to access and modify any value. We can access directly the SCO attributes. Below is an example if you want to change the objectives.

```
sco.getImsssSequencing().getObjectives().getPrimaryObjective().setPrimary(true);
```

The methods (setCannotChoice, setCannotExit, setRandomize and setLimitChildren) available in EasyPackage help the programmer to simplify the access.

However, the power the EasyPackage is creating new SCO's. First, in the code block below we exemplify the resources addition.
```
easy.addFilesFromFolder("assets");

// create a SCO resource with two files
Resource res1 = easy.createResourceSCO("one", "pag1.html",
			"lapis.jpeg", "pag1-2.html", "pag1-3.html");
res1.setXmlBase("resources/"); 

// another option to create a SCO resource
Resource res2 = easy.createResourceSCO("two");
res2.setXmlBase("resources/");
easy.addFileSCO(res2, "pag2.html");
easy.addFileSCO(res2, "esportes.jpeg");

// create a SCO resource with two files
Resource res3 = easy.createResourceSCO("three", "pag1.html",
			"lapis.jpeg", "pag1-2.html", "pag1-3.html");
res3.setXmlBase("resources/"); 

```

Next, we create the SCO's and change some Sequencig and Navigation.
```
// adding three items to a course link with the respective resources
AbstractItem item1 = easy.createItem("item1", "First", res1);
AbstractItem item2 = easy.createItem("item2", "Second", res2);
AbstractItem item3 = easy.createItem("item3", "Third", res3);

AbstractItem root = easy.getOrganization();
easy.setLimitChildren(root, 2); // show just two items
easy.setRandomize(root, false); // reorder the sequence from the items
```

Finally, the zip generation.
```
easy.generate();
```