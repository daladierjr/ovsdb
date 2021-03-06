/*
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Authors : Sam Hague
 */
package org.opendaylight.ovsdb.utils.mdsal.node;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(NodeUtils.class);

    public static String getId (String identifier) {
        String id = identifier;

        String[] pair = identifier.split("\\|");
        if ((pair.length > 1) && (pair[0].equals("OVS"))) {
            id = pair[1];
        }
        return id;
    }

    public static Node getOpenFlowNode (String identifier) {
        NodeId nodeId = new NodeId(identifier);
        NodeKey nodeKey = new NodeKey(nodeId);
        Node node = new NodeBuilder()
                .setId(nodeId)
                .setKey(nodeKey)
                .build();

        return node;
    }
}
