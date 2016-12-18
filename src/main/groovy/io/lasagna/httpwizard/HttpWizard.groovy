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

import io.lasagna.httpwizard.checks.VersionCheck
import io.lasagna.httpwizard.resources.GetResource

import com.google.common.collect.ImmutableMap
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.dropwizard.views.ViewBundle

/**
 * HttpWizard is the main Dropwizard application class
 */
class HttpWizard extends Application<HttpWizardConfiguration> {
    /** Run the HttpWizard application */
    static void main(String[] args) throws Exception {
        new HttpWizard().run(args)
    }

    @Override
    void run(HttpWizardConfiguration configuration,
                Environment env) {
        env.healthChecks().register('sanity', new VersionCheck(configuration));

        env.jersey().register(new GetResource())
    }

    @Override
    void initialize(Bootstrap<HttpWizardConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<HttpWizardConfiguration>() {
            @Override
            ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(HttpWizardConfiguration c) {
                return c.viewRendererConfiguration
            }
        })
    }
}
