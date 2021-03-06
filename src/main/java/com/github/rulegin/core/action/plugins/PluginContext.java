/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rulegin.core.action.plugins;


import com.github.rulegin.actors.msg.core.TimeoutMsg;
import com.github.rulegin.actors.msg.plugin.aware.PluginWebsocketMsg;
import com.github.rulegin.actors.msg.plugin.torule.PluginToRuleMsg;
import com.github.rulegin.actors.msg.ws.PluginWebsocketSessionRef;
import com.github.rulegin.actors.service.cluster.ServerAddress;
import com.github.rulegin.common.data.Device;
import com.github.rulegin.common.data.id.DeviceId;
import com.github.rulegin.common.data.id.EntityId;
import com.github.rulegin.common.data.id.PluginId;
import com.github.rulegin.common.data.id.UserId;
import com.github.rulegin.common.data.kv.AttributeKvEntry;
import com.github.rulegin.common.data.kv.TsKvEntry;
import com.github.rulegin.common.data.kv.TsKvQuery;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PluginContext {

    PluginId getPluginId();

    void reply(PluginToRuleMsg<?> msg);

    void checkAccess(DeviceId deviceId, PluginCallback<Void> callback);

    Optional<PluginApiCallSecurityContext> getSecurityCtx();

    void persistError(String method, Exception e);

    /*
        Device RPC API
     */

    Optional<ServerAddress> resolve(EntityId entityId);

    void getDevice(DeviceId deviceId, PluginCallback<Device> pluginCallback);

    //void sendRpcRequest(ToDeviceRpcRequest msg);

    void scheduleTimeoutMsg(TimeoutMsg<?> timeoutMsg);


    /*
        Websocket API
     */

    void send(PluginWebsocketMsg<?> wsMsg) throws IOException;

    void close(PluginWebsocketSessionRef sessionRef) throws IOException;
    /*
        Plugin RPC API
     */

   // void sendPluginRpcMsg(RpcMsg msg);

    /*
        Timeseries API
     */


    void saveTsData(EntityId entityId, TsKvEntry entry, PluginCallback<Void> callback);

    void saveTsData(EntityId entityId, List<TsKvEntry> entries, PluginCallback<Void> callback);

    void saveTsData(EntityId deviceId, List<TsKvEntry> entries, long ttl, PluginCallback<Void> pluginCallback);

    void loadTimeseries(EntityId entityId, List<TsKvQuery> queries, PluginCallback<List<TsKvEntry>> callback);

    void loadLatestTimeseries(EntityId entityId, Collection<String> keys, PluginCallback<List<TsKvEntry>> callback);

    void loadLatestTimeseries(EntityId entityId, PluginCallback<List<TsKvEntry>> callback);

    /*
        Attributes API
     */

    void saveAttributes(EntityId entityId, String attributeType, List<AttributeKvEntry> attributes, PluginCallback<Void> callback);

    void removeAttributes( EntityId entityId, String scope, List<String> attributeKeys, PluginCallback<Void> callback);

    void loadAttribute(EntityId entityId, String attributeType, String attributeKey, PluginCallback<Optional<AttributeKvEntry>> callback);

    void loadAttributes(EntityId entityId, String attributeType, Collection<String> attributeKeys, PluginCallback<List<AttributeKvEntry>> callback);

    void loadAttributes(EntityId entityId, String attributeType, PluginCallback<List<AttributeKvEntry>> callback);

    void loadAttributes(EntityId entityId, Collection<String> attributeTypes, PluginCallback<List<AttributeKvEntry>> callback);

    void loadAttributes(EntityId entityId, Collection<String> attributeTypes, Collection<String> attributeKeys, PluginCallback<List<AttributeKvEntry>> callback);

    void getCustomerDevices(UserId userId, int limit, PluginCallback<List<Device>> callback);

}
