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

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

import net.imglib2.type.numeric.RealType;

/**
 * A node factory for a node which computes for each given ROI in a Labeling the
 * minimum and maximum radius from the centroid to the perimeter.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz
 */
public class MinMaxRadiusNodeFactory<T extends RealType<T>, O extends RealType<O>>
		extends NodeFactory<MinMaxRadiusNodeModel<T, O>> {

	@Override
	protected int getNrNodeViews() {
		// Number of views this node has
		return 0;
	}

	@Override
	public NodeView<MinMaxRadiusNodeModel<T, O>> createNodeView(int viewIndex, MinMaxRadiusNodeModel<T, O> nodeModel) {
		// Create a node view for each view
		return null;
	}

	@Override
	protected boolean hasDialog() {
		// This example node has a node dialog which is implemented in
		// MinMaxRadiusNodeDialog
		return true;
	}

	@Override
	protected NodeDialogPane createNodeDialogPane() {
		// Create the NodeDialog
		return new MinMaxRadiusNodeDialog();
	}

	@Override
	public MinMaxRadiusNodeModel<T, O> createNodeModel() {
		// Create the NodeModel
		return new MinMaxRadiusNodeModel<>();
	}
}
