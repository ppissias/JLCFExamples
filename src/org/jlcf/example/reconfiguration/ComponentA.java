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

import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentA implements ITestCallback, IComponentA {

	private final IComponentB compB;

	private final String version;

	private final Logger logger = Logger.getLogger(getClass());

	public ComponentA(@Receptacle(name = "compoB") IComponentB compB,
			@Property(name = "version") String version)

	{
		this.compB = compB;

		this.version = version;
	}

	@InitMethod
	public void callme() {
		logger.info("component " + getClass().getName() + " version:" + version
				+ " initialized");
	}

	@Override
	public String receiveCallback(String info) {
		logger.info("component " + getClass().getName()
				+ " received callback with information:" + info);
		return "callback processed";
	}

	@Override
	public void register() {
		logger.info("register called. Registering to component B for receiving updates.");
		compB.register();
	}

	@Override
	public String doSomething(String input) {

		logger.info("received call to doSomething. Input:" + input);
		// simulate doing something that takes some time
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

		}
		logger.info("finished processing request for input:" + input);
		return "done";
	}

}
