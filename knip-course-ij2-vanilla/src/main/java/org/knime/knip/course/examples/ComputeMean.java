package org.knime.knip.course.examples;

import net.imagej.ops.OpService;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * <b>Exercise 0:</b>
 * <ul>
 * <li>Implement a {@code Command} that computes the mean intensity from an
 * image
 * </ul>
 */
@Plugin( type = Command.class, headless = true, menuPath = "Learnathon>Examples>Compute Mean" )
public class ComputeMean< T extends RealType< T > > implements Command
{

	@Parameter
	private Img< T > input;

	@Parameter
	private double mean;

	@Parameter
	private OpService opService;

	@Override
	public void run()
	{
		mean = opService.stats().mean( new DoubleType(), input ).get();
	}

}
