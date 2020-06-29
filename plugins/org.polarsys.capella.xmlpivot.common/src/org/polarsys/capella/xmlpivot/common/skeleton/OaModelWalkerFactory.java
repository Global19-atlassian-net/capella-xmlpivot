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
package org.polarsys.capella.xmlpivot.common.skeleton;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import org.polarsys.capella.core.data.oa.util.OaAdapterFactory;

/**
 *
 */
public class OaModelWalkerFactory extends OaAdapterFactory implements ComposeableAdapterFactory {

  private final OperationalAnalysisWalker operationalAnalysisWalker;
  private final OperationalActivityPkgWalker operationalActivityPkgWalker;
  
  private ComposeableAdapterFactory parentAdapterFactory;

  /**
   * @param operationalAnalysisWalker_p
   * @param operationalActivityPkgWalker_p
   */
  public OaModelWalkerFactory(OperationalAnalysisWalker operationalAnalysisWalker_p,
      OperationalActivityPkgWalker operationalActivityPkgWalker_p) {
    operationalAnalysisWalker = operationalAnalysisWalker_p;
    operationalActivityPkgWalker = operationalActivityPkgWalker_p;
  }

  @Override
  public boolean isFactoryForType(Object object_p) {
    return super.isFactoryForType(object_p) || object_p == ModelWalker.class;
  }

  @Override
  public Adapter createOperationalAnalysisAdapter() {
    return operationalAnalysisWalker;
  }
  
  @Override
  public Adapter createOperationalActivityPkgAdapter(){
    return operationalActivityPkgWalker;
  }
 
  @Override
  protected void associate(Adapter adapter_p, Notifier target_p) {
     /* we don't want to associate ever */
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ComposeableAdapterFactory getRootAdapterFactory() {
    return parentAdapterFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory_p) {
    parentAdapterFactory = parentAdapterFactory_p;
  }

  
}
