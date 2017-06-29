/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2016, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.jdbc;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SQLPostProcessorService {
    static SQLPostProcessorService service;
    private ServiceLoader<SQLPostProcessor> loader;

    SQLPostProcessorService() {
        loader = ServiceLoader.load(SQLPostProcessor.class);
    }

    public static synchronized SQLPostProcessorService getInstance() {
        if (service == null)
            service = new SQLPostProcessorService();
        return service;
    }

    public SQLPostProcessor getProvider(String className) {
        Iterator<SQLPostProcessor> postProcessors = loader.iterator();
        while (postProcessors.hasNext()) {
            SQLPostProcessor postProcessor = postProcessors.next();
            if (postProcessor.getClass().getName().equals(className))
                return postProcessor;
        }
        throw new ServiceConfigurationError(
            "cannot find a SQLPostProcessor service provider named \"" + className + "\"");
    }
}
