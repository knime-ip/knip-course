/*
 * ------------------------------------------------------------------------
 *
 *  Copyright (C) 2003 - 2019
 *  University of Konstanz, Germany and
 *  KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * --------------------------------------------------------------------- *
 *
 */
package org.knime.knip.course.node.exercise.numpixels;

import java.io.File;
import java.io.IOException;

import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelLong;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.knip.base.data.labeling.LabelingCell;
import org.knime.knip.core.KNIPGateway;
import org.scijava.log.LogService;

import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.type.numeric.RealType;

/**
 * A node model for a node which computes for each ROI in a Labeling the number
 * of pixels contained in the ROI.
 *
 * @author Benjamin Wilhelm, KNIME GmbH, Konstanz, Germany
 * @author Tim-Oliver Buchholz, University of Konstanz
 */
public class NumPixelsNodeModel<L extends Comparable<L>, O extends RealType<O>> extends NodeModel {

	/**
	 * KNIP logger instance.
	 */
	private static final LogService LOGGER = KNIPGateway.log();

	/**
	 * Create a settings model for the column selection component.
	 *
	 * @return SettingsModelString
	 */
	protected static SettingsModelString createColumnSelection() {

		// TODO exercise 2.1: Create a settings model for the column selection.

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Create a settings model for the minimum segment size.
	 *
	 * @return SettingsModelLong
	 */
	protected static SettingsModelLong createMinSegmentSize() {

		// TODO exercise 2.2: Create a settings model for minimum segment size

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Settings model of the column selection.
	 */
	private SettingsModelString m_columnSelection = createColumnSelection();

	/**
	 * Settings model of the minimum segment size.
	 */
	private SettingsModelLong m_minSegmentSize = createMinSegmentSize();

	/**
	 * Constructor of the {@link NumPixelsNodeModel}.
	 */
	protected NumPixelsNodeModel() {
		// One input and one output
		super(1, 1);
	}

	@Override
	protected DataTableSpec[] configure(final DataTableSpec[] inSpecs) throws InvalidSettingsException {
		final DataTableSpec spec = inSpecs[0];

		// TODO exercise 3.1: Check if the selected column is part of the input table.
		// NOTE: use NodeUtils.

		// TODO exercise 3.2: Create the output table spec (#createDataTableSpec())

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Create the table spec of the output table. In this case a new table with just
	 * one columns is generated.
	 *
	 * @return table spec with column "Segment size"
	 */
	private DataTableSpec createDataTableSpec() {

		// TODO exercise 3.3: Create an output DataTableSpec with one columns {"Segment
		// Size"} of type LongCell.TYPE

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
			throws Exception {

		// TODO exercise 4.1: Use the ExecutionContext exec to create a new
		// BufferedDataContainer for the output table.

		// TODO exercise 4.2: Iterate over each row and process it (#addRows)
		// NOTE:
		// - Find the index of the selected cell using the specs of the input table.
		// - Close the BufferedDataContainer before returning the table!

		// TODO exercise 6: Handle missing cells.

		// TODO exercise 7.1: Check if the execution was canceled.
		// NOTE: Use the ExecutionContext

		// TODO exercise 7.2: Report the execution progress.
		// NOTE: Use the ExecutionContext

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Add for each LabelRegion in a Labeling a new row with the number of pixels.
	 *
	 * In this method, the number of pixels for each ROI is computed. Since the
	 * Labeling can have many ROIs, they are processed concurrently.
	 * 
	 * @param container to store the result
	 * @param cell      the current cell containing the Labeling
	 * @param key       the current row key
	 */
	private void addRows(final BufferedDataContainer container, final LabelingCell<L> cell, final RowKey key) {

		// TODO exercise 5: Loop over the regions of the labeling and add rows with
		// the number of pixels
		// NOTE:
		// - Check if the segment is large enough
		// - Make sure that each row has a unique row id

		// TODO exercise 8: Use threads to count the number of pixels in multiple rows
		// at the same time

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) {

		// TODO exercise 2.3: save the column selection and the minimum segment size to
		// the settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void validateSettings(NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO exercise 2.4: validate the the column selection and the minimum segment
		// size from the settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void loadValidatedSettingsFrom(NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO exercise 2.5: load the column selection and the minimum segment size
		// from the settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void loadInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// nothing to do
	}

	@Override
	protected void saveInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// nothing to do
	}

	@Override
	protected void reset() {
		// nothing to do
	}
}
