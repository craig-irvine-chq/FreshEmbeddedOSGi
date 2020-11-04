/*
 * Licensed to the Indoqa Software Design und Beratung GmbH (Indoqa) under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Indoqa licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example;


import java.nio.file.Path;
import java.util.Map;

@SuppressWarnings("unused")
public final class ContainerConfiguration {

    private static final String PROPERTY_OSGI_STORAGE_DIR = "org.osgi.framework.storage";
    private static final String PROPERTY_OSGI_STORAGE_CLEAN = "org.osgi.framework.storage.clean";
    private static final String DEFAULT_OSGI_STORAGE_CLEAN = "onFirstInit";

    private static final String PROPERTY_REMOTE_SHELL_PORT = "osgi.shell.telnet.port";
    private static final String DEFAULT_REMOTE_SHELL_PORT = "6666";

    private Path frameworkStorage;
    private String frameworkStorageClean = DEFAULT_OSGI_STORAGE_CLEAN;

    private String remoteShellPort = DEFAULT_REMOTE_SHELL_PORT;

    private boolean remoteShellBundlesEnabled = false;

    public ContainerConfiguration setEnableRemoteShell(boolean enabled) {
        this.remoteShellBundlesEnabled = enabled;
        return this;
    }

    public ContainerConfiguration setFrameworkStorage(Path frameworkStorage) {
        this.frameworkStorage = frameworkStorage;
        return this;
    }

    public ContainerConfiguration setFrameworkStorageClean(String frameworkStorageClean) {
        this.frameworkStorageClean = frameworkStorageClean;
        return this;
    }

    public ContainerConfiguration setRemoteShellPort(String remoteShellPort) {
        this.remoteShellPort = remoteShellPort;
        return this;
    }

    void apply(Map<String, Object> config) {

        ContainerConfiguration.applyProperty(config, PROPERTY_OSGI_STORAGE_CLEAN, this.frameworkStorageClean);

        if (this.areRemoteShellBundlesEnabled()) {
            applyProperty(config, PROPERTY_REMOTE_SHELL_PORT, this.remoteShellPort);
        }
    }

    boolean areRemoteShellBundlesEnabled() {
        return this.remoteShellBundlesEnabled;
    }

    private static void applyProperty(Map<String, Object> config, String name, Object value) {
        if (value != null) {
            config.put(name, String.valueOf(value));
        }
    }
}
