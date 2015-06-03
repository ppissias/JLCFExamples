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
package org.jlcf.example.advancedcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jlcf.core.JLCFContainer;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.exception.ComponentReferenceException;

/**
 * @author Petros Pissias
 * 
 */
public class BatteryManager implements IBattery {

	private volatile float status = 100;

	private final List<IBatteryEventCallback> registeredComponents;

	private final ReentrantLock compListLock;

	private final Logger logger = Logger.getLogger(getClass());

	public BatteryManager() {
		registeredComponents = Collections
				.synchronizedList(new ArrayList<IBatteryEventCallback>());
		compListLock = new ReentrantLock();
	}

	@InitMethod
	public void init() {
		new Thread() {
			public void run() {
				logger.info("thread started");
				while (!interrupted()) {
					logger.info("thread doing cycle");

					try {
						Thread.sleep(7000);
					} catch (InterruptedException e) {
					}

					// aquire lock
					compListLock.lock();
					try {
						for (IBatteryEventCallback registered : registeredComponents) {
							registered.receiveBatteryUpdate(status--);
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
		logger.info("received call for registration in receiving battery info");
		try {
			IBatteryEventCallback callback = JLCFContainer.getInstance()
					.getCallback();
			if (callback != null) {
				logger.info("Received callback, locking clients list");

				compListLock.lock();
				logger.info("locked list, adding callback to clients list");

				try {
					registeredComponents.add(callback);
				} catch (Exception e) {
					logger.log(Level.ERROR, e.getMessage(), e);
				} finally {
					logger.info("locking list");

					compListLock.unlock();
				}
			} else {
				logger.info("No callback information proivided, ignoring call");
			}
		} catch (ComponentReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void unregister() {
		try {
			if (JLCFContainer.getInstance().getCallback() != null) {
				compListLock.lock();
				try {
					registeredComponents.remove(JLCFContainer.getInstance()
							.getCallback());
				} catch (Exception e) {
					logger.log(Level.ERROR, e.getMessage(), e);
				} finally {
					compListLock.unlock();
				}
			}
		} catch (ComponentReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
