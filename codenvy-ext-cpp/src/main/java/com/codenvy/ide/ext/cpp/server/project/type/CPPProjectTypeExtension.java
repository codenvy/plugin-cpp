/*******************************************************************************
 * Copyright (c) 2012-2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.cpp.server.project.type;

import com.codenvy.api.project.server.ProjectTemplateDescriptionLoader;
import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.server.ProjectTypeExtension;
import com.codenvy.api.project.shared.Attribute;
import com.codenvy.api.project.shared.ProjectTemplateDescription;
import com.codenvy.api.project.shared.ProjectType;
import com.codenvy.ide.Constants;
import com.codenvy.ide.ext.cpp.shared.ProjectAttributes;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** @author Vladyslav Zhukovskii */
@Singleton
public class CPPProjectTypeExtension implements ProjectTypeExtension {
    private static final Logger LOG = LoggerFactory.getLogger(CPPProjectTypeExtension.class);
    private final ProjectType                      projectType;
    private final ProjectTemplateDescriptionLoader projectTemplateDescriptionLoader;

    @Inject
    public CPPProjectTypeExtension(ProjectTypeDescriptionRegistry registry,
                                   ProjectTemplateDescriptionLoader projectTemplateDescriptionLoader) {
        this.projectTemplateDescriptionLoader = projectTemplateDescriptionLoader;
        this.projectType = new ProjectType(ProjectAttributes.CPP_ID, ProjectAttributes.CPP_NAME, ProjectAttributes.CPP_CATEGORY, null,
                                           ProjectAttributes.CPP_DEFAULT_RUNNER);
        registry.registerProjectType(this);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectType getProjectType() {
        return projectType;
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribute> getPredefinedAttributes() {
        final List<Attribute> list = new ArrayList<>(1);
        list.add(new Attribute(Constants.LANGUAGE, ProjectAttributes.CPP_ID));
        return list;
    }

    /** {@inheritDoc} */
    @Override
    public List<ProjectTemplateDescription> getTemplates() {
        final List<ProjectTemplateDescription> list = new ArrayList<>();
        try {
            projectTemplateDescriptionLoader.load(getProjectType().getId(), list);
        } catch (IOException e) {
            LOG.error("Unable to load external templates for project type: {}", getProjectType().getId());
        }
        return list;
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, String> getIconRegistry() {
        return null;
    }
}
