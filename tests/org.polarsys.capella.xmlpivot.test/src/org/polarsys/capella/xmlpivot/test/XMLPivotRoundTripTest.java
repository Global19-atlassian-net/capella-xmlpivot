/*******************************************************************************
 * Copyright (c) 2006, 2020 THALES GLOBAL SERVICES.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.xmlpivot.test;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.polarsys.capella.common.ef.ExecutionManager;
import org.polarsys.capella.common.ef.ExecutionManagerRegistry;
import org.polarsys.capella.common.ef.command.AbstractReadWriteCommand;

/**
 */
public abstract class XMLPivotRoundTripTest extends XMLPivotTest {

  public final static String DEFAULT_FIXTURES_LOCATION = "platform:/plugin/org.polarsys.capella.xmlpivot.test/fixtures/"; //$NON-NLS-1$

  protected Resource inputResource;
  protected Collection<EObject> inputElements;
  protected Collection<EObject> exportResult;
  protected Collection<EObject> importResult;
  protected XMLPivotTestUtil util;

  ExecutionManager manager;

  @Override
  @After
  public void tearDown() {
    inputElements = null;
    exportResult = null;
    importResult = null;
    inputResource.unload();
    manager.getEditingDomain().dispose();
  }

  @Override
  public void setUp() throws Exception {

    // first look for a model annotation on the test method
    Model model = getClass().getMethod(getName()).getAnnotation(Model.class);

    // a test can also define a model for all its test methods on the class itself
    if (model == null) {
      model = getClass().getAnnotation(Model.class);
    }

    if (model == null) {
      throw new IllegalArgumentException("No @Model annotation found to load input"); //$NON-NLS-1$
    }

    manager = ExecutionManagerRegistry.getInstance().addNewManager();

    URI uri = URI.createURI(model.uri());
    inputResource = manager.getEditingDomain().getResourceSet().getResource(uri, true);

    if ((model.elements() == null) || (model.elements().length == 0)) {
      inputElements = new ArrayList<EObject>(inputResource.getContents());
    } else {
      for (String id : model.elements()) {
        inputElements = new ArrayList<EObject>();
        EObject e = inputResource.getEObject(id);
        if (e == null) {
          throw new IllegalArgumentException("Could not find element with id: " + id); //$NON-NLS-1$
        }
        inputElements.add(e);
      }

    }
    exportResult = exporter(inputElements);
    importResult = importer(exportResult);
    // Imported objects need a resource in order to be able to resolve inverse references using EObjectExt
    final Resource importedResource = manager.getEditingDomain().getResourceSet().createResource(URI.createURI("importedResult.melodymodeller")); 
    AbstractReadWriteCommand cmd = new AbstractReadWriteCommand() {
		
		@Override
		public void run() {
			importedResource.getContents().addAll(importResult);
		}
	};
	manager.execute(cmd);
    util = new XMLPivotTestUtil(uri, inputElements, importResult);
  }

}
