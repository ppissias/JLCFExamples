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
package org.jlcf.example.callbacks;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jlcf.core.IJLCFContainer;
import org.jlcf.core.JLCFContainer;

/**
 * @author Petros Pissias
 * 
 */
public class AdvancedCallbackExample {

	private final Logger logger = Logger.getLogger(this.getClass());

	public static void main(String[] args) throws Exception {
		new AdvancedCallbackExample().process();
	}

	private void process() throws Exception {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("Logger initialized");

		IJLCFContainer runtime = JLCFContainer.getInstance();
		runtime.loadApplication("resources/CallbackAdvancedExample.xml");

		IComponentA compA = runtime
				.getComponentReference("compA/userinterface");
		IComponentA compA2 = runtime
				.getComponentReference("compA2/userinterface");
		IComponentA compA3 = runtime
				.getComponentReference("compA3/userinterface");

		compA.doTask("do something!");
		compA2.doTask("do something!");
		compA3.doTask("do something!");

	}

}
