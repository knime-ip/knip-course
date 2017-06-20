package org.knime.knip.course.exercises;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;

import org.knime.knip.course.examples.CreateImgFromDimFile;
import org.scijava.command.Command;
import org.scijava.command.CommandModule;
import org.scijava.plugin.Plugin;

/**
 * <b>Exercise 1:</b>
 * <ul>
 * <li>Implement a {@code Command} that creates an empty image with dimensions read form a text file:
 * <code>dim0,dim1,dim2,...,dimN</code>
 * </ul>
 */
@Plugin(type=Command.class, headless=true, menuPath="Learnathon>Exercises>Exercise1")
public class Exercise1 implements Command
{

	// TODO Specify inputs and outputs
	
	// TODO Try to use available SciJava services (HINT: look for TextService)
	
	@Override
	public void run()
	{
		// TODO Parse individual values from file
		
		// TODO Create ArrayImg with the read values
	}
	
	@SuppressWarnings( "unchecked" )
	public static void main( String[] args ) throws InterruptedException, ExecutionException
	{
		// Get ImageJ2 Gateway
		ImageJ ij = new ImageJ();
		
		// Execute our Command with defined inputs
		HashMap< String, Object > commandArgs = new HashMap<>();
		commandArgs.put( "dimFile", CreateImgFromDimFile.class.getResource( "/testDimFile.txt" ).getFile() );
		Future< CommandModule > future = ij.command().run( CreateImgFromDimFile.class, true, commandArgs );
		
		// Get output and show the Img
		CommandModule commandModule = future.get();
		Img< DoubleType > output = ( Img< DoubleType > ) commandModule.getOutput( "img" );
		ij.ui().show( output );
	}

}
