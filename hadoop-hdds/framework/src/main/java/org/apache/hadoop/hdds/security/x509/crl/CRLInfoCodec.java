/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.hadoop.hdds.security.x509.crl;

import com.google.common.base.Preconditions;
import org.apache.hadoop.hdds.protocol.proto.HddsProtos;
import org.apache.hadoop.hdds.utils.db.Codec;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;

/**
 * Codec to serialize / deserialize CRLInfo.
 */
public class CRLInfoCodec implements Codec<CRLInfo> {

  @Override
  public byte[] toPersistedFormat(CRLInfo crlInfo) throws IOException {
    return crlInfo.getProtobuf().toByteArray();
  }

  @Override
  public CRLInfo fromPersistedFormat(byte[] rawData) throws IOException {

    Preconditions.checkNotNull(rawData,
        "Null byte array can't be converted to real object.");
    try {
      return CRLInfo.fromProtobuf(
          HddsProtos.CRLInfoProto.PARSER.parseFrom(rawData));
    } catch (CertificateException | CRLException e) {
      throw new IllegalArgumentException(
          "Can't encode the the raw data from the byte array", e);
    }
  }

  @Override
  public CRLInfo copyObject(CRLInfo object) {
    throw new UnsupportedOperationException();
  }
}
