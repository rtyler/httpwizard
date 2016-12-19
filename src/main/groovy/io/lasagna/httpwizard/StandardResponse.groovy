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

package io.lasagna.httpwizard

import com.fasterxml.jackson.annotation.JsonProperty
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.UriInfo

/**
 *
 */
class StandardResponse {
    @JsonProperty
    MultivaluedMap<String, String> headers

    @JsonProperty
    String uri

    @JsonProperty
    String description

    static StandardResponse fromRequest(String description,
                                        UriInfo info,
                                        HttpHeaders headers) {
        StandardResponse r = new StandardResponse()
        r.description = description
        r.uri = info.absolutePath
        r.headers = headers.requestHeaders
        return r
    }
}
