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
package org.jlcf.example.advancedhelloworld;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;

/**
 * Sample component implementation that uses a queue processor
 * 
 * @author Petros Pissias
 * 
 */
public class ComponentB implements IComponentB {

	private final Logger logger = Logger.getLogger(getClass());

	private final ComponentBQueueProcessor processor;

	public ComponentB() {
		processor = new ComponentBQueueProcessor("ComponentB Processor");
		logger.info("ComponentB instantiating");
	}

	@InitMethod
	public void init() {
		processor.initialize();
		logger.info("initialized");
	}

	@Override
	public String someMethod(String input) {
		logger.info("method called, with input :" + input
				+ " sending to processor");
		ComponentBMessageA msg = new ComponentBMessageA(input);
		processor.addRequest(msg);
		try {
			logger.info("waiting for reply from processor");
			String asyncReply = msg.getResponse();
			logger.info("got reply:" + asyncReply + " returning");
			return asyncReply;
		} catch (InterruptedException e) {
			logger.log(Level.ERROR, "exception while waiting for a reply", e);
			return "Error";
		}

	}
}
