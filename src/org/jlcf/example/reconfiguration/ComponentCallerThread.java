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
package org.jlcf.example.reconfiguration;

import org.apache.log4j.Logger;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentCallerThread extends Thread {

	private final Logger logger = Logger.getLogger(getClass());
	private final IComponentA target;

	private static volatile int callcount = 1;

	public ComponentCallerThread(IComponentA target) {
		this.target = target;
	}

	public void run() {
		logger.info("calling component method doSomething with input: INPUT"
				+ callcount);
		target.doSomething("INPUT" + callcount++);
	}
}
