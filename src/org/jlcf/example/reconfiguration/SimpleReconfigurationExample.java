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
package org.jlcf.example.reconfiguration;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jlcf.core.IJLCFContainer;
import org.jlcf.core.JLCFContainer;

/**
 * @author Petros Pissias
 * 
 */
public class SimpleReconfigurationExample {

	private final Logger logger = Logger.getLogger(this.getClass());

	public static void main(String[] args) throws Exception {
		new SimpleReconfigurationExample().process();
	}

	private void process() throws Exception {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("Logger initialized");

		final IJLCFContainer runtime = JLCFContainer.getInstance();
		runtime.loadApplication("resources/SimpleReconfigrationExample.xml");

		IComponentA compA = runtime
				.getComponentReference("compA/userinterface");

		compA.register();

		new Thread() {
			public void run() {

				// sleep for 5 seconds
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {

				}
				// by now the component is servicing at least 10 calls (see code
				// at the end)

				// do a reconfiguration
				try {
					logger.info("starting reconfiguration");
					// replace component compA with new implementation class
					// org.jlcf.example.reconfiguration.NewComponentA
					// set a timeout for the reconfiguration of 10 seconds
					runtime.singleComponentReconfguration("compA",
							"org.jlcf.example.reconfiguration.NewComponentA",
							10000);
				} catch (Exception e) {
					logger.log(Level.ERROR, "exception during recofiguration",
							e);
				}

			}
		}.start();

		// do something on component A
		// every 500 millisecs we will call a method that needs 5000 millisecs
		// to complete
		// this ensures that the component is fully busy while we do the
		// reconfiguration
		while (true) {
			// create new thread that calls component
			Thread.sleep(500);
			new ComponentCallerThread(compA).start();
		}

	}

}
