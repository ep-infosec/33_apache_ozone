/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.ozone.om.codec;

import java.io.IOException;

import org.apache.hadoop.ozone.ClientVersion;
import org.apache.hadoop.ozone.om.helpers.OmKeyInfo;
import org.apache.hadoop.ozone.protocol.proto.OzoneManagerProtocolProtos.KeyInfo;
import org.apache.hadoop.hdds.utils.db.Codec;

import com.google.common.base.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Codec to encode OmKeyInfo as byte array.
 *
 * <p>
 * If the bucket layout is FileSystem Optimized.
 * Then, DB stores only the leaf node name into the 'keyName' field.
 * <p>
 * For example, the user given key path is '/a/b/c/d/e/file1', then in DB
 * 'keyName' field stores only the leaf node name, which is 'file1'.
 */
public class OmKeyInfoCodec implements Codec<OmKeyInfo> {
  private static final Logger LOG =
      LoggerFactory.getLogger(OmKeyInfoCodec.class);

  private final boolean ignorePipeline;
  public OmKeyInfoCodec(boolean ignorePipeline) {
    this.ignorePipeline = ignorePipeline;
    LOG.info("OmKeyInfoCodec ignorePipeline = {}", ignorePipeline);
  }

  @Override
  public byte[] toPersistedFormat(OmKeyInfo object) throws IOException {
    Preconditions
        .checkNotNull(object, "Null object can't be converted to byte array.");
    return object.getProtobuf(ignorePipeline, ClientVersion.CURRENT_VERSION)
        .toByteArray();
  }

  @Override
  public OmKeyInfo fromPersistedFormat(byte[] rawData) throws IOException {
    Preconditions
        .checkNotNull(rawData,
            "Null byte array can't converted to real object.");
    try {
      return OmKeyInfo.getFromProtobuf(KeyInfo.parseFrom(rawData));
    } catch (InvalidProtocolBufferException e) {
      throw new IllegalArgumentException(
          "Can't encode the the raw data from the byte array", e);
    }
  }

  @Override
  public OmKeyInfo copyObject(OmKeyInfo omKeyInfo) {
    return omKeyInfo.copyObject();
  }

}
