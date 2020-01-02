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

package org.eclipse.jetty.websocket.server;

import org.eclipse.jetty.websocket.server.internal.JettyServerFrameHandlerFactory;
import org.eclipse.jetty.websocket.servlet.FrameHandlerFactory;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public abstract class JettyWebSocketServlet extends WebSocketServlet
{
    protected abstract void configure(JettyWebSocketServletFactory factory);

    @Override
    protected final void configure(WebSocketServletFactory factory)
    {
        configure(new JettyWebSocketServletFactory(factory));
    }

    @Override
    protected FrameHandlerFactory getFactory()
    {
        JettyServerFrameHandlerFactory frameHandlerFactory = JettyServerFrameHandlerFactory.getFactory(getServletContext());

        if (frameHandlerFactory == null)
            throw new IllegalStateException("JettyServerFrameHandlerFactory not found");

        return frameHandlerFactory;
    }
}
