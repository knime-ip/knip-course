/*
 * ------------------------------------------------------------------------
 *
 *  Copyright (C) 2003 - 2017
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
package org.knime.knip.course.node.addconstant;

import java.io.File;
import java.io.IOException;

import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataType;
import org.knime.core.data.MissingCell;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.knip.base.data.img.ImgPlusCell;
import org.knime.knip.base.data.img.ImgPlusCellFactory;
import org.knime.knip.base.data.img.ImgPlusValue;
import org.knime.knip.base.node.NodeUtils;

import net.imagej.ImgPlus;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;

/**
 * Node that adds a constant value to an image.
 *
 * @author Benjamin Wilhelm, KNIME GmbH, Konstanz, Germany
 */
public class AddConstantNodeModel<T extends RealType<T>> extends NodeModel {

	// The logger instance
	private static final NodeLogger LOGGER = NodeLogger.getLogger(AddConstantNodeModel.class);

	private final SettingsModelDouble m_value = createValue();

	private final SettingsModelString m_columnSelection = createColumnSelection();

	protected static SettingsModelDouble createValue() {
		return new SettingsModelDouble("value", 1.0);
	}

	protected static SettingsModelString createColumnSelection() {
		return new SettingsModelString("columnSelection", "");
	}

	/**
	 * Constructor of the AddConstantNodeModel.
	 */
	protected AddConstantNodeModel() {
		super(1, 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DataTableSpec[] configure(DataTableSpec[] inSpecs) throws InvalidSettingsException {
		final DataTableSpec spec = inSpecs[0];

		// Check table spec if column is available.
		NodeUtils.autoColumnSelection(spec, m_columnSelection, ImgPlusValue.class, this.getClass());

		// If everything looks fine, create an output table spec.
		return new DataTableSpec[] { createDataTableSpec() };
	}

	/**
	 * Create the table spec of the output table.
	 *
	 * @return table spec with column "Add"
	 */
	private DataTableSpec createDataTableSpec() {
		return new DataTableSpec(new String[] { "Add" }, new DataType[] { ImgPlusCell.TYPE });
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected BufferedDataTable[] execute(BufferedDataTable[] inData, ExecutionContext exec) throws Exception {
		// The datatable passed by the first dataport.
		final BufferedDataTable data = inData[0];

		// Variables to compute progress.
		final double numRows = data.size();
		long currentRow = 0;

		// Create a container to store the output.
		final BufferedDataContainer container = exec.createDataContainer(createDataTableSpec());

		for (final DataRow row : data) {
			// Check if execution got canceled.
			exec.checkCanceled();

			// Get the data cell.
			final ImgPlusCell<T> cell = (ImgPlusCell<T>) row
					.getCell(data.getSpec().findColumnIndex(m_columnSelection.getStringValue()));
			final ImgPlusCellFactory imgPlusCellFactory = new ImgPlusCellFactory(exec);

			if (cell.isMissing()) {
				// If the cell is missing, insert missing cells and inform user
				// via log.
				container.addRowToTable(new DefaultRow(row.getKey(), new MissingCell(null), new MissingCell(null)));
				LOGGER.warn("Missing cell in row " + row.getKey().getString() + ". Missing cell inserted.");
			} else {
				// Else add the constant value
				// With iterator and randomAccess
				final ImgPlus<T> original = cell.getImgPlus();
				final Img<T> result = original.factory().create(original);

				final Cursor<T> originalC = original.localizingCursor();
				final RandomAccess<T> resultRA = result.randomAccess();

				T val = original.firstElement();
				val.setReal(m_value.getDoubleValue());

				while (originalC.hasNext()) {
					originalC.fwd();
					resultRA.setPosition(originalC);

					resultRA.get().set(originalC.get());
					resultRA.get().add(val);
				}

				container.addRowToTable(
						new DefaultRow(row.getKey(), imgPlusCellFactory.createCell(new ImgPlus<>(result))));
			}

			// Update progress indicator.
			exec.setProgress(currentRow++ / numRows);
		}

		container.close();

		return new BufferedDataTable[] { container.getTable() };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) {
		m_value.saveSettingsTo(settings);
		m_columnSelection.saveSettingsTo(settings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateSettings(NodeSettingsRO settings) throws InvalidSettingsException {
		m_value.validateSettings(settings);
		m_columnSelection.validateSettings(settings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadValidatedSettingsFrom(NodeSettingsRO settings) throws InvalidSettingsException {
		m_value.loadSettingsFrom(settings);
		m_columnSelection.loadSettingsFrom(settings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void reset() {
		// nothing to do
	}
}
