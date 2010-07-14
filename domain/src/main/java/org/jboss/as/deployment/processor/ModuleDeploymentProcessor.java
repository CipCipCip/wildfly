/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.deployment.processor;

import org.jboss.as.deployment.DeploymentPhases;
import org.jboss.as.deployment.attachment.Dependencies;
<<<<<<< HEAD
import org.jboss.as.deployment.item.ModuleDeploymentItem;
=======
import org.jboss.as.deployment.descriptor.ModuleConfig;
>>>>>>> bf5e5784e281e51c89497aee3feab2adcaf0b0ea
import org.jboss.as.deployment.unit.DeploymentUnitContext;
import org.jboss.as.deployment.unit.DeploymentUnitProcessingException;
import org.jboss.as.deployment.unit.DeploymentUnitProcessor;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.vfs.VirtualFile;

<<<<<<< HEAD
=======
import static org.jboss.as.deployment.attachment.Dependencies.getAttachedDependencies;
>>>>>>> bf5e5784e281e51c89497aee3feab2adcaf0b0ea
import static org.jboss.as.deployment.attachment.VirtualFileAttachment.getVirtualFileAttachment;

/**
 * DeploymentUnitProcessor capable of reading dependency information for a manifest and creating a ModuleDeploymentItem.
 *
 * @author John E. Bailey
 */
public class ModuleDeploymentProcessor implements DeploymentUnitProcessor {
    public static final long PRIORITY = DeploymentPhases.MODULARIZE.plus(100L);
    private static final ModuleConfig.Dependency[] NO_DEPS = new ModuleConfig.Dependency[0];

    @Override
    public void processDeployment(DeploymentUnitContext context) throws DeploymentUnitProcessingException {
        final VirtualFile deploymentRoot = getVirtualFileAttachment(context);
        final ModuleIdentifier moduleIdentifier = new ModuleIdentifier("org.jboss.deployments", context.getName(), "noversion");
        final ModuleConfig.ResourceRoot[] resourceRoots = new ModuleConfig.ResourceRoot[]{new ModuleConfig.ResourceRoot(deploymentRoot)};
        final Dependencies dependenciesAttachment = getAttachedDependencies(context);
        final ModuleConfig.Dependency[] dependencies = dependenciesAttachment != null ? dependenciesAttachment.getDependencies() : NO_DEPS;
        final ModuleConfig moduleConfig = new ModuleConfig(moduleIdentifier, dependencies, resourceRoots);
        context.putAttachment(ModuleConfig.ATTACHMENT_KEY, moduleConfig);
    }

}
