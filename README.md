# knip-course
KNIME Image Processing - Course Material

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
