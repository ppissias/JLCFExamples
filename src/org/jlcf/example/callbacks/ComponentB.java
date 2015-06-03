/**
 * Copyright 2013 Petros Pissias.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.jlcf.example.callbacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jlcf.core.IJLCFContainer;
import org.jlcf.core.annotation.ContainerRef;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.exception.ComponentReferenceException;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentB implements IComponentB {

	// internal property
	private volatile float status = 100;

	// list that holds registered components
	private final List<ITestCallback> registeredComponents;

	private final Logger logger = Logger.getLogger(getClass());

	// container reference
	private final IJLCFContainer container;

	public ComponentB(@ContainerRef IJLCFContainer container) {
		// initialize the list of clients
		registeredComponents = Collections
				.synchronizedList(new ArrayList<ITestCallback>());
		this.container = container;
	}

	@InitMethod
	public void init() {

		// start thread that periodically sends updates to components that have
		// registered
		new Thread() {
			public void run() {
				logger.info("reply thread started");
				while (!interrupted()) {

					try { // sleep for 2 seconds
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}

					logger.info("sending information to all registered client callbacks");

					// send updates to all registered components
					for (ITestCallback registered : registeredComponents) {
						registered.receiveCallback(String.valueOf(status--));
					}
				}
			}
		}.start();
	}

	@Override
	// functional interface of the component. Used by other components that want
	// to register for receiving asynchronously information
	public void register() {
		ITestCallback cb = null;
		try {
			// obtain the callback, the framework provides the correct callback
			// for this call transparently
			cb = container.getCallback();
		} catch (ComponentReferenceException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
			return;
		}
		if (cb != null) { // add the callback to this list of registered users
			registeredComponents.add(cb);
		}
	}

	@Override
	public void unregister() {
		ITestCallback cb = null;
		try {
			// get callback
			cb = container.getCallback();
		} catch (ComponentReferenceException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
			return;
		}
		if (cb != null) { // remove registered component from list
			registeredComponents.remove(cb);
		}
	}

}
