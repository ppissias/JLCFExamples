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

import javax.jws.WebService;

import org.apache.log4j.Logger;

/**
 * @author Petros Pissias
 * 
 */
@WebService(endpointInterface = "org.jlcf.example.interceptors.remoting.RemoteWSInterface")
public class RemoteWSImplementation implements RemoteWSInterface {

	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public String sayHello(String name) {
		logger.info("Web Service Called with inout : " + name);
		return "Hello from WS";
	}

}
