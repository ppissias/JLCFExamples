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
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentA implements IComponentA {

	private final IExternalInterface remotingGateway;

	private final String version;

	private final Logger logger = Logger.getLogger(getClass());

	public ComponentA(
			@Receptacle(name = "remote") IExternalInterface remotingGateway,
			@Property(name = "version") String version)

	{
		this.remotingGateway = remotingGateway;

		this.version = version;
	}

	@InitMethod
	public void callme() {
		logger.info("component " + getClass().getName() + " version:" + version
				+ " initialized");
	}

	@Override
	public String doTask(String input) {
		String compBInput = input;
		logger.info("doTask called. Calling receptacle with input="
				+ compBInput);
		String ret = remotingGateway.externalMethod(compBInput);
		logger.info("received reply :" + ret);
		return ret;
	}

}
