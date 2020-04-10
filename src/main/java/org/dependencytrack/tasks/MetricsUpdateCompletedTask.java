/*
 * This file is part of Dependency-Track.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright (c) Michael Gissing. All Rights Reserved.
 */
package org.dependencytrack.tasks;

import alpine.event.framework.Event;
import alpine.event.framework.Subscriber;
import alpine.logging.Logger;
import org.dependencytrack.event.MetricsUpdateCompletedEvent;
import org.dependencytrack.event.MetricsUpdateEvent;
import org.dependencytrack.model.Component;
import org.dependencytrack.model.Dependency;
import org.dependencytrack.model.Project;
import org.dependencytrack.persistence.QueryManager;

/**
 * Subscriber task that performs calculations of various Metrics.
 *
 * @author Michael Gissing
 * @since 3.9.0
 */
public class MetricsUpdateCompletedTask implements Subscriber {

    private static final Logger LOGGER = Logger.getLogger(MetricsUpdateCompletedTask.class);

    @Override
    public void inform(Event e) {
        if (e instanceof MetricsUpdateCompletedEvent) {
            final MetricsUpdateCompletedEvent event = (MetricsUpdateCompletedEvent) e;
            final MetricsUpdateEvent causingEvent = event.getCausingEvent();

            LOGGER.debug("Starting metrics update completed task");

            try (QueryManager qm = new QueryManager()) {
                if (MetricsUpdateEvent.Type.PORTFOLIO == causingEvent.getType()) {
                    handlePortfolioMetricUpdate(qm);
                } else {
                    // Don't know yet - what kind of metric updates do we want to react on?
                    LOGGER.debug("Handling of metric updates other than 'portfolio' currently not implemented");
                }

            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }

            // TODO this message sounds weired
            LOGGER.debug("Metrics update completed task completed");
        }
    }

    private void handlePortfolioMetricUpdate(final QueryManager qm) {
        LOGGER.debug("Executing handle portfolio metric update");
    }

}
