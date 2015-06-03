package org.jlcf.example.interceptors.remoting.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.6 in JDK 6 Generated
 * source version: 2.1
 * 
 */
@WebService(name = "RemoteWSInterface", targetNamespace = "http://remoting.interceptors.example.jlcf.org/")
@XmlSeeAlso({ ObjectFactory.class })
public interface RemoteWSInterface {

	/**
	 * 
	 * @param arg0
	 * @return returns java.lang.String
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "sayHello", targetNamespace = "http://remoting.interceptors.example.jlcf.org/", className = "org.jlcf.example.interceptors.remoting.client.SayHello")
	@ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://remoting.interceptors.example.jlcf.org/", className = "org.jlcf.example.interceptors.remoting.client.SayHelloResponse")
	public String sayHello(
			@WebParam(name = "arg0", targetNamespace = "") String arg0);

}