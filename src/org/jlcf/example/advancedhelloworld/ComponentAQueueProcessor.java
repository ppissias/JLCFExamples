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

import org.jlcf.core.util.AbstractQueueProcessor;
import org.jlcf.core.util.GenericProcessorRequest;

/**
 * Sample Queue processor for component A
 * 
 * @author Petros Pissias
 * 
 */
public class ComponentAQueueProcessor extends
		AbstractQueueProcessor<GenericProcessorRequest<ComponentAMessageType>> {

	private final IComponentB componentB;

	public ComponentAQueueProcessor(String processorName, IComponentB componentB) {
		super(processorName);

		this.componentB = componentB;
	}

	@Override
	public void processEvent(
			GenericProcessorRequest<ComponentAMessageType> event)
			throws Exception {

		getLogger().info(
				"processing request of type:"
						+ event.getRequestType().toString());

		switch (event.getRequestType()) {
		case MESSAGE_TYPE_A: {
			// cast the message to the correct type
			ComponentAMessageA msg = (ComponentAMessageA) event;
			// call component B and send back the reply
			String data = msg.getData();
			getLogger().info("calling component B");
			String reply = componentB.someMethod(data);
			getLogger().info("got reply:" + reply + " inserting to message");
			msg.insertResponse(reply);
			break;
		}

		case MESSAGE_TYPE_B: {
			// cast the message to the correct type
			ComponentAMessageB msg = (ComponentAMessageB) event;
			// call component B
			String data = msg.getData();
			getLogger().info("calling component B and immediately retutning");
			componentB.someMethod(data);
			break;
		}

		default: {
			getLogger().info(
					"Cannot process request of type:"
							+ event.getRequestType().toString());
			break;
		}

		}
	}

}
