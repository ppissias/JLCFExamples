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

import org.apache.log4j.Logger;
import org.jlcf.core.Interceptor;
import org.jlcf.example.interceptors.remoting.client.RemoteWSImplementationService;
import org.jlcf.example.interceptors.remoting.client.RemoteWSInterface;

/**
 * @author Petros Pissias
 * 
 */
public class RemotingInterceptor extends Interceptor implements
		IExternalInterface {

	private final Logger logger = Logger.getLogger(getClass().getName());

	private final RemoteWSInterface remoteService = new RemoteWSImplementationService()
			.getRemoteWSImplementationPort();

	@Override
	public String externalMethod(String argument) {
		logger.info("Intercepting call to externalMethod, with input "
				+ argument + " and forwarding to web service");

		String ret = remoteService.sayHello("Hello");

		// proceess return if needed

		// return
		return ret;
	}
}
