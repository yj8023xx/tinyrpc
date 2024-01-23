package com.smallc.xrpc.network.transport.rdma;

import com.ibm.disni.RdmaEndpointFactory;
import com.ibm.disni.verbs.RdmaCmId;

import java.io.IOException;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/11
 * @since com.smallc.xrpc.server.transport.TransportServer.network.transport.rdma
 */
public class XRpcClientGroup extends XRpcEndpointGroup<XRpcClientEndpoint> {

    private XRpcClientGroup(int timeout) throws IOException {
        super(timeout);
    }

    private XRpcClientGroup(int timeout, int threadCount) throws IOException {
        super(timeout, threadCount);
    }

    private XRpcClientGroup(int timeout, long[] affinities) throws IOException {
        super(timeout, affinities);
    }

    public static XRpcClientGroup createClientGroup(int timeout) throws IOException {
        XRpcClientGroup group = new XRpcClientGroup(timeout);
        group.init(new XRpcClientFactory(group));
        return group;
    }

    public static XRpcClientGroup createClientGroup(int timeout, int threadCount) throws IOException {
        XRpcClientGroup group = new XRpcClientGroup(timeout, threadCount);
        group.init(new XRpcClientFactory(group));
        return group;
    }

    public static XRpcClientGroup createClientGroup(int timeout, long[] affinities) throws IOException {
        XRpcClientGroup group = new XRpcClientGroup(timeout, affinities);
        group.init(new XRpcClientFactory(group));
        return group;
    }

    @Override
    public void allocateResources(XRpcClientEndpoint endPoint) throws Exception {
        endPoint.allocateResources();
    }

    @Override
    public XRpcClientGroup option(RdmaOption option, int value) {
        return (XRpcClientGroup) super.option(option, value);
    }

    public static class XRpcClientFactory implements RdmaEndpointFactory<XRpcClientEndpoint> {
        private XRpcClientGroup group;

        public XRpcClientFactory(XRpcClientGroup group) {
            this.group = group;
        }

        @Override
        public XRpcClientEndpoint createEndpoint(RdmaCmId id, boolean serverSide) throws IOException {
            return new XRpcClientEndpoint(group, id, serverSide);
        }
    }

}