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
package org.jlcf.example.reconfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jlcf.core.IJLCFContainer;
import org.jlcf.core.JLCFContainer;
import org.jlcf.core.annotation.ContainerRef;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.exception.ComponentReferenceException;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentB implements IComponentB {

	private volatile float status = 100;

	private final List<ITestCallback> registeredComponents;

	private final ReentrantLock compListLock;

	private final Logger logger = Logger.getLogger(getClass());

	private final IJLCFContainer container;

	public ComponentB(@ContainerRef IJLCFContainer container) {
		registeredComponents = Collections
				.synchronizedList(new ArrayList<ITestCallback>());
		compListLock = new ReentrantLock();
		this.container = container;
	}

	@InitMethod
	public void init() {
		new Thread() {
			public void run() {
				logger.info("thread started");
				while (!interrupted()) {

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}

					logger.info("sending data to registered clients");

					// aquire lock
					compListLock.lock();
					try {
						for (ITestCallback registered : registeredComponents) {
							registered
									.receiveCallback(String.valueOf(status--));
						}
					} catch (Throwable t) {
						// do nothing , report perhaps
					} finally { // unlock!
						compListLock.unlock();
					}
				}
			}
		}.start();
	}

	@Override
	public void register() {
		ITestCallback cb = null;
		try {
			cb = container.getCallback();
		} catch (ComponentReferenceException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
			return;
		}
		if (cb != null) {
			compListLock.lock();
			try {
				registeredComponents.add(cb);
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			} finally {
				compListLock.unlock();
			}
		}
	}

	@Override
	public void unregister() {
		ITestCallback cb = null;
		try {
			cb = JLCFContainer.getInstance().getCallback();
		} catch (ComponentReferenceException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
			return;
		}
		if (cb != null) {
			compListLock.lock();
			try {
				registeredComponents.remove(cb);
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			} finally {
				compListLock.unlock();
			}
		}
	}

}
