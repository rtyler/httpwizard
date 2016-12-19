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


import io.lasagna.httpwizard.HttpWizard
import io.lasagna.httpwizard.HttpWizardConfiguration

import groovy.json.JsonSlurper
import io.dropwizard.testing.junit.ResourceTestRule
import io.dropwizard.testing.junit.DropwizardAppRule
import io.dropwizard.testing.ResourceHelpers
import javax.ws.rs.client.Client
import org.glassfish.jersey.client.JerseyClientBuilder
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType
import org.junit.Rule
import spock.lang.Specification

class GetSpec extends Specification {
    @Rule
    ResourceTestRule dropwizard = ResourceTestRule.builder()
                .addResource(new GetResource()).build();

    Client client

    def setup() {
        client = dropwizard.client()
    }

    def "GET [application/json] returns 200"() {
        when:
        def jerseyResponse = dropwizard.client().target('/get/200').request(MediaType.APPLICATION_JSON).get()
        def data = jsonResponseToMap(jerseyResponse)

        then:
        jerseyResponse.status == 200
        data instanceof Map
        data.uri == '/get/200'
    }

    private Map jsonResponseToMap(Object response) {
        return new JsonSlurper().parseText(
            response.readEntity(String.class)
        )
    }
}

class GetViewSpec extends Specification {
    @Rule
    final DropwizardAppRule<HttpWizardConfiguration> app = new DropwizardAppRule<>(HttpWizard.class, ResourceHelpers.resourceFilePath('test-config.yml'))

    Client client

    def setup() {
        client = JerseyClientBuilder.createClient()
    }

    def "GET [text/html] returns 200 and HTML"() {
        when:
        Response jerseyResponse = client.target(getFullPathOf('/get/200'))
                .request(MediaType.TEXT_HTML)
                .get()

        then:
        jerseyResponse.status == 200
        isHtml(jerseyResponse)
    }

    def "GET [] returns 200 and HTML"() {
        when:
        Response jerseyResponse = client.target(getFullPathOf('/get/200'))
                .request()
                .get()

        then:
        jerseyResponse.status == 200
        isHtml jerseyResponse
    }

    private String getFullPathOf(String path) {
        return String.format('http://localhost:%d%s', app.localPort, path)
    }

    private boolean isHtml(Response response) {
        String body = response.readEntity(String.class)
        return body.findAll(/<\/html>/)?.size > 0
    }
}
