# knip-course
KNIME Image Processing - Course Material

Make sure that you set up the [KNIME Image Processing developer environment](https://github.com/knime-ip/knip-sdk-setup) beforehand. 

## knip-course-ij2-buddy

A KNIME Image Processing plugin that wraps an ImageJ2 *Command*.

If you are using this plugin as skeleton for a new project, you have to change the `EclipseHelper` according to [Point 4](https://github.com/knime-ip/knip-imagej2#add-your-own-imagej2-plugins-to-knime).

### Exercise 0

1. Create a *lib/* folder within the project and download the required artifacts to build the Coloc2 wrapper from [https://maven.imagej.net](https://maven.imagej.net) into that folder. 
2. Change the plugin's Build configuration (open plugin.xml in project) to include the *lib/* folder
3. Add the libraries to the Runtime classpath
4. Check if your *Command* is picked up when you execute the knime-launch-configuration
5. Export the plugin to yor local KNIME installation (Right click *knip-course-ij2-buddy* > Export > Plug-in Development > Deployable plug-ins and fragments; select the plugins folder of your installation as destination)
6. Restart KNIME to see if it is picked up

## knip-course-ij2-vanilla

An example ImageJ2 *Command* that can be installed as a KNIME Image Processing node via the ImageJ2 integration.

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

## org.knime.knip.course
Import the directory org.knime.knip.course via "Existing Projects into Workspace".

The project has five packages:
* copyimg: is an example implementation that shows how to access an ImgPlusCell and create a new ImgPlusCell. This example node simply copies every pixel of the input image to an output image.
* ex0: is a copy of Skeleton where the icons and names are already changed to MinMaxRadius. Every method signature is already there and described by a comment.
* ex1: is the implementation of MinMaxRadius but the handling of the cells is still missing. The missing methods are already added and described by a comment.
* minmaxradius: is an example implementation that shows how to access a LabelingCell and how to process LabelRegions with the help of imagej-ops. An example image to test the node is in the project direcotry.
* skeleton: is an empty node skeleton with no functionality and can be used as starting point to develop a new KNIME Image Processing Node.

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
