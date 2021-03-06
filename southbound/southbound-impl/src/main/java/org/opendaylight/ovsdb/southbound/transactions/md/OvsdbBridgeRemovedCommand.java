package org.opendaylight.ovsdb.southbound.transactions.md;

import java.util.Collection;

import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.ovsdb.lib.message.TableUpdates;
import org.opendaylight.ovsdb.lib.schema.DatabaseSchema;
import org.opendaylight.ovsdb.lib.schema.typed.TyperUtils;
import org.opendaylight.ovsdb.schema.openvswitch.Bridge;
import org.opendaylight.ovsdb.southbound.OvsdbConnectionInstance;
import org.opendaylight.ovsdb.southbound.SouthboundMapper;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.ovsdb.rev150105.OvsdbBridgeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.ovsdb.rev150105.OvsdbNodeAugmentation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.ovsdb.rev150105.ovsdb.node.attributes.ManagedNodeEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.ovsdb.rev150105.ovsdb.node.attributes.ManagedNodeEntryKey;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Node;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvsdbBridgeRemovedCommand extends AbstractTransactionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(OvsdbBridgeRemovedCommand.class);

    public OvsdbBridgeRemovedCommand(OvsdbConnectionInstance key, TableUpdates updates,
            DatabaseSchema dbSchema) {
        super(key,updates,dbSchema);
    }

    @Override
    public void execute(ReadWriteTransaction transaction) {
        Collection<Bridge> removedRows = TyperUtils.extractRowsRemoved(Bridge.class,
                getUpdates(), getDbSchema()).values();
        for (Bridge bridge : removedRows) {
            InstanceIdentifier<Node> bridgeIid = SouthboundMapper.createInstanceIdentifier(getOvsdbConnectionInstance(),
                    bridge);
            InstanceIdentifier<ManagedNodeEntry> mnIid = getOvsdbConnectionInstance().getInstanceIdentifier()
                    .augmentation(OvsdbNodeAugmentation.class)
                    .child(ManagedNodeEntry.class, new ManagedNodeEntryKey(new OvsdbBridgeRef(bridgeIid)));
            // TODO handle removal of reference to managed node from model
            transaction.delete(LogicalDatastoreType.OPERATIONAL, bridgeIid);
            transaction.delete(LogicalDatastoreType.OPERATIONAL, mnIid);
        }
    }

}
