package org.knime.knip.course.examples;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.CommandModule;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.text.TextService;

/**
 * <b>Exercise 0:</b>
 * <ul>
 * <li>Implement a {@code Command} that creates an empty {@code Img<DoubleType>}
 * with dimensions read from a text file: <code>dim0,dim1,dim2,...,dimN</code>
 * </ul>
 */
@Plugin( type = Command.class, headless = true, menuPath = "Learnathon>Examples>Create Img From Dimension File" )
public class CreateImgFromDimFile implements Command
{

	@Parameter
	private TextService textService;

	@Parameter
	private File dimFile;

	@Parameter( type = ItemIO.OUTPUT )
	private Img< DoubleType > img;

	@Override
	public void run()
	{
		try
		{
			String dimFileContent = textService.open( dimFile ).trim();
			String[] split = dimFileContent.split( "," );
			long[] dims = new long[ split.length ];
			for ( int i = 0; i < split.length; i++ )
			{
				dims[ i ] = Long.parseLong( split[ i ] );
			}

			img = ArrayImgs.doubles( dims );
		}
		catch ( IOException exc )
		{
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
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
