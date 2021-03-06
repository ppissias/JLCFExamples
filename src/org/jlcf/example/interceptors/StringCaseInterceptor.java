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
package org.jlcf.example.interceptors;

import org.apache.log4j.Logger;
import org.jlcf.core.Interceptor;

/**
 * @author Petros Pissias
 * 
 */
public class StringCaseInterceptor extends Interceptor implements IComponentB {

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public String someMethod(String input) {
		logger.info("Intercepting call to someMethod, changing " + input
				+ " to upper case");
		IComponentB targetInterface = getTarget();
		String ret = targetInterface.someMethod(input.toUpperCase());
		logger.info("Intercepting reply (" + ret + "), switching to lower case");
		return ret.toLowerCase();
	}
}
