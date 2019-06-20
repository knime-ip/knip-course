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
package org.knime.knip.course.node.exercise.addconstant;

import java.io.File;
import java.io.IOException;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.knip.core.KNIPGateway;
import org.scijava.log.LogService;

import net.imglib2.type.numeric.RealType;

/**
 * A node model for a node which adds a constant value to an image.
 *
 * @author Benjamin Wilhelm, KNIME GmbH, Konstanz, Germany
 */
public class AddConstantNodeModel<T extends RealType<T>> extends NodeModel {

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
	 * Create a settings model for the value to add.
	 *
	 * @return SettingsModelDouble
	 */
	protected static SettingsModelDouble createValue() {

		// TODO exercise 2.2: Create a settings model for the value which should be
		// added to the image

		throw new UnsupportedOperationException("Not implemented yet");
	}

	/**
	 * Settings model of the column selection.
	 */
	private final SettingsModelString m_columnSelection = createColumnSelection();

	/**
	 * Settings model of the number to add.
	 */
	private final SettingsModelDouble m_value = createValue();

	/**
	 * Constructor of the {@link AddConstantNodeModel}.
	 */
	protected AddConstantNodeModel() {
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
	 * Create the table spec of the output table.
	 *
	 * @return table spec with column "Add"
	 */
	private DataTableSpec createDataTableSpec() {

		// TODO exercise 3.3: Create an output DataTableSpec with one column {"Add"} of
		// type ImgPlusCell.TYPE.

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
			throws Exception {

		// TODO exercise 4.1: Use the ExecutionContext exec to create a new
		// BufferedDataContainer for the output table.

		// TODO exercise 4.2: Iterate over each row and add the value to the selected
		// image.
		// NOTE:
		// - Find the index of the selected cell using the specs of the input table.
		// - The cell has the type ImgPlusCell.
		// - Close the BufferedDataContainer before returning the table!

		// TODO exercise 5: Handle missing cells.

		// TODO exercise 6.1: Check if the execution was canceled.
		// NOTE: Use the ExecutionContext

		// TODO exercise 6.2: Report the execution progress.
		// NOTE: Use the ExecutionContext

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {

		// TODO exercise 2.3: save the column selection and value to the settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO exercise 2.4: validate the column selection and value from the settings

		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {

		// TODO exercise 2.5: load the column selection and value from the settings

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
