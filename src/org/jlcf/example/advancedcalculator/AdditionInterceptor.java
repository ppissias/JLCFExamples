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
package org.jlcf.example.advancedcalculator;

import org.apache.log4j.Logger;
import org.jlcf.core.Interceptor;

/**
 * @author Petros Pissias
 * 
 */
public class AdditionInterceptor extends Interceptor implements IAddition {

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public Double add(Double a, Double b) {
		// intercept the values
		logger.info("Interceptor: " + getClass().getName()
				+ " intercepting call, changing " + a + " to " + (-a));
		double aminus = -a;
		IAddition targetInterface = getTarget();
		return targetInterface.add(aminus, b);
	}
}
