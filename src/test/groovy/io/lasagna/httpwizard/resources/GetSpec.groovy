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

import io.dropwizard.testing.junit.ResourceTestRule
import javax.ws.rs.client.Client
import org.junit.Rule
import spock.lang.Specification

class GetSpec extends Specification {
    @Rule
    ResourceTestRule dropwizard = ResourceTestRule.builder()
                .addResource(new GetResource()).build();

    def "simple GET returns 200"() {
        given:
        Client client = dropwizard.client()
        def response

        when:
        response = dropwizard.client().target('/get/200').request().get()

        then:
        response.status == 200
    }
}
