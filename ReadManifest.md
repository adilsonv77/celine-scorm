# Introduction #

A basic feature of CELINE is reading an IMS manifest. This page examplifies how it is possible read a manifest and print the titles of the items.

# Details #

First, we need to download the following files from [here](https://drive.google.com/folderview?id=0BzNO6qBfK-pGOWY1bV9KYUNvaGc&usp=sharing):
  * celine.jar
  * commons-digester`*`.jar
  * commons-logging`*`.jar
  * commons-beanutils`*`.jar

Then, we create the reader. Because CELINE supports different SCORM versions, the reader is created by a factory.
```
ContentPackageReader cpr = ContentPackageReaderFactory.getContentPackageReader("imsmanifest.xml");
```

Now, it is time to load the file and get the default organization.
```
ContentPackage cp = cpr.read("imsmanifest.xml");
Organization org = cp.getOrganizations().getDefaultOrganization();
Iterator<Item> items = org.getChildren();
```

Finally, iterates by the items.
```
while (items.hasNext()) {
  
  Item item = items.next();
  System.out.println(item.getTitle());

}
```

From here it is possible to access any information from the Item. If the information is from an specific SCORM version, then you need to cast the object.

```
Item20043rd i3rd = (Item20043rd) item;

Item20044th i4th = (Item20044th) item;
```