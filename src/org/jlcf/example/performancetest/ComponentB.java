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
package org.jlcf.example.performancetest;

import java.util.Random;

import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentB implements IComponentB {

	private volatile float status = 100;

	private final Logger logger = Logger.getLogger(getClass());

	public ComponentB() {

	}

	@InitMethod
	public void init() {
	}

	@Override
	public String getString(java.lang.String in) {
		byte[] ppp = new byte[1000];
		for (int i = 0; i < 1000; i++) {
			ppp[i] = in.getBytes()[new Random().nextInt(in.length())];
		}
		return new String(ppp);
	}

	@Override
	public void doesNothing() {
		int i = 0;
		i = i + 1;
		return;
	}

}
