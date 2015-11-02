
/**
 * PreRequestserviceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
        package com.Over2Cloud.ctrl.client;

       

        /*
        *  PreRequestserviceStub java implementation
        */

       
        public class PreRequestserviceStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1;
            return Long.toString(System.currentTimeMillis()) + "_" + counter;
        }

   
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("PreRequestservice" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[3];
       
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
               

            __operation.setName(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com", "insertIntoTablewithoutquery"));
        _service.addOperation(__operation);
       

       
       
            _operations[0]=__operation;
           
       
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
               

            __operation.setName(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com", "updateTable"));
        _service.addOperation(__operation);
       

       
       
            _operations[1]=__operation;
           
       
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
               

            __operation.setName(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com", "insertIntoTable"));
        _service.addOperation(__operation);
       

       
       
            _operations[2]=__operation;
           
       
        }

    //populates the faults
    private void populateFaults(){
        


    }

    /**
      *Constructor that takes in a configContext
      */

    public PreRequestserviceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public PreRequestserviceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
       
   
        configurationContext = _serviceClient.getServiceContext().getConfigurationContext();

        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
       
            //Set the soap version
            _serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
       
   
    }

    /**
     * Default Constructor
     */
    public PreRequestserviceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
       
                    this(configurationContext,"http://192.168.1.21:8083/vamserviceProvider/services/PreRequestservice.PreRequestserviceHttpSoap12Endpoint/" );
               
    }

    /**
     * Default Constructor
     */
    public PreRequestserviceStub() throws org.apache.axis2.AxisFault {
       
                    this("http://192.168.1.21:8083/vamserviceProvider/services/PreRequestservice.PreRequestserviceHttpSoap12Endpoint/" );
               
    }

    /**
     * Constructor taking the target endpoint
     */
    public PreRequestserviceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



       
                    /**
                     * Auto generated method signature
                     *
                     * @see com.Over2Cloud.ctrl.client.PreRequestservice#insertIntoTablewithoutquery
                     * @param insertIntoTablewithoutquery0
                   
                     */

                   

                            public  com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse insertIntoTablewithoutquery(

                            com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery insertIntoTablewithoutquery0)
                       

                    throws java.rmi.RemoteException
                   
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:insertIntoTablewithoutquery");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

             
             
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
             

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

             

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                   
                                                   
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    insertIntoTablewithoutquery0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                    "insertIntoTablewithoutquery")));
                                               
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

        
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
               
               
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                              
                                        return (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse)object;
                                  
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                       

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
           
                /**
                * Auto generated method signature for Asynchronous Invocations
                *
                * @see com.Over2Cloud.ctrl.client.PreRequestservice#startinsertIntoTablewithoutquery
                    * @param insertIntoTablewithoutquery0
               
                */
                public  void startinsertIntoTablewithoutquery(

                 com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery insertIntoTablewithoutquery0,

                  final com.Over2Cloud.ctrl.client.PreRequestserviceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("urn:insertIntoTablewithoutquery");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

             
             
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
             


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                   
                                    //Style is Doc.
                                   
                                                   
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    insertIntoTablewithoutquery0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                    "insertIntoTablewithoutquery")));
                                               
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                   
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                               
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinsertIntoTablewithoutquery(
                                        (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse)object);
                                       
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinsertIntoTablewithoutquery(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
                                if (error instanceof org.apache.axis2.AxisFault) {
                                    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                                    org.apache.axiom.om.OMElement faultElt = f.getDetail();
                                    if (faultElt!=null){
                                        if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                                            //make the fault by reflection
                                            try{
                                                    java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                                                    java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                                                    java.lang.Exception ex=
                                                        (java.lang.Exception) exceptionClass.newInstance();
                                                    //message class
                                                    java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                                                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                                                    java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                                                    java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                                            new java.lang.Class[]{messageClass});
                                                    m.invoke(ex,new java.lang.Object[]{messageObject});
                                                   
                   
                                                    callback.receiveErrorinsertIntoTablewithoutquery(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTablewithoutquery(f);
                                            }
                                        } else {
                                            callback.receiveErrorinsertIntoTablewithoutquery(f);
                                        }
                                    } else {
                                        callback.receiveErrorinsertIntoTablewithoutquery(f);
                                    }
                                } else {
                                    callback.receiveErrorinsertIntoTablewithoutquery(error);
                                }
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorinsertIntoTablewithoutquery(axisFault);
                                }
                            }
                });
                       

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
               
                    /**
                     * Auto generated method signature
                     *
                     * @see com.Over2Cloud.ctrl.client.PreRequestservice#updateTable
                     * @param updateTable2
                   
                     */

                   

                            public  com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse updateTable(

                            com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable updateTable2)
                       

                    throws java.rmi.RemoteException
                   
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("urn:updateTable");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

             
             
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
             

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

             

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                   
                                                   
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    updateTable2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                    "updateTable")));
                                               
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

        
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
               
               
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                              
                                        return (com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse)object;
                                  
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                       

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
           
                /**
                * Auto generated method signature for Asynchronous Invocations
                *
                * @see com.Over2Cloud.ctrl.client.PreRequestservice#startupdateTable
                    * @param updateTable2
               
                */
                public  void startupdateTable(

                 com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable updateTable2,

                  final com.Over2Cloud.ctrl.client.PreRequestserviceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("urn:updateTable");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

             
             
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
             


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                   
                                    //Style is Doc.
                                   
                                                   
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    updateTable2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                    "updateTable")));
                                               
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                   
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                               
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultupdateTable(
                                        (com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse)object);
                                       
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorupdateTable(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
                                if (error instanceof org.apache.axis2.AxisFault) {
                                    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                                    org.apache.axiom.om.OMElement faultElt = f.getDetail();
                                    if (faultElt!=null){
                                        if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                                            //make the fault by reflection
                                            try{
                                                    java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                                                    java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                                                    java.lang.Exception ex=
                                                        (java.lang.Exception) exceptionClass.newInstance();
                                                    //message class
                                                    java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                                                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                                                    java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                                                    java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                                            new java.lang.Class[]{messageClass});
                                                    m.invoke(ex,new java.lang.Object[]{messageObject});
                                                   
                   
                                                    callback.receiveErrorupdateTable(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorupdateTable(f);
                                            }
                                        } else {
                                            callback.receiveErrorupdateTable(f);
                                        }
                                    } else {
                                        callback.receiveErrorupdateTable(f);
                                    }
                                } else {
                                    callback.receiveErrorupdateTable(error);
                                }
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorupdateTable(axisFault);
                                }
                            }
                });
                       

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[1].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[1].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
               
                    /**
                     * Auto generated method signature
                     *
                     * @see com.Over2Cloud.ctrl.client.PreRequestservice#insertIntoTable
                     * @param insertIntoTable4
                   
                     */

                   

                            public  com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse insertIntoTable(

                            com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable insertIntoTable4)
                       

                    throws java.rmi.RemoteException
                   
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("urn:insertIntoTable");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

             
             
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
             

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

             

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                   
                                                   
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    insertIntoTable4,
                                                    optimizeContent(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                    "insertIntoTable")));
                                               
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

        
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
               
               
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                              
                                        return (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse)object;
                                  
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                       

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
           
                /**
                * Auto generated method signature for Asynchronous Invocations
                *
                * @see com.Over2Cloud.ctrl.client.PreRequestservice#startinsertIntoTable
                    * @param insertIntoTable4
               
                */
                public  void startinsertIntoTable(

                 com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable insertIntoTable4,

                  final com.Over2Cloud.ctrl.client.PreRequestserviceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
             _operationClient.getOptions().setAction("urn:insertIntoTable");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

             
             
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
             


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                   
                                    //Style is Doc.
                                   
                                                   
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    insertIntoTable4,
                                                    optimizeContent(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                    "insertIntoTable")));
                                               
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                   
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                               
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinsertIntoTable(
                                        (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse)object);
                                       
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinsertIntoTable(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
                                if (error instanceof org.apache.axis2.AxisFault) {
                                    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                                    org.apache.axiom.om.OMElement faultElt = f.getDetail();
                                    if (faultElt!=null){
                                        if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                                            //make the fault by reflection
                                            try{
                                                    java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                                                    java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                                                    java.lang.Exception ex=
                                                        (java.lang.Exception) exceptionClass.newInstance();
                                                    //message class
                                                    java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                                                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                                                    java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                                                    java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                                            new java.lang.Class[]{messageClass});
                                                    m.invoke(ex,new java.lang.Object[]{messageObject});
                                                   
                   
                                                    callback.receiveErrorinsertIntoTable(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinsertIntoTable(f);
                                            }
                                        } else {
                                            callback.receiveErrorinsertIntoTable(f);
                                        }
                                    } else {
                                        callback.receiveErrorinsertIntoTable(f);
                                    }
                                } else {
                                    callback.receiveErrorinsertIntoTable(error);
                                }
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorinsertIntoTable(axisFault);
                                }
                            }
                });
                       

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[2].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[2].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
               


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

   
   
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
       

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;  
            }
        }
        return false;
    }
     //http://192.168.1.21:8083/vamserviceProvider/services/PreRequestservice.PreRequestserviceHttpSoap12Endpoint/
        public static class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

             
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
   
        public static class InsertIntoTable
        implements org.apache.axis2.databinding.ADBBean{
       
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://services.ctrl.Over2Cloud.com",
                "insertIntoTable",
                "ns1");

           

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://services.ctrl.Over2Cloud.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

       

                        /**
                        * field for CreateTableQuery
                        */

                       
                                    protected java.lang.String localCreateTableQuery ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreateTableQueryTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCreateTableQuery(){
                               return localCreateTableQuery;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param CreateTableQuery
                               */
                               public void setCreateTableQuery(java.lang.String param){
                           
                                       if (param != null){
                                          //update the setting tracker
                                          localCreateTableQueryTracker = true;
                                       } else {
                                          localCreateTableQueryTracker = true;
                                             
                                       }
                                  
                                            this.localCreateTableQuery=param;
                                   

                               }
                           

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
       
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
    
    
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


       
                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       InsertIntoTable.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
           
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
           
               


                java.lang.String prefix = null;
                java.lang.String namespace = null;
               

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
               
                  if (serializeType){
              

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://services.ctrl.Over2Cloud.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":insertIntoTable",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "insertIntoTable",
                           xmlWriter);
                   }

              
                   }
                if (localCreateTableQueryTracker){
                                    namespace = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"createTableQuery", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"createTableQuery");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("createTableQuery");
                                    }
                               

                                          if (localCreateTableQuery==null){
                                              // write the nil attribute
                                             
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                 
                                          }else{

                                       
                                                   xmlWriter.writeCharacters(localCreateTableQuery);
                                           
                                          }
                                   
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
              

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


 
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


       
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCreateTableQueryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "createTableQuery"));
                                
                                         elementList.add(localCreateTableQuery==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreateTableQuery));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
           
           

        }

 

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

       
       

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InsertIntoTable parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InsertIntoTable object =
                new InsertIntoTable();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
               
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

               
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                   
                            if (!"insertIntoTable".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InsertIntoTable)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                       

                  }
               

                }

               

               
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
               

                
                   
                    reader.next();
               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","createTableQuery").equals(reader.getName())){
                               
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                   
                                    java.lang.String content = reader.getElementText();
                                   
                                              object.setCreateTableQuery(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                           
                                       } else {
                                          
                                          
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                     
                                        reader.next();
                                   
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                                 
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                           
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                           



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

       

        }
          
         
        public static class UpdateTableResponse
        implements org.apache.axis2.databinding.ADBBean{
       
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://services.ctrl.Over2Cloud.com",
                "updateTableResponse",
                "ns1");

           

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://services.ctrl.Over2Cloud.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

       

                        /**
                        * field for _return
                        */

                       
                                    protected boolean local_return ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean local_returnTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean get_return(){
                               return local_return;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param _return
                               */
                               public void set_return(boolean param){
                           
                                       // setting primitive attribute tracker to true
                                      
                                               if (false) {
                                           local_returnTracker = false;
                                             
                                       } else {
                                          local_returnTracker = true;
                                       }
                                  
                                            this.local_return=param;
                                   

                               }
                           

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
       
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
    
    
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


       
                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       UpdateTableResponse.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
           
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
           
               


                java.lang.String prefix = null;
                java.lang.String namespace = null;
               

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
               
                  if (serializeType){
              

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://services.ctrl.Over2Cloud.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":updateTableResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "updateTableResponse",
                           xmlWriter);
                   }

              
                   }
                if (local_returnTracker){
                                    namespace = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"return", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"return");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("return");
                                    }
                               
                                               if (false) {
                                          
                                                         throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                                     
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));
                                               }
                                   
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
              

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


 
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


       
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (local_returnTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "return"));
                                
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
           
           

        }

 

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

       
       

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static UpdateTableResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            UpdateTableResponse object =
                new UpdateTableResponse();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
               
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

               
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                   
                            if (!"updateTableResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (UpdateTableResponse)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                       

                  }
               

                }

               

               
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
               

                
                   
                    reader.next();
               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","return").equals(reader.getName())){
                               
                                    java.lang.String content = reader.getElementText();
                                   
                                              object.set_return(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                             
                                        reader.next();
                                   
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                                 
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                           
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                           



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

       

        }
          
         
        public static class InsertIntoTablewithoutquery
        implements org.apache.axis2.databinding.ADBBean{
       
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://services.ctrl.Over2Cloud.com",
                "insertIntoTablewithoutquery",
                "ns1");

           

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://services.ctrl.Over2Cloud.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

       

                        /**
                        * field for TableName
                        */

                       
                                    protected java.lang.String localTableName ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTableNameTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTableName(){
                               return localTableName;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param TableName
                               */
                               public void setTableName(java.lang.String param){
                           
                                       if (param != null){
                                          //update the setting tracker
                                          localTableNameTracker = true;
                                       } else {
                                          localTableNameTracker = true;
                                             
                                       }
                                  
                                            this.localTableName=param;
                                   

                               }
                           

                        /**
                        * field for Tablecolumesaaa
                        */

                       
                                    protected java.lang.Object localTablecolumesaaa ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTablecolumesaaaTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return java.lang.Object
                           */
                           public  java.lang.Object getTablecolumesaaa(){
                               return localTablecolumesaaa;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param Tablecolumesaaa
                               */
                               public void setTablecolumesaaa(java.lang.Object param){
                           
                                       if (param != null){
                                          //update the setting tracker
                                          localTablecolumesaaaTracker = true;
                                       } else {
                                          localTablecolumesaaaTracker = true;
                                             
                                       }
                                  
                                            this.localTablecolumesaaa=param;
                                   

                               }
                           

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
       
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
    
    
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


       
                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       InsertIntoTablewithoutquery.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
           
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
           
               


                java.lang.String prefix = null;
                java.lang.String namespace = null;
               

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
               
                  if (serializeType){
              

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://services.ctrl.Over2Cloud.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":insertIntoTablewithoutquery",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "insertIntoTablewithoutquery",
                           xmlWriter);
                   }

              
                   }
                if (localTableNameTracker){
                                    namespace = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"tableName", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"tableName");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("tableName");
                                    }
                               

                                          if (localTableName==null){
                                              // write the nil attribute
                                             
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                 
                                          }else{

                                       
                                                   xmlWriter.writeCharacters(localTableName);
                                           
                                          }
                                   
                                   xmlWriter.writeEndElement();
                             } if (localTablecolumesaaaTracker){
                           
                            if (localTablecolumesaaa!=null){
                                if (localTablecolumesaaa instanceof org.apache.axis2.databinding.ADBBean){
                                    ((org.apache.axis2.databinding.ADBBean)localTablecolumesaaa).serialize(
                                               new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","Tablecolumesaaa"),
                                               factory,xmlWriter,true);
                                 } else {
                                    java.lang.String namespace2 = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace2.equals("")) {
                                        java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                        if (prefix2 == null) {
                                            prefix2 = generatePrefix(namespace2);

                                            xmlWriter.writeStartElement(prefix2,"Tablecolumesaaa", namespace2);
                                            xmlWriter.writeNamespace(prefix2, namespace2);
                                            xmlWriter.setPrefix(prefix2, namespace2);

                                        } else {
                                            xmlWriter.writeStartElement(namespace2,"Tablecolumesaaa");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("Tablecolumesaaa");
                                    }
                                    org.apache.axis2.databinding.utils.ConverterUtil.serializeAnyType(localTablecolumesaaa, xmlWriter);
                                    xmlWriter.writeEndElement();
                                 }
                            } else {
                               
                                        // write null attribute
                                            java.lang.String namespace2 = "http://services.ctrl.Over2Cloud.com";
                                            if (! namespace2.equals("")) {
                                                java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                                if (prefix2 == null) {
                                                    prefix2 = generatePrefix(namespace2);

                                                    xmlWriter.writeStartElement(prefix2,"Tablecolumesaaa", namespace2);
                                                    xmlWriter.writeNamespace(prefix2, namespace2);
                                                    xmlWriter.setPrefix(prefix2, namespace2);

                                                } else {
                                                    xmlWriter.writeStartElement(namespace2,"Tablecolumesaaa");
                                                }

                                            } else {
                                                xmlWriter.writeStartElement("Tablecolumesaaa");
                                            }

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                   
                            }


                        }
                    xmlWriter.writeEndElement();
              

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


 
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


       
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localTableNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "tableName"));
                                
                                         elementList.add(localTableName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTableName));
                                    } if (localTablecolumesaaaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "Tablecolumesaaa"));
                           
                           
                                    elementList.add(localTablecolumesaaa==null?null:
                                    localTablecolumesaaa);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
           
           

        }

 

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

       
       

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InsertIntoTablewithoutquery parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InsertIntoTablewithoutquery object =
                new InsertIntoTablewithoutquery();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
               
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

               
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                   
                            if (!"insertIntoTablewithoutquery".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InsertIntoTablewithoutquery)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                       

                  }
               

                }

               

               
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
               

                
                   
                    reader.next();
               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","tableName").equals(reader.getName())){
                               
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                   
                                    java.lang.String content = reader.getElementText();
                                   
                                              object.setTableName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                           
                                       } else {
                                          
                                          
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                     
                                        reader.next();
                                   
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","Tablecolumesaaa").equals(reader.getName())){
                               
                                     object.setTablecolumesaaa(org.apache.axis2.databinding.utils.ConverterUtil.getAnyTypeObject(reader,
                                                ExtensionMapper.class));
                                      
                                         reader.next();
                                    
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                                 
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                           
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                           



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

       

        }
          
         
        public static class InsertIntoTablewithoutqueryResponse
        implements org.apache.axis2.databinding.ADBBean{
       
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://services.ctrl.Over2Cloud.com",
                "insertIntoTablewithoutqueryResponse",
                "ns1");

           

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://services.ctrl.Over2Cloud.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

       

                        /**
                        * field for _return
                        */

                       
                                    protected boolean local_return ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean local_returnTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean get_return(){
                               return local_return;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param _return
                               */
                               public void set_return(boolean param){
                           
                                       // setting primitive attribute tracker to true
                                      
                                               if (false) {
                                           local_returnTracker = false;
                                             
                                       } else {
                                          local_returnTracker = true;
                                       }
                                  
                                            this.local_return=param;
                                   

                               }
                           

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
       
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
    
    
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


       
                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       InsertIntoTablewithoutqueryResponse.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
           
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
           
               


                java.lang.String prefix = null;
                java.lang.String namespace = null;
               

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
               
                  if (serializeType){
              

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://services.ctrl.Over2Cloud.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":insertIntoTablewithoutqueryResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "insertIntoTablewithoutqueryResponse",
                           xmlWriter);
                   }

              
                   }
                if (local_returnTracker){
                                    namespace = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"return", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"return");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("return");
                                    }
                               
                                               if (false) {
                                          
                                                         throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                                     
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));
                                               }
                                   
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
              

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


 
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


       
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (local_returnTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "return"));
                                
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
           
           

        }

 

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

       
       

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InsertIntoTablewithoutqueryResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InsertIntoTablewithoutqueryResponse object =
                new InsertIntoTablewithoutqueryResponse();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
               
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

               
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                   
                            if (!"insertIntoTablewithoutqueryResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InsertIntoTablewithoutqueryResponse)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                       

                  }
               

                }

               

               
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
               

                
                   
                    reader.next();
               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","return").equals(reader.getName())){
                               
                                    java.lang.String content = reader.getElementText();
                                   
                                              object.set_return(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                             
                                        reader.next();
                                   
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                                 
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                           
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                           



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

       

        }
          
         
        public static class InsertIntoTableResponse
        implements org.apache.axis2.databinding.ADBBean{
       
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://services.ctrl.Over2Cloud.com",
                "insertIntoTableResponse",
                "ns1");

           

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://services.ctrl.Over2Cloud.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

       

                        /**
                        * field for _return
                        */

                       
                                    protected boolean local_return ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean local_returnTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean get_return(){
                               return local_return;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param _return
                               */
                               public void set_return(boolean param){
                           
                                       // setting primitive attribute tracker to true
                                      
                                               if (false) {
                                           local_returnTracker = false;
                                             
                                       } else {
                                          local_returnTracker = true;
                                       }
                                  
                                            this.local_return=param;
                                   

                               }
                           

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
       
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
    
    
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


       
                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       InsertIntoTableResponse.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
           
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
           
               


                java.lang.String prefix = null;
                java.lang.String namespace = null;
               

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
               
                  if (serializeType){
              

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://services.ctrl.Over2Cloud.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":insertIntoTableResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "insertIntoTableResponse",
                           xmlWriter);
                   }

              
                   }
                if (local_returnTracker){
                                    namespace = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"return", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"return");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("return");
                                    }
                               
                                               if (false) {
                                          
                                                         throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                                     
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));
                                               }
                                   
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
              

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


 
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


       
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (local_returnTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "return"));
                                
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
           
           

        }

 

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

       
       

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InsertIntoTableResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InsertIntoTableResponse object =
                new InsertIntoTableResponse();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
               
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

               
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                   
                            if (!"insertIntoTableResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InsertIntoTableResponse)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                       

                  }
               

                }

               

               
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
               

                
                   
                    reader.next();
               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","return").equals(reader.getName())){
                               
                                    java.lang.String content = reader.getElementText();
                                   
                                              object.set_return(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                             
                                        reader.next();
                                   
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                                 
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                           
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                           



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

       

        }
          
         
        public static class UpdateTable
        implements org.apache.axis2.databinding.ADBBean{
       
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://services.ctrl.Over2Cloud.com",
                "updateTable",
                "ns1");

           

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://services.ctrl.Over2Cloud.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

       

                        /**
                        * field for TableQuery
                        */

                       
                                    protected java.lang.String localTableQuery ;
                               
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTableQueryTracker = false ;
                          

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTableQuery(){
                               return localTableQuery;
                           }

                          
                       
                            /**
                               * Auto generated setter method
                               * @param param TableQuery
                               */
                               public void setTableQuery(java.lang.String param){
                           
                                       if (param != null){
                                          //update the setting tracker
                                          localTableQueryTracker = true;
                                       } else {
                                          localTableQueryTracker = true;
                                             
                                       }
                                  
                                            this.localTableQuery=param;
                                   

                               }
                           

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
       
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
    
    
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


       
                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       UpdateTable.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
           
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
           
               


                java.lang.String prefix = null;
                java.lang.String namespace = null;
               

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
               
                  if (serializeType){
              

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://services.ctrl.Over2Cloud.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":updateTable",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "updateTable",
                           xmlWriter);
                   }

              
                   }
                if (localTableQueryTracker){
                                    namespace = "http://services.ctrl.Over2Cloud.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"TableQuery", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"TableQuery");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("TableQuery");
                                    }
                               

                                          if (localTableQuery==null){
                                              // write the nil attribute
                                             
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                 
                                          }else{

                                       
                                                   xmlWriter.writeCharacters(localTableQuery);
                                           
                                          }
                                   
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
              

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


 
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


       
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localTableQueryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com",
                                                                      "TableQuery"));
                                
                                         elementList.add(localTableQuery==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTableQuery));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
           
           

        }

 

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

       
       

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static UpdateTable parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            UpdateTable object =
                new UpdateTable();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
               
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

               
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                   
                            if (!"updateTable".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (UpdateTable)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                       

                  }
               

                }

               

               
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
               

                
                   
                    reader.next();
               
                                   
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                               
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://services.ctrl.Over2Cloud.com","TableQuery").equals(reader.getName())){
                               
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                   
                                    java.lang.String content = reader.getElementText();
                                   
                                              object.setTableQuery(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                           
                                       } else {
                                          
                                          
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                     
                                        reader.next();
                                   
                              }  // End of if for expected property start element
                               
                                    else {
                                       
                                    }
                                 
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                           
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                           



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

       

        }
          
         
            private  org.apache.axiom.om.OMElement  toOM(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

           
                        try{
                             return param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                   

            }
       
            private  org.apache.axiom.om.OMElement  toOM(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

           
                        try{
                             return param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                   

            }
       
            private  org.apache.axiom.om.OMElement  toOM(com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

           
                        try{
                             return param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                   

            }
       
            private  org.apache.axiom.om.OMElement  toOM(com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

           
                        try{
                             return param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                   

            }
       
            private  org.apache.axiom.om.OMElement  toOM(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

           
                        try{
                             return param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                   

            }
       
            private  org.apache.axiom.om.OMElement  toOM(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

           
                        try{
                             return param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                   

            }
       
                                   
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                            
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                               

                                        }
                               
                            
                             /* methods to provide back word compatibility */

                            
                                   
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                            
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                               

                                        }
                               
                            
                             /* methods to provide back word compatibility */

                            
                                   
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                            
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                               

                                        }
                               
                            
                             /* methods to provide back word compatibility */

                            


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
       
                if (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery.class.equals(type)){
               
                           return com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutquery.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                   

                }
          
                if (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse.class.equals(type)){
               
                           return com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                   

                }
          
                if (com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable.class.equals(type)){
               
                           return com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                   

                }
          
                if (com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse.class.equals(type)){
               
                           return com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                   

                }
          
                if (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable.class.equals(type)){
               
                           return com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                   

                }
          
                if (com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse.class.equals(type)){
               
                           return com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                   

                }
          
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



   
   }
  
