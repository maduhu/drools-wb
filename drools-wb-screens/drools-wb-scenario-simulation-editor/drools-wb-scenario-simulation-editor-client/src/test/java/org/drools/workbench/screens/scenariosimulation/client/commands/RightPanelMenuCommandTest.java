/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package org.drools.workbench.screens.scenariosimulation.client.commands;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.uberfire.backend.vfs.ObservablePath;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.mvp.PlaceStatus;
import org.uberfire.mvp.impl.PathPlaceRequest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class RightPanelMenuCommandTest {

    private RightPanelMenuCommand rightPanelMenuCommand;

    @Mock
    private PlaceManager placeManager;

    @Mock
    private PathPlaceRequest mockPlaceRequest;

    @Mock
    private ObservablePath mockPath;

    @Before
    public void setup() {
        when(mockPlaceRequest.getPath()).thenReturn(mockPath);
        this.rightPanelMenuCommand = new RightPanelMenuCommand(placeManager) {
            {
                this.rightPanelRequest = mockPlaceRequest;
            }
        };
    }

    @Test
    public void execute() {
        when(placeManager.getStatus(mockPlaceRequest)).thenReturn(PlaceStatus.OPEN);
        rightPanelMenuCommand.execute();
        verify(placeManager, times(1)).closePlace(mockPlaceRequest);
        when(placeManager.getStatus(mockPlaceRequest)).thenReturn(PlaceStatus.CLOSE);
        rightPanelMenuCommand.execute();
        verify(placeManager, times(1)).goTo(mockPlaceRequest);
    }
}