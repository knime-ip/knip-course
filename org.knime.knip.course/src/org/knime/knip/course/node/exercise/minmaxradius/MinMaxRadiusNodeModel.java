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
package org.knime.knip.course.node.exercise.minmaxradius;

import java.io.File;
import java.io.IOException;

import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.knip.base.data.labeling.LabelingCell;
import org.knime.knip.base.node.nodesettings.SettingsModelDimSelection;
import org.knime.knip.core.KNIPGateway;
import org.scijava.log.LogService;

import net.imagej.ops.special.function.UnaryFunctionOp;
import net.imglib2.RealLocalizable;
import net.imglib2.roi.geom.real.Polygon2D;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.type.numeric.RealType;

/**
 * A node model for a node which computes for each given ROI in a Labeling the
 * minimum and maximum radius from the centroid to the perimeter.
 *
 * @author Tim-Oliver Buchholz, University of Konstanz
 * @author Benjamin Wilhelm, KNIME GmbH, Konstanz, Germany
 */
public class MinMaxRadiusNodeModel<L extends Comparable<L>, O extends RealType<O>> extends NodeModel {

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
	 * Create a settings model for the dimension selection component.
	 * 
	 * @return SettingsModelDimSelection
	 */
	protected static SettingsModelDimSelection createDimSelection() {

		// TODO exercise 2.2: Create a settings model for dimension selection

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Settings model of the column selection.
	 */
	private SettingsModelString m_columnSelection = createColumnSelection();

	/**
	 * Settings model of the dimension selection.
	 */
	private SettingsModelDimSelection m_dimSelection = createDimSelection();

	/**
	 * The centroid function from imagej-ops.
	 */
	private UnaryFunctionOp<LabelRegion<L>, RealLocalizable> m_centroidFunction;

	/**
	 * The LabelRegion to Polygon converter from imagej-ops.
	 */
	private UnaryFunctionOp<LabelRegion<L>, Polygon2D> m_converter;

	/**
	 * Constructor of the MinMaxRadiusNodeModel.
	 */
	protected MinMaxRadiusNodeModel() {
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
	 * two columns is generated.
	 * 
	 * @return table spec with columns "Min Radius" and "Max Radius"
	 */
	private DataTableSpec createDataTableSpec() {

		// TODO exercise 3.3: Create an output DataTableSpec with two columns {"Max
		// Radius", "Min Radius"} of type DoubleCell.TYPE

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

		// TODO exercise 7: Handle missing cells.

		// TODO exercise 8.1: Check if the execution was canceled.
		// NOTE: Use the ExecutionContext

		// TODO exercise 8.2: Report the execution progress.
		// NOTE: Use the ExecutionContext

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Initiate ops. This is not possible during
	 * {@link MinMaxRadiusNodeModel#configure(DataTableSpec[])} since the data is
	 * not available at the time. The ops are initiated with the first actual
	 * data-instance.
	 * 
	 * @param region the first real LabelRegion
	 */
	private void init(final LabelRegion<L> region) {

		// TODO exercise 5.3: Initialize centroidFunction and converter.
		// Note: OpService is available via KNIPGateway.ops().

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Add for each LabelRegion in a Labeling a new row with the min and max radius.
	 * 
	 * In this method, the ROIs get sliced to 2D slices which then will be
	 * processed. Since a Label can result in many slices of any size, the slices
	 * are processed concurrently.
	 * 
	 * @param container to store the result
	 * @param cell      the current cell containing the Labeling
	 * @param key       the current row key
	 * @throws InvalidSettingsException if it is not possible to create 2D slices
	 */
	private void addRows(final BufferedDataContainer container, final LabelingCell<L> cell, final RowKey key)
			throws InvalidSettingsException {

		// TODO exercise 5.1: Slice the labeling based on the selected dimensions of
		// dimSelection.

		// TODO exercise 5.2: Initialize centroidFunction and converter.

		// TODO exercise 5.4: Submit a Callable to KNIPGateway.threads() for each slice
		// and compute min max radius.

		// TODO exercise 5.5: Collect the results and add them to the container.
		// Note: Make sure that each row has a unique rowid: oldRowID + label + slice

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Compute the min and max radius for one ROI.
	 * 
	 * @param region the ROI
	 * @return the min and max radius
	 */
	private DoubleCell[] computeMinMaxRadius(final LabelRegion<L> region) {

		// TODO exercise 6: Compute min and max radius of the given region.
		// NOTE:
		// - Use centroidFunction to get the centroid.
		// - Use converter to get the polygon describing the region.

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {

		// TODO exercise 2.3: save the column selection and dimension selection to the
		// settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO exercise 2.4: validate the column selection and dimension selection from
		// the settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO exercise 2.5: load the column selection and dimension selection from the
		// settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void loadInternals(final File nodeInternDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// nothing to do
	}

	@Override
	protected void saveInternals(final File nodeInternDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// nothing to do
	}

	@Override
	protected void reset() {
		// nothing to do
	}
}
