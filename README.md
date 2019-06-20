# KNIME Image Processing Course

KNIME Image Processing - Course Material

Make sure that you set up the [KNIME Image Processing developer environment](https://github.com/knime-ip/knip-sdk-setup) beforehand.

## Wrap ImageJ2 *Command* as KNIME Image Processing Plugin

The folder `knip-course-ij2-buddy` contains a KNIME Image Processing plugin that wraps an ImageJ2 *Command*.

If you are using this plugin as skeleton for a new project, you have to change the `EclipseHelper` according to [Point 4](https://github.com/knime-ip/knip-imagej2#add-your-own-imagej2-plugins-to-knime).

### Exercise 0

1. Create a *lib/* folder within the project and download the required artifacts to build the Coloc2 wrapper from [maven.scijava.org](https://maven.scijava.org) into that folder.
2. Add the libraries to the Runtime classpath (open plugin.xml in project) to add the `lib/*.jar` jars
3. Check if your *Command* is picked up when you execute the knime-launch-configuration
4. Export the plugin to yor local KNIME installation (Right click *knip-course-ij2-buddy* > Export > Plug-in Development > Deployable plug-ins and fragments; select the plugins folder of your installation as destination)
5. Restart KNIME to see if it is picked up

## Add ImageJ2 *Command* to KNIME Analytics Platform

The dirctory `knip-course-ij2-vanilla` contains an example ImageJ2 *Command* that can be installed as a KNIME Image Processing node via the ImageJ2 integration.

### Exercise 0

1. Implement a *Command* that computes the mean intensity of an image
2. Open your KNIME installation with the installed Image Processing Extensions
3. Go to Preferences > KNIME > Image Processing Plugin > ImageJ2 Plugin Installation
4. Add and select the JAR file that contains your *Command*
5. Find the *Command* in the Node Repository

### Exercise 1

1. Implement a *Command* that creates an empty image with dimensions read from a text file of the form: `dim0,dim1,dim2,...,dimN`
2. Open your KNIME installation with the installed Image Processing Extensions
3. Go to Preferences > KNIME > Image Processing Plugin > ImageJ2 Plugin Installation
4. Add and select the JAR file that contains your *Command*
5. Find the *Command* in the Node Repository - any issues?

## Write an KNIME Image Processing Plugin

Import the directory `org.knime.knip.course` via "Existing Projects into Workspace".

The has multiple packages:
* `example.copyimg`: Example implementation that shows how to access an ImgPlusCell and create a new ImgPlusCell. This example node simply copies every pixel of the input image to an output image.
* `skeleton`: Empty node skeleton with no functionality and can be used as starting point to develop a new KNIME Image Processing Node.
* `exercise`: Three exercises that of variing difficulty. The method signatures are alredy there and the exercises are described in comments.
  * `addconstant`: Exercise for a node which adds a constant value to an image. This is a good exercise to understand which parts a KNIME node consists of and how to handle the different states of a node.
  * `numpixels`: Exercise for a node which counts the number of pixels for each segment of a labeling. This is a good exercise to learn how to access a LabelingCell and LabelRegions and how to create multiple output rows for one input row.
  * `minmaxradius`: Exercise for a node which computes the minumum and maximum radius of each segment of a labeling. This is a good exercise to learn how to use imagej-ops in KNIME Image Processing nodes.
* `solution`: Example solutions for all three exercises.


Adding a new Node-Category:
1. Open plugin.xml
2. Go to Extensions
3. Add org.knime.workbench.repository.categories extension point.
4. Right click on extension point -> New -> category

Add a new Node to a Category:
1. Open plugin.xml
2. Go to Extensions
3. Add org.knime.workbench.repository.nodes extension point.
4. Right click on extension point -> New -> node


