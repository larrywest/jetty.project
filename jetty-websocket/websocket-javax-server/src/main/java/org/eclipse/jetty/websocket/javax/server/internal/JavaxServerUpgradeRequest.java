//
// ========================================================================
// Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under
// the terms of the Eclipse Public License 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0
//
// This Source Code may also be made available under the following
// Secondary Licenses when the conditions for such availability set
// forth in the Eclipse Public License, v. 2.0 are satisfied:
// the Apache License v2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.websocket.javax.server.internal;

import java.net.URI;
import java.security.Principal;

import org.eclipse.jetty.websocket.javax.common.UpgradeRequest;
import org.eclipse.jetty.websocket.util.server.internal.ServerUpgradeRequest;

public class JavaxServerUpgradeRequest implements UpgradeRequest
{
    private final ServerUpgradeRequest servletRequest;

    public JavaxServerUpgradeRequest(ServerUpgradeRequest servletRequest)
    {
        this.servletRequest = servletRequest;
    }

    @Override
    public Principal getUserPrincipal()
    {
        return servletRequest.getUserPrincipal();
    }

    @Override
    public URI getRequestURI()
    {
        return servletRequest.getRequestURI();
    }

    @Override
    public String getPathInContext()
    {
        return servletRequest.getPathInContext();
    }
}
