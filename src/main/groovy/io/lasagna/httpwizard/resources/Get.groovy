/*
 * Copyright (C) 2016 R. Tyler Croy <tyler@linux.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package io.lasagna.httpwizard.resources

import io.lasagna.httpwizard.StandardResponse

import com.codahale.metrics.annotation.Metered
import groovy.transform.TypeChecked
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path('/get')
@Produces(MediaType.APPLICATION_JSON)
@TypeChecked
class GetResource {

    @GET
    @Path('200')
    @Metered
    StandardResponse returns200(@Context UriInfo ui,
                                @Context HttpHeaders headers) {
        StandardResponse r = new StandardResponse()
        r.uri = ui.absolutePath
        r.headers = headers.requestHeaders
        return r
    }

}
