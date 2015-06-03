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
import org.jlcf.example.interceptors.remoting.client.RemoteWSImplementationService;
import org.jlcf.example.interceptors.remoting.client.RemoteWSInterface;

/**
 * @author Petros Pissias
 * 
 */
public class RemoteProxyComponent implements IRemoteProxyComponent {

	private final Logger logger = Logger.getLogger(getClass());

	private final RemoteWSInterface remoteService = new RemoteWSImplementationService()
			.getRemoteWSImplementationPort();

	public RemoteProxyComponent() {

	}

	@Override
	public String remoteCall(String input) {
		logger.info("doTask called. Calling remote WS with input=" + input);
		String ret = remoteService.sayHello(input);
		logger.info("received reply from remote WS :" + ret);
		return ret;
	}

}
