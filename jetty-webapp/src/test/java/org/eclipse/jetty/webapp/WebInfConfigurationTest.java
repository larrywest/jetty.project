//
//  ========================================================================
//  Copyright (c) 1995-2020 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.eclipse.jetty.toolchain.test.FS;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDir;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDirExtension;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * WebInfConfigurationTest
 */
@ExtendWith(WorkDirExtension.class)
public class WebInfConfigurationTest
{
    public WorkDir workDir;

    public static Stream<Arguments> rawResourceNames()
    {
        return Stream.of(
            Arguments.of("/", ""),
            Arguments.of("/a", "a")
        );
    }

    @ParameterizedTest
    @MethodSource("rawResourceNames")
    public void testTinyGetResourceBaseName(String rawPath, String expectedName) throws IOException
    {
        Resource resource = Resource.newResource(rawPath);
        assertThat(WebInfConfiguration.getResourceBaseName(resource), is(expectedName));
    }

    public static Stream<Arguments> fileBaseResourceNames()
    {
        return Stream.of(
            Arguments.of("test.war", "test.war"),
            Arguments.of("a/b/c/test.war", "test.war"),
            Arguments.of("bar%2Fbaz/test.war", "test.war"),
            Arguments.of("fizz buzz/test.war", "test.war"),
            Arguments.of("another one/bites the dust/", "bites the dust"),
            Arguments.of("another+one/bites+the+dust/", "bites+the+dust"),
            Arguments.of("another%20one/bites%20the%20dust/", "bites%20the%20dust"),
            Arguments.of("spanish/n\u00FAmero.war", "n\u00FAmero.war"),
            Arguments.of("spanish/n%C3%BAmero.war", "n%C3%BAmero.war"),
            Arguments.of("a/b!/", "b!"),
            Arguments.of("a/b!/c/", "c"),
            Arguments.of("a/b!/c/d/", "d"),
            Arguments.of("a/b%21/", "b%21")
        );
    }

    @ParameterizedTest
    @MethodSource("fileBaseResourceNames")
    public void testPathGetResourceBaseName(String basePath, String expectedName) throws IOException
    {
        Path root = workDir.getPath();
        Path base = root.resolve(basePath);
        if (basePath.endsWith("/"))
        {
            // we are working with a directory.
            FS.ensureDirExists(base);
        }
        else
        {
            FS.ensureDirExists(base.getParent());
            FS.touch(base);
        }

        Resource resource = new PathResource(base);
        assertThat(WebInfConfiguration.getResourceBaseName(resource), is(expectedName));
    }
}
