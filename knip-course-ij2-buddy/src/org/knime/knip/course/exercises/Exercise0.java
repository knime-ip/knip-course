package org.knime.knip.course.exercises;

import org.knime.core.node.NodeLogger;
import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

/**
 * Computes the Pearson Correlation Coefficient for two input images.
 * 
 * @author Stefan Helfrich (University of Konstanz)
 */
@Plugin(menuPath = "Learnathon>Exercises>Exercise0", headless = true, type = Command.class)
public class Exercise0<T extends RealType<T> & NativeType<T>> implements Command {

        @SuppressWarnings("unused")
        private static final NodeLogger LOGGER = NodeLogger.getLogger(Exercise0.class);

        // TODO Define two input images and one double output

        @SuppressWarnings("unused")
        @Override
        public void run() {
                // TODO Initialize a Coloc_2 instance

                // Set default settings
                int indexMask = 0;
                int indexRegr = 0;
                boolean autoSavePdf = false;
                boolean displayImages = false;
                boolean displayShuffledCostes = false;
                boolean useLiCh1 = true;
                boolean useLiCh2 = true;
                boolean useLiICQ = true;
                boolean useSpearmanRank = true;
                boolean useManders = true;
                boolean useKendallTau = true;
                boolean useScatterplot = true;
                boolean useCostes = true;
                int psf = 3;
                int nrCostesRandomisations = 10;

                // Coloc_2 shows a user interface if java.awt.headless is false. To not show this window, set the system property.
                System.getProperties().setProperty("java.awt.headless", "true");

                // TODO Wrap input images as ImagePlus

                // TODO Initialize settings

                // TODO Compute a colocalization result

                // Use the following snippet to determine the interesting outputs
                /* logger.info(v.name + "=" + (v.isNumber ? v.number : v.value)); */

                // TODO Assign computed value to output

                // Reset system property
                System.getProperties().setProperty("java.awt.headless", "false");
        }

}
