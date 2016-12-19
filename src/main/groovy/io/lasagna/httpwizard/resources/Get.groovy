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

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

import com.google.common.base.Charsets
import com.codahale.metrics.annotation.Metered
import groovy.transform.TypeChecked
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriInfo
import javax.ws.rs.Consumes
import javax.ws.rs.DefaultValue
import javax.ws.rs.GET
import javax.ws.rs.QueryParam
import javax.ws.rs.Path
import javax.ws.rs.Produces
import io.dropwizard.views.View

@Path('/get')
@TypeChecked
class GetResource {

    final String DESCRIPTION_200 = 'Standard response for successful HTTP requests.'

    @GET
    @Path('200')
    @Metered
    @Produces([MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML])
    @Consumes([MediaType.TEXT_PLAIN, MediaType.TEXT_HTML])
    View returns200WithHtml(@DefaultValue('true') @QueryParam('pretty') boolean pretty,
                                @Context UriInfo ui,
                                @Context HttpHeaders headers) {
        return new View('/views/standard-response.mustache', Charsets.UTF_8) {
            String getTitle() { return ui.path }
            String getUri() { return ui.absolutePath }
            String getJson() {
                ObjectMapper mapper = new ObjectMapper()
                mapper.enable(SerializationFeature.INDENT_OUTPUT)
                return mapper.writeValueAsString(StandardResponse.fromRequest(DESCRIPTION_200, ui, headers))
            }
        }
    }

    @GET
    @Path('200')
    @Metered
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    StandardResponse returns200(@Context UriInfo ui,
                                @Context HttpHeaders headers) {
        return StandardResponse.fromRequest(DESCRIPTION_200, ui, headers)
    }
}
