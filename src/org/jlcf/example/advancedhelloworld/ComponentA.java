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
package org.jlcf.example.advancedhelloworld;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * Sample component that uses a queue processor to process input requests.
 * 
 * @author Petros Pissias
 * 
 */
public class ComponentA implements IComponentA {

	private final IComponentB compB;
	private final String version;
	private final Logger logger = Logger.getLogger(getClass());

	// processor that will process requests
	private final ComponentAQueueProcessor processor;

	public ComponentA(@Receptacle(name = "compoB") IComponentB compB,
			@Property(name = "version") String version) {

		logger.info("instantiating ComponentA");
		this.compB = compB;
		this.version = version;

		logger.info("creating processor");
		// instantiate the processor
		processor = new ComponentAQueueProcessor("ComponentA processor", compB);
	}

	@InitMethod
	public void callme() {
		logger.info("initializing processor");
		// initialize the processor, this starts the processor thread
		processor.initialize();

		logger.info("component " + getClass().getName() + " version:" + version
				+ " initialized");
	}

	@Override
	public String testMethod1(String task) {
		logger.info("method called with input:" + task
				+ " sending to processor");
		// create input request for the processor
		ComponentAMessageA msg = new ComponentAMessageA(task);
		// send msg to the processor
		processor.addRequest(msg);
		// wait for the reply
		String asyncResponse;
		try {
			logger.info("waiting for an asynchronous reply");
			asyncResponse = msg.getResponse();
			logger.info("got reply:" + asyncResponse + " returning");
		} catch (InterruptedException e) {
			logger.log(Level.ERROR, "exception while waiting for a reply", e);
			return "Error";
		}
		return asyncResponse;
	}

	@Override
	public boolean testMethod2(String task) {
		logger.info("method called with input:" + task
				+ " sending to processor");
		// create input request for the processor
		ComponentAMessageB msg = new ComponentAMessageB(task);
		// send msg to the processor
		processor.addRequest(msg);
		logger.info("sent to processor, returning");
		return true;
	}
}
