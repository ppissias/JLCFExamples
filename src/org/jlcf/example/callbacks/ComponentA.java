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
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentA implements ITestCallback, IComponentA {

	// reference to other component
	private final IComponentB compB;

	// property
	private final String version;

	// logger
	private final Logger logger = Logger.getLogger(getClass());

	// constructor, will be called by the JLCF runtime
	public ComponentA(@Receptacle(name = "compoB") IComponentB compB, // Receptacle
			@Property(name = "version") String version) // Property
	{
		// set other component and property
		this.compB = compB;
		this.version = version;
	}

	@InitMethod
	// initialization methos, this is called by the runtime after it connects
	// all components together
	public void callme() {
		logger.info("component " + getClass().getName() + " version:" + version
				+ " initialized");
	}

	@Override
	// functional interface of the component. Called by users of the framework
	public String doTask(String task) {
		logger.info("version of component="
				+ version
				+ " doTask called. Registering to component B for receiving updates.");
		// register to the other component, the runtime will provide the
		// callback address that was used in the application configuration
		compB.register();
		return task;
	}

	@Override
	// functionali interface of the component. Other component uses this
	// interface to send asynchronously information (via callbacks)
	public String receiveCallback(String info) {
		logger.info("version of component=" + version
				+ " received callback with information:" + info);
		return "callback processed";
	}

}
