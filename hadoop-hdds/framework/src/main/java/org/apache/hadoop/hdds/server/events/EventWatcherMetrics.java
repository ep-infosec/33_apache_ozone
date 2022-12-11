/*
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
package org.apache.hadoop.hdds.server.events;

import org.apache.hadoop.metrics2.annotation.Metric;
import org.apache.hadoop.metrics2.lib.MutableCounterLong;
import org.apache.hadoop.metrics2.lib.MutableRate;

import com.google.common.annotations.VisibleForTesting;

/**
 * Metrics for any event watcher.
 */
public class EventWatcherMetrics {

  @Metric
  private MutableCounterLong trackedEvents;

  @Metric
  private MutableCounterLong timedOutEvents;

  @Metric
  private MutableCounterLong completedEvents;

  @Metric
  private MutableRate completionTime;

  public void incrementTrackedEvents() {
    trackedEvents.incr();
  }

  public void incrementTimedOutEvents() {
    timedOutEvents.incr();
  }

  public void incrementCompletedEvents() {
    completedEvents.incr();
  }

  public void updateFinishingTime(long duration) {
    completionTime.add(duration);
  }

  @VisibleForTesting
  public MutableCounterLong getTrackedEvents() {
    return trackedEvents;
  }

  @VisibleForTesting
  public MutableCounterLong getTimedOutEvents() {
    return timedOutEvents;
  }

  @VisibleForTesting
  public MutableCounterLong getCompletedEvents() {
    return completedEvents;
  }

  @VisibleForTesting
  public MutableRate getCompletionTime() {
    return completionTime;
  }
}
