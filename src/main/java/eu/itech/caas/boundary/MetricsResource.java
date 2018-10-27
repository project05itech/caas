/*
 * Copyright 2018 airhacks.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.itech.caas.boundary;

import eu.itech.caas.control.ServerWatch;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author airhacks.com
 */
@Path("metrics")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class MetricsResource {

    @Inject
    ServerWatch watch;

    @GET
    @Path("usedmemory")
    public JsonObject metric() {
        return Json.createObjectBuilder()
                .add("application", "caas-service")
                .add("component", "jvmusedmemory")
                .add("units", "bytes")
                .add("suffix", "size")
                .add("value", String.valueOf(this.watch.usedMemoryInMb()))
                .build();
    }

    @GET
    @Path("availablememory")
    public JsonObject additional() {
        return Json.createObjectBuilder()
                .add("application", "caas-service")
                .add("component", "jvmavailablememory")
                .add("units", "bytes")
                .add("suffix", "size")
                .add("value", String.valueOf(this.watch.availableMemoryInMB()))
                .build();
    }

    @GET
    @Path("/start-time")
    @Produces(MediaType.TEXT_PLAIN)
    public JsonObject bootTime() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("boottime", watch.getDateTime().toString());

        return builder.build();
    }

   
     //expose in production
    @GET
    @Path("/os-info")
    public JsonObject osInfo() {
        return this.watch.osInfo();
    }

}