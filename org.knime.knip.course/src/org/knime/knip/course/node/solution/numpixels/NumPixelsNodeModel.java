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
package org.knime.knip.course.node.solution.numpixels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataType;
import org.knime.core.data.MissingCell;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.LongCell;
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
import org.knime.knip.base.data.labeling.LabelingValue;
import org.knime.knip.base.node.NodeUtils;
import org.knime.knip.core.KNIPGateway;
import org.scijava.log.LogService;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.roi.labeling.LabelRegions;
import net.imglib2.roi.labeling.LabelingType;
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
		return new SettingsModelString("column_selection", "");
	}

	/**
	 * Create a settings model for the minimum segment size.
	 *
	 * @return SettingsModelLong
	 */
	protected static SettingsModelLong createMinSegmentSize() {
		return new SettingsModelLong("min_segment_size", 0);
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

		// Check table spec if column is available
		NodeUtils.autoColumnSelection(spec, m_columnSelection, LabelingValue.class, this.getClass());

		// If everything looks fine, create an output table spec
		return new DataTableSpec[] { createDataTableSpec() };
	}

	/**
	 * Create the table spec of the output table. In this case a new table with just
	 * one columns is generated.
	 *
	 * @return table spec with column "Segment size"
	 */
	private DataTableSpec createDataTableSpec() {
		return new DataTableSpec(new String[] { "Segment Size" }, new DataType[] { LongCell.TYPE });
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
			throws Exception {
		// The datatable passed by the first dataport
		final BufferedDataTable data = inData[0];

		// Variables to compute progress
		final double numRows = data.size();
		long currentRow = 0;
		exec.setProgress(0.0);

		// Create a container to store the output
		final BufferedDataContainer container = exec.createDataContainer(createDataTableSpec());

		for (final DataRow row : data) {
			// Check if execution got canceled
			exec.checkCanceled();

			// Get the data cell
			final DataCell cell = row.getCell(data.getSpec().findColumnIndex(m_columnSelection.getStringValue()));

			if (cell.isMissing()) {
				// If the cell is missing, insert missing cells and inform user via log
				container.addRowToTable(new DefaultRow(row.getKey(), new MissingCell(null)));
				LOGGER.warn("Missing cell in row " + row.getKey().getString() + ". Missing cell inserted.");
			} else {
				// Else compute the results
				addRows(container, (LabelingCell<L>) cell, row.getKey());
			}

			// Update progress indicator
			exec.setProgress(currentRow++ / numRows);
		}

		container.close();

		return new BufferedDataTable[] { container.getTable() };
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

		// Get the labeling from the cell
		final RandomAccessibleInterval<LabelingType<L>> labeling = cell.getLabeling();

		// Get the regions of the labeling
		final LabelRegions<L> regions = KNIPGateway.regions().regions(labeling);

		// The future results of the threads
		final List<Future<Optional<DataRow>>> futures = new ArrayList<>();

		// Loop over the regions and start the threads
		for (final LabelRegion<L> region : regions) {
			final Callable<Optional<DataRow>> createRowCallable = () -> {
				// Get the number of pixels for this region
				final long numPixels = region.size();

				// Check if the segment is large enough
				if (numPixels >= m_minSegmentSize.getLongValue()) {
					// Create a row and return it
					return Optional.of(new DefaultRow(key.getString() + "_Region" + region.getLabel().toString(),
							new LongCell(numPixels)));
				} else {
					return Optional.empty();
				}
			};
			futures.add(KNIPGateway.threads().run(createRowCallable));
		}

		// Wait for the threads and collect the results
		for (final Future<Optional<DataRow>> future : futures) {
			try {
				// Collect the result and add it to the table if it is present
				final Optional<DataRow> result = future.get();
				if (result.isPresent()) {
					container.addRowToTable(result.get());
				}
			} catch (final ExecutionException | InterruptedException e) {
				LOGGER.error(e);
			}
		}
	}

	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) {
		m_columnSelection.saveSettingsTo(settings);
		m_minSegmentSize.saveSettingsTo(settings);
	}

	@Override
	protected void validateSettings(NodeSettingsRO settings) throws InvalidSettingsException {
		m_columnSelection.validateSettings(settings);
		m_minSegmentSize.validateSettings(settings);
	}

	@Override
	protected void loadValidatedSettingsFrom(NodeSettingsRO settings) throws InvalidSettingsException {
		m_columnSelection.loadSettingsFrom(settings);
		m_minSegmentSize.loadSettingsFrom(settings);
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
