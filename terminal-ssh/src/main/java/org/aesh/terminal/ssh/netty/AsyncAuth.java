/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.terminal.ssh.netty;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class AsyncAuth extends RuntimeException {

    private volatile Consumer<Boolean> listener;
    private volatile Boolean authed;

    public void setAuthed(boolean authed) {
        Consumer<Boolean> listener;
        synchronized (this) {
            if (this.authed != null) {
                return;
            }
            this.authed = authed;
            listener = this.listener;
        }
        if (listener != null) {
            listener.accept(authed);
        }
    }

    public void setListener(Consumer<Boolean> listener) {
        Boolean result;
        synchronized (this) {
            this.listener = listener;
            result = this.authed;
        }
        if (result != null) {
            listener.accept(result);
        }
    }
}
