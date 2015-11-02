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
package org.jlcf.example.remoting;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jlcf.core.IJLCFContainer;
import org.jlcf.core.JLCFContainer;

/**
 * @author Petros Pissias
 * 
 */
public class SimpleRemotingExample {

	private final Logger logger = Logger.getLogger(this.getClass());

	public static void main(String[] args) throws Exception {
		new SimpleRemotingExample().process();
	}

	private void process() throws Exception {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("Logger initialized");

		// create the runtime container and get the single instance
		IJLCFContainer runtime = JLCFContainer.getInstance();
		// load the application
		runtime.loadApplication("resources/RemotingExample.xml");

		// get reference to ComponentA's "userinterface" interface
		IComponentA compA = runtime
				.getComponentReference("compA/userinterface");

		compA.doTask("HellO");

		while (true) {
			Thread.sleep(5000);
			compA.doTask("HellO");
		}

	}

}
