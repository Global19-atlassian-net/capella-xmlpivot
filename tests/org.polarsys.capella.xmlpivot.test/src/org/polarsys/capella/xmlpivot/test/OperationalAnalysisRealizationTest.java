/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *    
 *  Contributors:
 *     Thales - initial API and implementation
 ******************************************************************************/
package org.polarsys.capella.xmlpivot.test;

import org.polarsys.capella.core.data.ctx.CtxPackage;


/**
 * @generated
 */
public class OperationalAnalysisRealizationTest extends XMLPivotRoundTripTest {

  @Model(
      uri="platform:/plugin/org.polarsys.capella.xmlpivot.test/fixtures/testRoundTrip1/testRoundTrip1.melodymodeller"
  )
  public void testOperationalAnalysisRealizationRoundTrip(){
    util.assertAllLinksRecreated(CtxPackage.Literals.OPERATIONAL_ANALYSIS_REALIZATION);
  }

  
}
