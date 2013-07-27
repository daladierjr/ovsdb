package org.opendaylight.ovsdb;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.opendaylight.controller.sal.core.Node;
import org.opendaylight.controller.sal.core.NodeConnector;
import org.opendaylight.ovsdb.database.Uuid;
import org.opendaylight.ovsdb.internal.*;
import org.opendaylight.ovsdb.sal.connection.ConnectionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvsdbTestAddBridge {
    private static final Logger logger = LoggerFactory
            .getLogger(OvsdbTestAddBridge.class);
    @Test
    public void addBridge() throws Throwable{
        Node.NodeIDType.registerIDType("OVS", String.class);
        NodeConnector.NodeConnectorIDType.registerIDType("OVS", String.class, "OVS");

        ConnectionService connectionService = new ConnectionService();
        connectionService.init();
        String identifier = "TEST";
        Map<ConnectionConstants, String> params = new HashMap<ConnectionConstants, String>();
        params.put(ConnectionConstants.ADDRESS, "192.168.56.101");

        Node node = connectionService.connect(identifier, params);
        if(node == null){
            logger.error("Could not connect to ovsdb server");
            return;
        }
        ConfigurationService configurationService = new ConfigurationService();
        configurationService.setConnectionServiceInternal(connectionService);
        configurationService.createBridgeDomain(node, "JUNIT_BRIDGE_TEST");
    }

}