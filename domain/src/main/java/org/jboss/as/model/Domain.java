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

package org.jboss.as.model;

import java.util.Collection;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import org.jboss.msc.service.Location;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

/**
 * The JBoss AS Domain state.  An instance of this class represents the complete running state of the domain.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class Domain extends AbstractModel<Domain> {

    private static final long serialVersionUID = 5516070442013067881L;

    /**
     * The namespace of version 1.0 of the domain model.
     */
    public static final String NAMESPACE_1_0 = "urn:jboss:domain:1.0";
    /**
     * The default namespace.
     */
    public static final String NAMESPACE = NAMESPACE_1_0;

    /**
     * The set of recognized domain namespaces.
     */
    public static final Set<String> NAMESPACES = Collections.singleton(NAMESPACE_1_0);

    private final NavigableMap<String, ExtensionElement> extensions = new TreeMap<String, ExtensionElement>();
    private final NavigableMap<String, ServerGroupElement> serverGroups = new TreeMap<String, ServerGroupElement>();
    private final NavigableMap<String, DeploymentUnitElement> deployments = new TreeMap<String, DeploymentUnitElement>();

    private final PropertiesElement systemProperties = new PropertiesElement(null);

    /**
     * Construct a new instance.
     *
     * @param location the declaration location of the domain element
     */
    public Domain(final Location location) {
        super(location);
    }

    /** {@inheritDoc} */
    public long elementHash() {
        long hash = 0L;
        hash = calculateElementHashOf(extensions.values(), hash);
        hash = calculateElementHashOf(serverGroups.values(), hash);
        hash = calculateElementHashOf(deployments.values(), hash);
        hash = Long.rotateLeft(hash, 1) ^ systemProperties.elementHash();
        return hash;
    }

    /** {@inheritDoc} */
    protected void appendDifference(final Collection<AbstractModelUpdate<Domain>> target, final Domain other) {
        calculateDifference(target, extensions, other.extensions, new DifferenceHandler<String, ExtensionElement, Domain>() {
            public void handleAdd(final Collection<AbstractModelUpdate<Domain>> target, final String name, final ExtensionElement newElement) {

            }

            public void handleRemove(final Collection<AbstractModelUpdate<Domain>> target, final String name, final ExtensionElement oldElement) {

            }

            public void handleChange(final Collection<AbstractModelUpdate<Domain>> target, final String name, final ExtensionElement oldElement, final ExtensionElement newElement) {
                // not possible
                throw new IllegalStateException();
            }
        });
        calculateDifference(target, serverGroups, other.serverGroups, new DifferenceHandler<String, ServerGroupElement, Domain>() {
            public void handleAdd(final Collection<AbstractModelUpdate<Domain>> target, final String name, final ServerGroupElement newElement) {
                // todo add-server-group operation
            }

            public void handleRemove(final Collection<AbstractModelUpdate<Domain>> target, final String name, final ServerGroupElement oldElement) {
                // todo remove-server-group operation
            }

            public void handleChange(final Collection<AbstractModelUpdate<Domain>> target, final String name, final ServerGroupElement oldElement, final ServerGroupElement newElement) {
                // todo update-server-group operation
                oldElement.appendDifference(null, newElement);
            }
        });
        calculateDifference(target, deployments, other.deployments, new DifferenceHandler<String, DeploymentUnitElement, Domain>() {
            public void handleAdd(final Collection<AbstractModelUpdate<Domain>> target, final String name, final DeploymentUnitElement newElement) {
                // todo deploy
            }

            public void handleRemove(final Collection<AbstractModelUpdate<Domain>> target, final String name, final DeploymentUnitElement oldElement) {
                // todo undeploy
            }

            public void handleChange(final Collection<AbstractModelUpdate<Domain>> target, final String name, final DeploymentUnitElement oldElement, final DeploymentUnitElement newElement) {
                // todo redeploy...? or maybe just modify stuff
            }
        });
        // todo enclosing diff item
        systemProperties.appendDifference(null, other.systemProperties);
    }

    /** {@inheritDoc} */
    protected Class<Domain> getElementClass() {
        return Domain.class;
    }

    /** {@inheritDoc} */
    protected QName getElementName() {
        return new QName(NAMESPACE, "domain");
    }

    /** {@inheritDoc} */
    public void writeContent(final XMLExtendedStreamWriter streamWriter) throws XMLStreamException {
        if (! extensions.isEmpty()) for (ExtensionElement element : extensions.values()) {
            streamWriter.writeStartElement("extension");
            element.writeContent(streamWriter);
        }
        if (systemProperties.size() > 0) {
            streamWriter.writeStartElement("system-properties");
            systemProperties.writeContent(streamWriter);
        }
        if (! serverGroups.isEmpty()) for (ServerGroupElement element : serverGroups.values()) {
            streamWriter.writeStartElement("server-group");
            element.writeContent(streamWriter);
        }
        if (! deployments.isEmpty()) for (DeploymentUnitElement element : deployments.values()) {
            streamWriter.writeStartElement("deployment");
            element.writeContent(streamWriter);
        }
        streamWriter.writeEndElement();
    }
}
