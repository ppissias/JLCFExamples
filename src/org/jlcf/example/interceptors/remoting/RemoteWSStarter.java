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
package org.jlcf.example.interceptors.remoting;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Petros Pissias
 * 
 */
public class RemoteWSStarter {

	private static final Logger logger = Logger
			.getLogger(RemoteWSStarter.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Logger initialized");

		Endpoint.publish("http://localhost:8091/ws/RemoteWS",
				new RemoteWSImplementation());
	}

}
