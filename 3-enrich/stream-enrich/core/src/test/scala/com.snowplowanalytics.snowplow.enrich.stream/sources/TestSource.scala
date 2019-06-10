/*
 * Copyright (c) 2013-2019 Snowplow Analytics Ltd.
 * All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0, and
 * you may not use this file except in compliance with the Apache License
 * Version 2.0.  You may obtain a copy of the Apache License Version 2.0 at
 * http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Apache License Version 2.0 is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the Apache License Version 2.0 for the specific language
 * governing permissions and limitations there under.
 */

package com.snowplowanalytics.snowplow.enrich.stream
package sources

import cats.Id
import com.snowplowanalytics.iglu.client.Client
import com.snowplowanalytics.snowplow.badrows.Processor
import com.snowplowanalytics.snowplow.enrich.common.adapters.AdapterRegistry
import com.snowplowanalytics.snowplow.enrich.common.adapters.registry.RemoteAdapter
import com.snowplowanalytics.snowplow.enrich.common.enrichments.EnrichmentRegistry
import com.snowplowanalytics.snowplow.scalatracker.Tracker
import io.circe.Json

import model.EnrichConfig
import sinks.Sink

/**
 * Source to allow the testing framework to enrich events
 * using the same methods from AbstractSource as the other
 * sources.
 */
class TestSource(
  config: EnrichConfig,
  client: Client[Id, Json],
  adapterRegistry: AdapterRegistry,
  enrichmentRegistry: EnrichmentRegistry[Id],
  tracker: Option[Tracker[Id]]
) extends Source(client, adapterRegistry, enrichmentRegistry, tracker, Processor("sce", "1.0.0"), "") {

  override val MaxRecordSize = None

  override val threadLocalGoodSink: ThreadLocal[Sink] = new ThreadLocal[Sink] {
    override def initialValue: Sink = null
  }
  override val threadLocalPiiSink: Option[ThreadLocal[Sink]] = Some(new ThreadLocal[Sink] {
    override def initialValue: Sink = null
  })
  override val threadLocalBadSink: ThreadLocal[Sink] = new ThreadLocal[Sink] {
    override def initialValue: Sink = null
  }

  override def run(): Unit =
    throw new RuntimeException("run() should not be called on TestSource")
}
